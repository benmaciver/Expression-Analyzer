import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // write your code here
        CharStream in = CharStreams.fromFileName("./src/test.cc");
        exprLexer lexer = new exprLexer(in);  //create a lexer object
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        exprParser parser = new exprParser(tokens);

        String str = new PrettyVisitor().visit(parser.program());
        System.out.println(str);
    }


}
