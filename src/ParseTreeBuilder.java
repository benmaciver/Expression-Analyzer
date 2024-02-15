import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import javax.swing.*;
import java.awt.*;

public class ParseTreeBuilder {
    private ParseTree parseTree;

    public void buildParseTree(String input, exprParser parser) {
        ANTLRInputStream inputStream = new ANTLRInputStream(input);
        CommonTokenStream tokens = new CommonTokenStream(new exprLexer(inputStream));
        parseTree = (ParseTree) parser.program();
    }

    public void displayParseTree() {
        if (parseTree != null) {
            JFrame frame = new JFrame("Parse Tree Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);


            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Use parseTree to draw the parse tree graphically
                }
            };

            frame.add(panel);
            frame.setVisible(true);
        } else {
            System.out.println("Parse tree is not built yet.");
        }
    }

    // Other methods for processing the parse tree if needed
}
