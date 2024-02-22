// Generated from C:/Users/Ben/Documents/Asignment1/src/expr.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link exprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface exprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link exprParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(exprParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(exprParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#control_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControl_statement(exprParser.Control_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#while_loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_loop(exprParser.While_loopContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#if_statemnt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statemnt(exprParser.If_statemntContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#for_loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_loop(exprParser.For_loopContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(exprParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(exprParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#boolean}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(exprParser.BooleanContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#arithmetic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(exprParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(exprParser.ValueContext ctx);
}