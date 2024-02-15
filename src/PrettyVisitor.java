import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class PrettyVisitor extends exprBaseVisitor<String> {

    @Override
    public String visitProgram(exprParser.ProgramContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (exprParser.StatementContext statementContext : ctx.statement()) {
            builder.append(this.visitStatement(statementContext)).append("\n");
        }
        return builder.toString();
    }

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

    @Override
    public String visitWhile_loop(exprParser.While_loopContext ctx) {
        return "while " + ctx.boolean_().getText();
    }

    @Override
    public String visitIf_statemnt(exprParser.If_statemntContext ctx) {
        return "if " + ctx.boolean_().getText();
    }

    @Override
    public String visitFor_loop(exprParser.For_loopContext ctx) {
        if (ctx.INT() == null) {
            return "for " + ctx.VARIABLE(0) + " in " + ctx.VARIABLE(1);
        }
        else return "for " + ctx.VARIABLE(0) + " in " + ctx.INT();

    }

    @Override
    public String visitBoolean(exprParser.BooleanContext ctx) {
        return visit(ctx.value(0)) + " " + ctx.getChild(1).getText() + " " + visit(ctx.value(1));
    }
}
