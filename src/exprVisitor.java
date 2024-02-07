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
	 * Visit a parse tree produced by {@link exprParser#arithmetic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(exprParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperation(exprParser.OperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(exprParser.ValueContext ctx);
}