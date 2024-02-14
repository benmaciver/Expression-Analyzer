import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class BinaryTree {
    private TreeNode root;

    public void add(int val) {
        root = insert(root, val);
    }

    private TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insert(root.left, val);
        } else if (val > root.val) {
            root.right = insert(root.right, val);
        }

        return root;
    }

    public void inorderTraversal() {
        inorder(root);
    }

    private void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.val + " ");
            inorder(root.right);
        }
    }

    public void preorderTraversal() {
        preorder(root);
    }

    private void preorder(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    public void postorderTraversal() {
        postorder(root);
    }

    private void postorder(TreeNode root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.val + " ");
        }
    }

    public void displayTree() {
        JFrame frame = new JFrame("Binary Tree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, getWidth() / 2, 30, root, 300);
            }
        };

        panel.setPreferredSize(new Dimension(3000, 2000));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void drawTree(Graphics g, int x, int y, TreeNode node, int xOffset) {
        if (node == null)
            return;

        g.setColor(Color.BLACK);
        g.fillOval(x - 10, y - 10, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(node.val), x - 5, y + 5);

        if (node.left != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - xOffset, y + 50);
            drawTree(g, x - xOffset, y + 50, node.left, xOffset / 2);
        }

        if (node.right != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + xOffset, y + 50);
            drawTree(g, x + xOffset, y + 50, node.right, xOffset / 2);
        }
    }

//    public static void main(String[] args) {
//        BinaryTree tree = new BinaryTree();
//        tree.add(5);
//        tree.add(3);
//        tree.add(7);
//        tree.add(1);
//        tree.add(4);
//        tree.add(6);
//        tree.add(8);
//
//        tree.displayTree();
//    }
}