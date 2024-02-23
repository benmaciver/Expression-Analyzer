import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    private static ArrayList<Variable> variables = new ArrayList<>();
    private static Boolean readNextLine;
    private static Boolean lastLineWasIf;
    private static int forLoops = 0;
    private static Queue<String> parseTreeQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        readNextLine = true;
        lastLineWasIf = false;
        BufferedReader reader = new BufferedReader(new FileReader("src/test.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.equals("")) {
                continue;
            }

            if (line.substring(0,3).equals("    ") && lastLineWasIf)
                line = line.substring(4);
            else {
                lastLineWasIf = false;
                if (line.substring(0,3).equals("    "))
                    continue;
            }
            //System.out.println("Input: " + line);
            CharStream cs = CharStreams.fromString(line);
            exprLexer lexer = new exprLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            exprParser parser = new exprParser(tokens);
            System.out.println("Executing pretty print");
            PrettyVisitor pv = new PrettyVisitor();
            String LISP = pv.visit(parser.program());
            String evaluateableCode = pv.GetEvaluateableCode();
            System.out.println("Pretty print result: "+  LISP);
            System.out.println("Evaluating expreesion...");
            ExpressionEvaluator(evaluateableCode);
            parseTreeQueue.add(evaluateableCode);
        }
        displayParseTree();
    }
    private static void displayParseTree(){
        BinaryTree parseTree = new BinaryTree();
        int i = 1;
        parseTree.add("program");
        while (!parseTreeQueue.isEmpty()) {
            BinaryTree subTree = new BinaryTree();
            String line = parseTreeQueue.poll();
            line = line.strip();
            String[] splitStr = line.split(" ");
            subTree.add("statement: " + i);
            if (splitStr[0].equals("print>>")){
                subTree.insert(("statement: " + i), "print");
                subTree.insert("print","PRINT:print>>");
                splitStr = Arrays.copyOfRange(splitStr, 1, splitStr.length);
                if (containsOperator(splitStr)){
                    subTree.insert("print","arithmetic");
                    insertValue(subTree, "arithmetic", splitStr[0]);
                    insertOperator(subTree, "arithmetic", splitStr[1]);
                    insertValue(subTree, "arithmetic", splitStr[2]);
                }
                else if (splitStr[0].contains("\""))
                {
                    subTree.insert("print","STRING: " + splitStr[0]);
                }
                else {
                    insertValue(subTree, "print", splitStr[0]);
                }

            }
            else if (splitStr[1].equals("=")){
                subTree.insert(("statement: " + i), "assignment");
                subTree.insert(("assignment"), "VARIABLE: " + splitStr[0]);
                subTree.insert(("assignment"), "ASSIGN: =");
                splitStr = Arrays.copyOfRange(splitStr, 2, splitStr.length);
                if (containsOperator(splitStr)){
                    subTree.insert("assignment","arithmetic");
                    insertValue(subTree, "arithmetic", splitStr[0]);
                    insertOperator(subTree, "arithmetic", splitStr[1]);
                    insertValue(subTree, "arithmetic", splitStr[2]);
                }
                else if (splitStr[0].contains("\""))
                {
                    subTree.insert("assignment","STRING: " + splitStr[0]);
                }
                else {
                    insertValue(subTree, "assignment", splitStr[0]);
                }
            }
            else{
                subTree.insert(("statement: " + i),"control_statement");
                String controlStatement = null;
                splitStr[0] = splitStr[0].strip();
                System.out.println(splitStr[0]);
                if (splitStr[0].equals("if"))
                    controlStatement = "if_statement";
                else if (splitStr[0].equals("while"))
                    controlStatement = "while_loop";
                if (controlStatement!=null) {
                    subTree.insert("control_statement", controlStatement);
                    subTree.insert(controlStatement, splitStr[0].toUpperCase());
                    subTree.insert(controlStatement, "boolean");
                    splitStr = Arrays.copyOfRange(splitStr, 1, splitStr.length);
                    insertValue(subTree, "boolean", String.valueOf(splitStr[0].charAt(0)));
                    insertComparisonOperator(subTree, "boolean", String.valueOf(splitStr[0].charAt(1)));
                    insertValue(subTree, "boolean", String.valueOf(splitStr[0].charAt(2)));
                }
                else {
                    subTree.insert("control_statement","for_loop");
                    subTree.insert("for_loop", "FOR");
                    subTree.insert("for_loop", "VARIABLE: " + splitStr[1]);
                    subTree.insert("for_loop", "IN RANGE: in range");
                    insertValue(subTree, "for_loop", splitStr[3]);
                }
            }



            parseTree.joinTrees(subTree);
            i++;
        }
        parseTree.display();
    }
    private static Boolean containsOperator(String[] str){
        for (String s : str){
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
                return true;
        }
        return false;
    }
    private static void insertValue(BinaryTree parseTree, String parent, String value){
        parseTree.insert(parent, "value");
        if (isInt(value))
            parseTree.insert("value","INT: " +  value);
        else if (isFloat(value))
            parseTree.insert("value","FLOAT: " +  value);
        else
            parseTree.insert("value","VARIABLE: " +  value);

        parseTree.getNode("value").setNumberOfChildren(1);
    }
    private static void insertOperator(BinaryTree parseTree, String parent, String operator){
        if (operator.equals("+"))
            parseTree.insert(parent, "PLUS: " +  operator);
        else if (operator.equals("-"))
            parseTree.insert(parent, "MINUS:" +  operator);
        else if (operator.equals("*"))
            parseTree.insert(parent, "MULTIPLY: " +  operator);
        else if (operator.equals("/"))
            parseTree.insert(parent, "DIVIDE: " + operator);
    }
    private static void insertComparisonOperator(BinaryTree parseTree, String parent, String operator){
        if (operator.equals(">"))
            parseTree.insert(parent, "GREATER: " +  operator);
        else if (operator.equals("<"))
            parseTree.insert(parent, "LESS:" +  operator);
        else if (operator.equals(">="))
            parseTree.insert(parent, "GREATER_EQUAL: " +  operator);
        else if (operator.equals("<="))
            parseTree.insert(parent, "LESS__EQUAL: " + operator);
        else if (operator.equals("=="))
            parseTree.insert(parent, "EQUAL: " + operator);
        else if (operator.equals("!="))
            parseTree.insert(parent, "NOT_EQUAL: " + operator);
    }
    private static void ExpressionEvaluator(String str) {
        ArrayList<String> splitStr = new ArrayList<>(Arrays.asList(str.split(" ")));
        if (splitStr.get(0).equals("print>>")) {
            splitStr.remove(0);

//            for (String s : splitStr)
//                System.out.println(s);
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
        }
        else if (splitStr.get(0).equals("if")){
            //System.out.println(String.valueOf(splitStr));
            splitStr.remove(0);
            String expression = SubstituteVariablesForValues(String.join(" ", splitStr));
            //System.out.println(expression);
            //System.out.println(String.valueOf(splitStr));
            if (!BooleanExpressionEvaluator(expression))
                readNextLine = false;
            else lastLineWasIf = true;
        }
        else if (splitStr.get(0).equals("while")){
            splitStr.remove(0);
            String expression = SubstituteVariablesForValues(String.join(" ", splitStr));
            if (!BooleanExpressionEvaluator(expression))
                readNextLine = false;
            else lastLineWasIf = true;
        }
        else if (splitStr.get(0).equals("for")){
            splitStr.remove(0);
            String varName = splitStr.get(0);
            int varIndex = getVariable(varName);
            if (varIndex == -1)
                throw new IllegalArgumentException("Variable " + varName + " not found");
            String val = splitStr.get(splitStr.size()-1);
            if (isInt(val))
                forLoops = Integer.parseInt(val);
            else {
                Variable v = variables.get(varIndex);
                if (v!=null){
                    forLoops = v.intValue;
                }
                else throw new IllegalArgumentException("Variable " + varName + " not found");
            }


        }
        else {
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
//    private static ArrayList<String> SubstituteVariablesForValues(ArrayList<String> expression) {
//        ArrayList<String> output = new ArrayList<>();
//        for (String s : expression) {
//            if (getVariable(s) != -1) {
//                output.add(variables.get(getVariable(s)).getValue());
//            } else
//                output.add(s);
//        }
//
//        return output;
//    }
    private static String SubstituteVariablesForValues(String str) {
        for (int i = 0; i < variables.size(); i++) {
            str = str.replaceAll(variables.get(i).name, variables.get(i).getValue());
        }
        return str;
    }

    private static boolean BooleanExpressionEvaluator(String expression) {
        int opIndex = -1;
        for (int i= 0; i < expression.length(); i++) {
            Character s = expression.charAt(i);
            if (s == '>')
            {opIndex = i; break;}
            else if (s == '<')
            {opIndex = i; break;}
            else if (s == '=')
            {opIndex = i; break;}
            else if (s == '!')
            {opIndex = i; break;}
        }
        if (opIndex == -1)
            throw new IllegalArgumentException("Invalid boolean expression: " + expression);
        float a = Float.parseFloat(expression.substring(0, opIndex));
        float b;
        try {
            b = Float.parseFloat(expression.substring(opIndex + 1));
        }
        catch(Exception e)
        {
            b = Float.parseFloat(expression.substring(opIndex + 2));
        }


        if (expression.charAt(opIndex) == '>' && expression.charAt(opIndex + 1) == '=')
            return a >= b;
        else if (expression.charAt(opIndex) == '<' && expression.charAt(opIndex + 1) == '=')
            return a <= b;
        else if (expression.charAt(opIndex) == '='&& expression.charAt(opIndex + 1) == '=')
            return a == b;
        else if (expression.charAt(opIndex) == '!'&& expression.charAt(opIndex + 1) == '=')
            return a != b;
        else if (expression.charAt(opIndex) == '>')
            return a > b;
        else if (expression.charAt(opIndex) == '<')
            return a < b;

        else throw new IllegalArgumentException("Invalid boolean expression: " + expression);
    }
    private static float arithmeticExpressionEvaluator(String expression) {
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
                return b - a;
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
    private static int getVariable(String str) {
        str = str.strip();
        for (int i = 0; i < variables.size(); i++) {
            String varName = variables.get(i).name;
            if (varName.equals(str)) {
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
    private static class Pair{
        public int index;
        public String value;

        Pair(int index, String value){
            this.index = index;
            this.value = value;
        }
        public String toString(){
            return "Index: " + index + " Value: " + value;
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
    private static void PrintVariables() {
        for (Variable v : variables) {
            System.out.println(v.name + " " + v.getValue());
        }
    }



}
