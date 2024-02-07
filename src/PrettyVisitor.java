import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class PrettyVisitor extends exprBaseVisitor<String> {

    @Override
    public String visitProgram(exprParser.ProgramContext ctx) {
        //return visit(ctx.statement());
        return ctx.statement(0) == null ? "" : "[ " + this.visitStatement(ctx.statement(0)) + " ]";
    }

    @Override
    public String visitStatement(exprParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        } else {
            return visit(ctx.print());
        }
    }

    @Override
    public String visitAssignment(exprParser.AssignmentContext ctx) {
        if (ctx.value() != null) {
            return ctx.VARIABLE().getText() + " = " + visit(ctx.value());
        } else if (ctx.arithmetic() != null) {
            return ctx.VARIABLE().getText() + " = " + visit(ctx.arithmetic());
        } else if (ctx.operation() != null) {
            return ctx.VARIABLE().getText() + " = " + visit(ctx.operation());
        } else if (ctx.STRING() != null) {
            return ctx.VARIABLE().getText() + " = " + ctx.STRING().getText();
        } else {
            return ctx.VARIABLE().getText() + " = null";
        }
    }

    @Override
    public String visitPrint(exprParser.PrintContext ctx) {
        if (ctx.value() != null) {
            return "print>> " + visit(ctx.value());
        } else if (ctx.arithmetic() != null) {
            return "print>> " + visit(ctx.arithmetic());
        } else if (ctx.operation() != null) {
            return "print>> " + visit(ctx.operation());
        } else if (ctx.VARIABLE() != null) {
            return "print>> " + ctx.VARIABLE().getText();
        } else if (ctx.STRING() != null) {
            return "print>> " + ctx.STRING().getText();
        } else {
            return "print>> null";
        }
    }
    @Override
    public String visitArithmetic(exprParser.ArithmeticContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ctx.value().size(); i++) {
            builder.append(visit(ctx.value(i)));
            if (i < ctx.value().size() - 1) {
                builder.append(ctx.PLUS() != null ? " + " : " - ");
            }
        }
        return builder.toString();
    }

    @Override
    public String visitOperation(exprParser.OperationContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ctx.value().size(); i++) {
            builder.append(visit(ctx.value(i)));
            if (i < ctx.value().size() - 1) {
                builder.append(ctx.MULTIPLY() != null ? " * " : " / ");
            }
        }
        return builder.toString();
    }

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
}