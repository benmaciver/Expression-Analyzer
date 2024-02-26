import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import java.util.Arrays;

/**
 * This class extends the exprBaseVisitor class and overrides its methods to provide
 * a custom implementation for visiting the nodes of the parse tree.
 */
public class PrettyVisitor extends exprBaseVisitor<String> {
    private String evaluateableCode = "";

    /**
     * Visits the Program node of the parse tree.
     * @param ctx The context of the Program node.
     * @return The string representation of the Program node.
     */
    @Override
    public String visitProgram(exprParser.ProgramContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (exprParser.StatementContext statementContext : ctx.statement()) {
            builder.append(this.visitStatement(statementContext)).append("\n");
        }
        evaluateableCode = builder.toString();
        return convertToLISP(builder.toString()).strip();
    }

    /**
     * Returns the evaluateable code.
     * @return The evaluateable code.
     */
    public String GetEvaluateableCode() {
        return evaluateableCode.strip();
    }

    /**
     * Visits the Statement node of the parse tree.
     * @param ctx The context of the Statement node.
     * @return The string representation of the Statement node.
     */
    @Override
    public String visitStatement(exprParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return visitAssignment(ctx.assignment());
        } else if (ctx.print() != null) {
            return visitPrint(ctx.print());
        } else if (ctx.control_statement() != null) {
            return visitControl_statement(ctx.control_statement());
        } else {
            return "";
        }
    }

    /**
     * Visits the Assignment node of the parse tree.
     * @param ctx The context of the Assignment node.
     * @return The string representation of the Assignment node.
     */
    @Override
    public String visitAssignment(exprParser.AssignmentContext ctx) {
        if (ctx.value() != null) {
            return ctx.VARIABLE().getText() + " = " + visit(ctx.value());
        } else if (ctx.arithmetic() != null) {
            return ctx.VARIABLE().getText() + " = " + visit(ctx.arithmetic());
        } else if (ctx.STRING() != null) {
            return ctx.VARIABLE().getText() + " = " + ctx.STRING().getText();
        } else {
            return ctx.VARIABLE().getText() + " = null";
        }
    }

    /**
     * Visits the Print node of the parse tree.
     * @param ctx The context of the Print node.
     * @return The string representation of the Print node.
     */
    @Override
    public String visitPrint(exprParser.PrintContext ctx) {
        if (ctx.value() != null) {
            return "print>> " + visit(ctx.value());
        } else if (ctx.arithmetic() != null) {
            return "print>> " + visit(ctx.arithmetic());
        } else if (ctx.STRING() != null) {
            return "print>> " + ctx.STRING().getText();
        } else {
            return "print>> null";
        }
    }

    /**
     * Visits the Arithmetic node of the parse tree.
     * @param ctx The context of the Arithmetic node.
     * @return The string representation of the Arithmetic node.
     */
    @Override
    public String visitArithmetic(exprParser.ArithmeticContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ctx.value().size(); i++) {
            builder.append(visit(ctx.value(i)));
            if (i < ctx.value().size() - 1) {
                String operator = ctx.getChild(2*i+1).getText();
                builder.append(" ").append(operator).append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * Visits the Value node of the parse tree.
     * @param ctx The context of the Value node.
     * @return The string representation of the Value node.
     */
    @Override
    public String visitValue(exprParser.ValueContext ctx) {
        if (ctx.FLOAT() != null) {
            return ctx.FLOAT().getText();
        } else if (ctx.INT() != null) {
            return ctx.INT().getText();
        } else {
            return ctx.VARIABLE().getText();
        }
    }

    /**
     * Visits the Control_statement node of the parse tree.
     * @param ctx The context of the Control_statement node.
     * @return The string representation of the Control_statement node.
     */
    @Override
    public String visitControl_statement(exprParser.Control_statementContext ctx) {
        if (ctx.while_loop() != null) {
            return visitWhile_loop(ctx.while_loop());
        } else if (ctx.if_statemnt() != null) {
            return visitIf_statemnt(ctx.if_statemnt());
        } else if (ctx.for_loop() != null) {
            return visitFor_loop(ctx.for_loop());
        } else {
            return "";
        }
    }

    /**
     * Visits the While_loop node of the parse tree.
     * @param ctx The context of the While_loop node.
     * @return The string representation of the While_loop node.
     */
    @Override
    public String visitWhile_loop(exprParser.While_loopContext ctx) {
        return "while " + ctx.boolean_().getText();
    }

    /**
     * Visits the If_statemnt node of the parse tree.
     * @param ctx The context of the If_statemnt node.
     * @return The string representation of the If_statemnt node.
     */
    @Override
    public String visitIf_statemnt(exprParser.If_statemntContext ctx) {
        return "if " + ctx.boolean_().getText();
    }

    /**
     * Visits the For_loop node of the parse tree.
     * @param ctx The context of the For_loop node.
     * @return The string representation of the For_loop node.
     */
    @Override
    public String visitFor_loop(exprParser.For_loopContext ctx) {
        if (ctx.INT() == null) {
            return "for " + ctx.VARIABLE(0) + " in " + ctx.VARIABLE(1);
        }
        else return "for " + ctx.VARIABLE(0) + " in " + ctx.INT();

    }

    /**
     * Visits the Boolean node of the parse tree.
     * @param ctx The context of the Boolean node.
     * @return The string representation of the Boolean node.
     */
    @Override
    public String visitBoolean(exprParser.BooleanContext ctx) {
        return visit(ctx.value(0)) + " " + ctx.getChild(1).getText() + " " + visit(ctx.value(1));
    }
    //Converts an expression to list format
    private String convertToLISP(String line){
        String[] parts = line.split(" ");
        String partsMerged = String.join("", parts);
        String output = "(";
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
    }
    private Boolean ContainsOperator(String str) {
        return str.contains("+") || str.contains("-") || str.contains("*") || str.contains("/");
    }
    private String[] SubStringArray(String[] arr, int start) {
        return Arrays.copyOfRange(arr, start, arr.length);
    }
    //Takes an arithmetic expression and converts it to LISP format
    private String ConvertArithmeticToLISP(String expression) {
        String output = expression.replaceAll(" ", "");
        int[] divIndexes = FindAll(output, '/');
        int[] multIndexes = FindAll(output, '*');
        int[] addIndexes = FindAll(output, '+');
        int[] subIndexes = FindAll(output, '-');
        output = CalculateOutput(addIndexes, '+', output);
        output = CalculateOutput(subIndexes, '-', output);
        output = CalculateOutput(multIndexes, '*', output);
        output = CalculateOutput(divIndexes, '/', output);


        return output;

    }
    //Helper moethod for ConvertArithmeticToLISP
    private String CalculateOutput(int[] operatorIndexes, Character operator, String input){
        String output = input;

        for (int i = 0; i < operatorIndexes.length; i++){
            String part1="";
            String part2="";
            if (output.charAt(operatorIndexes[i]+1) == '('){
                part2 = output.substring(operatorIndexes[i]+1);
                int closingIndex = part2.indexOf(')');
                part2 = part2.substring(0, closingIndex+1);
            }
//            else part1 = output.charAt(operatorIndexes[i]-1) + "";
            else{
                String subString = output.substring(operatorIndexes[i]+1);
                subString = subString.strip();
                int decCount=0;
                for (Character c : subString.toCharArray()) {


                    if (c == '.'){
                        decCount++;
                        if (decCount > 1)
                            break;
                        else {
                            part2+=c;
                        }

                    }
                    else if (!isPunctOrWhitespace(c)) {
                        part2 += c;

                    }
                    else break;
                }
            }
            if (output.charAt(operatorIndexes[i]-1) == ')'){
                part1 = output.substring(0, operatorIndexes[i]-1);
                int openingIndex = part1.lastIndexOf('(');
                part1 = part1.substring(openingIndex);
            }
            else{
                String subString = output.substring(0, operatorIndexes[i]);
                int decCount=0;
                for (Character c : subString.toCharArray()) {

                    if (c == '.'){
                        decCount++;
                        if (decCount > 1)
                            break;
                        else {
                            part1+=c;

                        }
                    }
                    else if (!isPunctOrWhitespace(c)) {
                        part1 += c;

                    }
                    else break;
                }

            }
            if (part1.equals("") || part2.equals(""))
                throw new IllegalArgumentException("Invalid expression");
            String subString = part1 + operator + part2;
            String replacement = "(" + operator + " " + part1 +  " " + part2 + ")";
            output = output.strip();
            if (output.equals(subString)) {
                output = replacement;
            } else {
                output = output.replaceFirst(subString, replacement);
            }


        }
        return output;

    }
    //Takes a boolean expression and converts it to LISP format
    private String ConvertBooleanToLISP(String expression){
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
    //Finds all occurrences of a character in a string
    private int[] FindAll(String str,Character c){
        int[] output = new int[str.length()];
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                output[index++] = i;
        }
        return Arrays.copyOf(output, index);
    }
    //Checks if an array is empty
    private Boolean ArrayIsEmpty(int[] arr){
        Boolean empty = true;
        for (int i : arr) {
            if (i != -1)
                empty = false;
        }
        return empty;
    }
    //Checks if a character is punctuation or whitespace
    private Boolean isPunctOrWhitespace(Character c) {
        return c == ' ' || c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':' || c == '(' || c == ')';
    }
}