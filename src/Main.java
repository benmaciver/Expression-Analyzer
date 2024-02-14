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
            if (line.equals("")) {
                continue;
            }
            CharStream cs = CharStreams.fromString(line);
            exprLexer lexer = new exprLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            exprParser parser = new exprParser(tokens);

            String str = new PrettyVisitor().visit(parser.program());
            System.out.println("Parsed expression with pretty print: ");
            System.out.println(str);
            System.out.println("Evaluated expression with evaluator: ");
            ExpressionEvaluator(str);
        }
        System.out.println("");
    }
    private static void ExpressionEvaluator(String str) {
        ArrayList<String> splitStr = new ArrayList<>(Arrays.asList(str.split(" ")));
        splitStr.remove(0);
        splitStr.remove(splitStr.size()-1);
        if (splitStr.get(0).equals("print>>")){
            splitStr.remove(0);
            if (splitStr.size() == 1){
                int index = getVariable(splitStr.get(0));
                if (index !=-1){
                    variables.get(index).printValue();
                }
                else {
                    String output = splitStr.get(0);
                    output = output.replaceAll("\"","");
                    System.out.println(output);
                }
            }
            else {
                String arithmeticExpression = "";
                for (String s : splitStr)
                    arithmeticExpression +=s ;
                System.out.println(arithmeticExpressionEvaluator(arithmeticExpression));
            }
        }
        else{
            String varName = splitStr.get(0);
            Variable var;
            splitStr.remove(0);
            splitStr.remove(0);
            if (splitStr.size()==1) {
                if (splitStr.get(0).contains("\"")) {
                    var = new Variable(varName, splitStr.get(0));
                }
                else {
                    int index = getVariable(splitStr.get(0));
                    if (index!=-1){
                        if (variables.get(index).type.equals("int")){
                            int value = variables.get(index).intValue;
                            var = new Variable(varName, Integer.toString(value));
                        }
                        else {
                            String value = variables.get(index).strValue;
                            var = new Variable(varName, value);
                        }
                    }
                    else {
                        String value = splitStr.get(0);
                        var = new Variable(varName, value);
                    }
                }
            }
            else {
                String arithmeticExpression = "";
                for (String s : splitStr)
                    arithmeticExpression +=s ;
                int val = arithmeticExpressionEvaluator(arithmeticExpression);
                var = new Variable(varName, Integer.toString(val));
            }
            if (getVariable(varName) != -1) {
                variables.set(getVariable(varName), var);
            } else {
                variables.add(var);
            }
        }
    }
    public static int arithmeticExpressionEvaluator(String expression) {
        expression = expression.replaceAll(" ", "");
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                num.append(c);
                while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    num.append(expression.charAt(++i));
                }
                values.push(Integer.parseInt(num.toString()));
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
    private static int applyOperator(int a, int b, char op) {
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

        Variable(String name, String value) {
            this.name = name;
            try{
                intValue = Integer.parseInt(value);
                type = "int";
            } catch (NumberFormatException e) {
                if (value.contains("\"")) {
                    type = "string";
                    strValue = value.replaceAll("\"", "");
                }
                else {
                    if (getVariable(value) != -1) {
                        intValue = variables.get(getVariable(value)).intValue;
                        strValue = variables.get(getVariable(value)).strValue;
                        type = variables.get(getVariable(value)).type;
                    }
                }
            }
        }
        public void printValue() {
            if (type.equals("int")) {
                System.out.println(intValue);
            } else {
                System.out.println(strValue);
            }
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
}

