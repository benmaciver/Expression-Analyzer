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

            String str = new PrettyVisitor().visit(parser.program());
            System.out.println(str);
            System.out.println(convertToLISP(str));
            //ExpressionEvaluator(str);
        }
    }
    private static String convertToLISP(String line){
        String[] parts = line.split(" ");
        String partsMerged = String.join("", parts);
        String output = "(";
//        for (String s : parts)
//            System.out.print(s + ", ");
        System.out.println();
        if (parts[1].equals( "=")){
            output+="= ";
            output+=parts[0];
            String[] value = Arrays.copyOfRange(parts, 2, parts.length);
            String stringVersion = "";
            for (String s : value) {
                stringVersion+=" ";
                stringVersion += s;
            }
            if (!ContainsOperator(stringVersion))
                output+=stringVersion;
            else{
                output+=ConvertArithmeticToLISP(stringVersion);

            }




        }
        else if (parts[0].equals("print>>")){
            output+=parts[0];
            String[] subArray = SubStringArray(parts, 1);
            String stringVersion = "";
            for (String s : subArray) {
                stringVersion+=" ";
                stringVersion += s;
            }
            if (!ContainsOperator(stringVersion)){
                if (subArray.length == 1)
                    output+=subArray[0];
                else {
                    if (!stringVersion.contains("\""))
                        throw new IllegalArgumentException("Invalid print statement");
                    for (String s : subArray) {
                        output += " ";
                        output += s;
                    }
                }
            }
            else {
                output+=ConvertArithmeticToLISP(stringVersion);

            }
        }
        else if (parts[0].equals("if") || parts[0].equals("while")){
            output+=parts[0];
            output+=" ";
            String[] subArray = SubStringArray(parts, 1);
            String stringVersion = "";
            for (String s : subArray) {
                stringVersion+=" ";
                stringVersion += s;
            }
            System.out.println("ConvertBooleanToLISP input: " + stringVersion);
            output+=ConvertBooleanToLISP(stringVersion);

        }
        else if (parts[0].equals("for")){
            output+="for ( in range ";
            output+= parts[1];
            output+=" " + parts[3];
            output = output.strip();
            output+=")";
        }
        else{
            String stringVersion = "";
            for (String s : parts) {
                stringVersion+=" ";
                stringVersion += s;
            }
            if (!ContainsOperator(stringVersion))
                output+=stringVersion;
            else{
                output+=ConvertArithmeticToLISP(stringVersion);

            }

        }
        output = output.trim();
        output+=")";
        return output;
    } //IN THE MIDDLE OF WRITING THIS METHOD!!!
    private static Boolean ContainsOperator(String str) {
        return str.contains("+") || str.contains("-") || str.contains("*") || str.contains("/");
    }
    private static Boolean ContainsOperator(String[] arr) {
        for (String s : arr) {
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
                return true;
        }
        return false;
    }
    private static String[] SubStringArray(String[] arr, int start, int end) {
        return Arrays.copyOfRange(arr, start, end + 1);
    }
    private static String[] SubStringArray(String[] arr, int start) {
        return Arrays.copyOfRange(arr, start, arr.length);
    }
    private static Pair findFirstOperator(String[] str){
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("+") || str[i].equals("-") || str[i].equals("*") || str[i].equals("/"))
                return new Pair(i, str[i]);
        }
        return null;
    }
    private static String Remove(String str, int index){
        return str.substring(0, index) + str.substring(index + 1);
    }
    private static String ConvertArithmeticToLISP(String expression) {
        String output = expression.replaceAll(" ", "");
        int[] divIndexes = FindAll(output, '/');
        int[] multIndexes = FindAll(output, '*');
        int[] addIndexes = FindAll(output, '+');
        int[] subIndexes = FindAll(output, '-');
        System.out.println("Div Indexes: " + Arrays.toString(divIndexes));
        System.out.println("Mult Indexes: " + Arrays.toString(multIndexes));
        System.out.println("Add Indexes: " + Arrays.toString(addIndexes));
        System.out.println("Sub Indexes: " + Arrays.toString(subIndexes));
        for (int i = 0; i < divIndexes.length; i++){
            String part1="";
            String part2="";
            if (output.charAt(divIndexes[i]+1) == '('){
                part2 = output.substring(divIndexes[i]+1);
                int closingIndex = part2.indexOf(')');
                part2 = part2.substring(0, closingIndex+1);
            }
            else {part1 = output.charAt(divIndexes[i]-1) + "";
                System.out.println("part1 created: "+ part1);}
            if (output.charAt(divIndexes[i]-1) == '('){
                part1 = output.substring(0, divIndexes[i]);
                int openingIndex = part1.lastIndexOf('(');
                part1 = part1.substring(openingIndex);
            }
            else {part2 = output.charAt(divIndexes[i]+1) + "";
                System.out.println("part2 created: "+ part2);}
            if (part1.equals("") || part2.equals(""))
                throw new IllegalArgumentException("Invalid expression: " + expression);
            String subString = part1 + "/" + part2;
            String replacement = "(/ " + part1 +  " " + part2 + ")";
            output = output.replaceFirst(subString, replacement);


        }
        for (int i = 0; i < multIndexes.length; i++){
            String part1="";
            String part2="";
            if (output.charAt(multIndexes[i]+1) == '('){
                part2 = output.substring(multIndexes[i]+1);
                int closingIndex = part2.indexOf(')');
                part2 = part2.substring(0, closingIndex+1);
            }
            else {part1 = output.charAt(multIndexes[i]-1) + "";
                System.out.println("part1 created: "+ part1);}
            if (output.charAt(multIndexes[i]-1) == '('){
                part1 = output.substring(0, multIndexes[i]);
                int openingIndex = part1.lastIndexOf('(');
                part1 = part1.substring(openingIndex);
            }
            else {part2 = output.charAt(multIndexes[i]+1) + "";
                System.out.println("part2 created: "+ part2);}
            if (part1.equals("") || part2.equals(""))
                throw new IllegalArgumentException("Invalid expression: " + expression);
            String subString = part1 + "*" + part2;
            String replacement = "(* " + part1 +  " " + part2 + ")";
            output = output.replaceFirst(subString, replacement);
            output = Remove(output, multIndexes[i]);
            output = Remove(output, multIndexes[i]-1);

        }
        for (int i = 0; i < addIndexes.length; i++){
            String part1="";
            String part2="";
            if (output.charAt(addIndexes[i]+1) == '('){
                part2 = output.substring(addIndexes[i]+1);
                int closingIndex = part2.indexOf(')');
                part2 = part2.substring(0, closingIndex+1);
            }
            else {part1 = output.charAt(addIndexes[i]-1) + "";
                System.out.println("part1 created: "+ part1);}
            if (output.charAt(addIndexes[i]-1) == '('){
                part1 = output.substring(0, addIndexes[i]);
                int openingIndex = part1.lastIndexOf('(');
                part1 = part1.substring(openingIndex);
            }
            else {part2 = output.charAt(addIndexes[i]+1) + "";
                System.out.println("part2 created: "+ part2);}
            if (part1.equals("") || part2.equals(""))
                throw new IllegalArgumentException("Invalid expression: " + expression);
            String subString = part1 + "\\+" + part2;
            String replacement = "(+ " + part1 +  " " + part2 + ")";
            output = output.replaceFirst(subString, replacement);

        }
        for (int i = 0; i < subIndexes.length; i++){
            String part1="";
            String part2="";
            if (output.charAt(subIndexes[i]+1) == '('){
                part2 = output.substring(subIndexes[i]+1);
                int closingIndex = part2.indexOf(')');
                part2 = part2.substring(0, closingIndex+1);
            }
            else {part1 = output.charAt(subIndexes[i]-1) + "";
                System.out.println("part1 created: "+ part1);}
            if (output.charAt(subIndexes[i]-1) == '('){
                part1 = output.substring(0, subIndexes[i]);
                int openingIndex = part1.lastIndexOf('(');
                part1 = part1.substring(openingIndex);
            }
            else {part2 = output.charAt(subIndexes[i]+1) + "";
                System.out.println("part2 created: "+ part2);}
            if (part1.equals("") || part2.equals(""))
                throw new IllegalArgumentException("Invalid expression: " + expression);
            String subString = part1 + "-" + part2;
            String replacement = "(- " + part1 +  " " + part2 + ")";
            output = output.replaceFirst(subString, replacement);
//            output = Remove(output, subIndexes[i]);
//            output = Remove(output, subIndexes[i]-1);

        }

        return output;

    }
    private static String ConvertBooleanToLISP(String expression){
        //All expressions are simple, no brackets ie: a>b, a<b, a>=b, a<=b, a==b, a!=b
        String output = expression.replaceAll(" ", "");
            int equalsIndex = output.indexOf("==");
            int notEqualsIndex = output.indexOf("!=");
            int lessThanEqualsIndex = output.indexOf("<=");
            int greaterThanEqualsIndex = output.indexOf(">=");
            int lessThanIndex = output.indexOf('<');
            int greaterThanIndex = output.indexOf('>');
            int[] indexes = {lessThanIndex, greaterThanIndex, equalsIndex, notEqualsIndex, lessThanEqualsIndex, greaterThanEqualsIndex};
            if (ArrayIsEmpty(indexes))
                throw new IllegalArgumentException("Invalid boolean expression: " + expression);
            String operator = "";
            if (lessThanIndex != -1)
                operator = "<";
            else if (greaterThanIndex != -1)
                operator = ">";
            else if (equalsIndex != -1)
                operator = "=";
            else if (notEqualsIndex != -1)
                operator = "!=";
            else if (lessThanEqualsIndex != -1)
                operator = "<=";
            else if (greaterThanEqualsIndex != -1)
                operator = ">=";

            output = output.trim();
            String newOutput = "( " + operator + " "+  output.substring(0, 1) + " " + output.substring(output.length()-1) + " )";
            return newOutput;




    }
    private static int[] FindAll(String str,Character c){
        int[] output = new int[str.length()];
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                output[index++] = i;
        }
        return Arrays.copyOf(output, index);
    }
    private static Boolean ArrayIsEmpty(int[] arr){
        Boolean empty = true;
        for (int i : arr) {
            if (i != -1)
                empty = false;
        }
        return empty;
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
    private static int getVariable(String str) {
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



}
