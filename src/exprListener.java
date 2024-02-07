// Generated from C:/Users/Ben/Documents/Asignment1/src/expr.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link exprParser}.
 */
public interface exprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link exprParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(exprParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(exprParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(exprParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(exprParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(exprParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(exprParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(exprParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(exprParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(exprParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(exprParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(exprParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(exprParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(exprParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(exprParser.ValueContext ctx);
}