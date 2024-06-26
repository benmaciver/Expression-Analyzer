import org.antlr.v4.runtime.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The Main class is the entry point of the application.
 * It reads a file, parses it, and evaluates the expressions in it.
 */
public class Main {
    private static ArrayList<Variable> variables = new ArrayList<>();
    private static Boolean readNextLine;
    private static String whileExpression;
    private static int forLoops = 0;
    private static Variable forVar;
    private static Queue<String> parseTreeQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        readNextLine = true;
        BufferedReader reader = new BufferedReader(new FileReader("src/test.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            while (whileExpression!=null){
                CharStream cs = CharStreams.fromString(line);
                exprLexer lexer = new exprLexer(cs);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                exprParser parser = new exprParser(tokens);
                PrettyVisitor pv = new PrettyVisitor();
                pv.visit(parser.program());
                String evaluateableCode = pv.GetEvaluateableCode();
                ExpressionEvaluator(evaluateableCode);
                if (!BooleanExpressionEvaluator(SubstituteVariablesForValues(whileExpression)))
                    whileExpression = null;
            }
            for (int i = 0; i < forLoops-1; i++){

                CharStream cs = CharStreams.fromString(line);
                exprLexer lexer = new exprLexer(cs);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                exprParser parser = new exprParser(tokens);
                PrettyVisitor pv = new PrettyVisitor();
                pv.visit(parser.program());
                String evaluateableCode = pv.GetEvaluateableCode();

                ExpressionEvaluator(evaluateableCode);
                forVar.intValue++;
            }
            if (forVar!=null){

                forLoops=0;
            }


                if (!readNextLine) {
                    readNextLine = true;
                    continue;
                }
                line = line.trim();
                if (line.equals("")) {
                    continue;
                }


            CharStream cs = CharStreams.fromString(line);
            exprLexer lexer = new exprLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            exprParser parser = new exprParser(tokens);
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
    /**
     * Evaluates an expression.
     * @param str The expression to evaluate.
     */
    private static void ExpressionEvaluator(String str) {
        ArrayList<String> splitStr = new ArrayList<>(Arrays.asList(str.split(" ")));
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
        }
        else if (splitStr.get(0).equals("if")){
            splitStr.remove(0);
            String expression = SubstituteVariablesForValues(String.join(" ", splitStr));
            if (!BooleanExpressionEvaluator(expression))
                readNextLine = false;
        }
        else if (splitStr.get(0).equals("while")){
            splitStr.remove(0);
            String unSubstitutedExpression = String.join(" ", splitStr);
            String expression = SubstituteVariablesForValues(unSubstitutedExpression);
            whileExpression = unSubstitutedExpression;
            if (!BooleanExpressionEvaluator(expression))
                readNextLine = false;
        }
        else if (splitStr.get(0).equals("for")){
            splitStr.remove(0);
            String varName = splitStr.get(0);
            int varIndex = getVariable(varName);
            if (varIndex != -1)
                throw new IllegalArgumentException("Variable " + varName + " already defined so cannot be used in for loop");
            else {
                Variable var = new Variable(varName, "0");
                variables.add(var);
                forVar = var;
            }
            String val = splitStr.get(splitStr.size()-1);
            if (isInt(val))
                forLoops = Integer.parseInt(val);
            else {
                Variable v = variables.get(getVariable(val));

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

    /**
     * Displays the parse tree.
     */
    private static void displayParseTree(){
        Tree parseTree = new Tree();
        int i = 1;
        parseTree.add("program");
        while (!parseTreeQueue.isEmpty()) {
            Tree subTree = new Tree();
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
    /**
     * Checks if a string contains an operator.
     * @param str The string to check.
     * @return true if the string contains an operator, false otherwise.
     */
    private static Boolean containsOperator(String[] str){
        for (String s : str){
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
                return true;
        }
        return false;
    }
    /**
     * Inserts a value into the parse tree.
     * @param parseTree The parse tree.
     * @param parent The parent node.
     * @param value The value to insert.
     */
    private static void insertValue(Tree parseTree, String parent, String value){
        parseTree.insert(parent, "value");
        if (isInt(value))
            parseTree.insert("value","INT: " +  value);
        else if (isFloat(value))
            parseTree.insert("value","FLOAT: " +  value);
        else
            parseTree.insert("value","VARIABLE: " +  value);

        parseTree.getNode("value").setNumberOfChildren(1);
    }
    /**
     * Inserts an operator into the parse tree.
     * @param parseTree The parse tree.
     * @param parent The parent node.
     * @param operator The operator to insert.
     */
    private static void insertOperator(Tree parseTree, String parent, String operator){
        if (operator.equals("+"))
            parseTree.insert(parent, "PLUS: " +  operator);
        else if (operator.equals("-"))
            parseTree.insert(parent, "MINUS:" +  operator);
        else if (operator.equals("*"))
            parseTree.insert(parent, "MULTIPLY: " +  operator);
        else if (operator.equals("/"))
            parseTree.insert(parent, "DIVIDE: " + operator);
    }
    /**
     * Inserts a comparison operator into the parse tree.
     * @param parseTree The parse tree.
     * @param parent The parent node.
     * @param operator The comparison operator to insert.
     */
    private static void insertComparisonOperator(Tree parseTree, String parent, String operator){
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
    /**
     * Substitutes variables for their values in a string.
     * @param str The string to substitute.
     * @return The substituted string.
     */
    private static String SubstituteVariablesForValues(String str) {
        for (int i = 0; i < variables.size(); i++) {
            str = str.replaceAll(variables.get(i).name, variables.get(i).getValue());
        }
        return str;
    }
    /**
     * Evaluates a boolean expression.
     * @param expression The expression to evaluate.
     * @return The result of the evaluation.
     */
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
    /**
     * Evaluates an arithmetic expression.
     * @param expression The expression to evaluate.
     * @return The result of the evaluation.
     */
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

    /**
     * Returns the precedence of an operator.
     * @param op The operator.
     * @return The precedence of the operator.
     */
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

    /**
     * Applies an operator to two operands.
     * @param a The first operand.
     * @param b The second operand.
     * @param op The operator.
     * @return The result of the operation.
     */
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
    /**
     * Returns the index of a variable in the variables list.
     * @param str The name of the variable.
     * @return The index of the variable, or -1 if the variable is not found.
     */
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
    /**
     * The Variable class represents a variable in the program.
     */
    private static class Variable {
        public String name;
        public String type;
        public String strValue;
        public int intValue;
        public float floatValue;


        /**
         * Constructor for the Variable class.
         * @param name The name of the variable.
         * @param value The value of the variable.
         */
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
    /**
     * Prints all variables.
     */
    private static void PrintVariables() {
        for (Variable v : variables) {
            System.out.println(v.name + " " + v.getValue());
        }
    }




}
