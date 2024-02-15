import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static ArrayList<Variable> variables = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.equals("")) {
                continue;
            }
            CharStream cs = CharStreams.fromString(line);
            exprLexer lexer = new exprLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            exprParser parser = new exprParser(tokens);

            String str = new PrettyVisitor().visit(parser.program());
            ExpressionEvaluator(str);
        }
    }

    private static void ExpressionEvaluator(String str) {
        ArrayList<String> splitStr = new ArrayList<>(Arrays.asList(str.split(" ")));
        splitStr.remove(0);
        splitStr.remove(splitStr.size() - 1);

        if (splitStr.get(0).equals("print>>")) {
            splitStr.remove(0);
            if (splitStr.size() == 1) {
                int index = getVariable(splitStr.get(0));
                if (index != -1) {
                    variables.get(index).printValue();
                } else {
                    String output = splitStr.get(0);
                    output = output.replaceAll("\"", "");
                    System.out.println(output);
                }
            } else {
                String arithmeticExpression = String.join("", splitStr);
                arithmeticExpression = SubstituteVariablesForValues(arithmeticExpression);
                System.out.println(arithmeticExpressionEvaluator(arithmeticExpression));
            }
        } else {
            String varName = splitStr.get(0);
            Variable var;
            splitStr.remove(0);
            splitStr.remove(0);
            if (splitStr.size() == 1) {
                if (splitStr.get(0).contains("\"")) {
                    var = new Variable(varName, splitStr.get(0));
                } else {
                    int index = getVariable(splitStr.get(0));
                    if (index != -1) {
                        if (variables.get(index).type.equals("int")) {
                            int value = variables.get(index).intValue;
                            var = new Variable(varName, Integer.toString(value));
                        } else {
                            String value = variables.get(index).strValue;
                            var = new Variable(varName, value);
                        }
                    } else {
                        String value = splitStr.get(0);
                        var = new Variable(varName, value);
                    }
                }
            } else {
                String arithmeticExpression = String.join("", splitStr);
                arithmeticExpression = SubstituteVariablesForValues(arithmeticExpression);
                float val = arithmeticExpressionEvaluator(arithmeticExpression);
                var = new Variable(varName, Float.toString(val));
            }
            if (getVariable(varName) != -1) {
                variables.set(getVariable(varName), var);
            } else {
                variables.add(var);
            }
        }
    }
    private static String SubstituteVariablesForValues(String str) {
        for (int i = 0; i < variables.size(); i++) {
            str = str.replaceAll(variables.get(i).name, variables.get(i).getValue());
        }
        return str;
    }

    public static float arithmeticExpressionEvaluator(String expression) {
        expression = expression.replaceAll(" ", "");
        Stack<Float> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                num.append(c);
                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    num.append(expression.charAt(++i));
                }
                values.push(Float.parseFloat(num.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(values.pop(), values.pop(), operators.pop()));
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.empty() && precedence(operators.peek()) >= precedence(c)) {
                    values.push(applyOperator(values.pop(), values.pop(), operators.pop()));
                }
                operators.push(c);
            }
        }
        while (!operators.empty()) {
            values.push(applyOperator(values.pop(), values.pop(), operators.pop()));
        }
        return values.pop();
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static float applyOperator(float a, float b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    private static class Variable {
        public String name;
        public String type;
        public String strValue;
        public int intValue;
        public float floatValue;

        Variable(String name, String value) {
            this.name = name;
            if (isInt(value)) {
                intValue = Integer.parseInt(value);
                type = "int";
            } else if (isFloat(value)) {
                floatValue = Float.parseFloat(value);
                type = "float";
            } else {
                if (value.contains("\"")) {
                    type = "string";
                    strValue = value.replaceAll("\"", "");
                } else {
                    if (getVariable(value) != -1) {
                        intValue = variables.get(getVariable(value)).intValue;
                        strValue = variables.get(getVariable(value)).strValue;
                        type = variables.get(getVariable(value)).type;
                    }
                }
            }
        }

        public void printValue() {
            System.out.println(getValue());
        }
        public String getValue(){
            if (type.equals("int"))
                return Integer.toString(intValue);
            else if (type.equals("float"))
                return Float.toString(floatValue);
            else if (type.equals("string"))
                return strValue;
            return null;
        }
    }

    public static int getVariable(String str) {
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).name.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private static Boolean isFloat(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
