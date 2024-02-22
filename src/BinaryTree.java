import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class BinaryTree {

    private Node root;

    public BinaryTree() {
        this.root = null;
    }

    public void add(String data) {
        if (root == null) {
            root = new Node(data);
        } else {
            insert(root, data);
        }
    }
    public void insert(String parent, String child){
        Node node = findNode(parent,root);
        insert(node,child);

    }

    private Node findNode (String nodeData, Node root) {
        if (root.data.equals(nodeData)) {
            return root;
        } else {
            for (int i = 0; i < root.getNumberOfChildren(); i++) {
                Node foundNode = findNode(nodeData, root.getChild(i));
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;

    }

    private void insert(Node node, String data) {
        node.addChild(new Node(data));
    }

    public void display() {
        JFrame frame = new JFrame("Binary Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new TreePanel(root));
        frame.setVisible(true);
    }

    private static class Node {
        String data;
        Node[] children;

        public Node(String data) {
            this.data = data;
            children = new Node[10];
        }
        public void addChild(Node child){
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    children[i] = child;
                    break;
                }
            }
            int i = children.length;
            children = Arrays.copyOf(children, children.length * 2);
            children[i] = child;
        }
        public Node getChild(int index){
            return children[index];
        }
        public int getNumberOfChildren(){
            int count = 0;
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    count++;
                }
            }
            return count;
        }
    }

    // Inner class for displaying the tree visually
    private static class TreePanel extends JPanel {

        private Node root;

        public TreePanel(Node root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, root, getWidth() / 2, 10);
        }

        private void drawTree(Graphics g, Node node, int x, int y) {
            if (node != null) {
                int leftX = (x - (int) (Math.abs(Math.pow(node.data.length(), 1.5)))) / 2;
                int rightX = x + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2;

                g.drawString(node.data, x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2, y);

                for (int i = 0; i < node.getNumberOfChildren(); i++){
                    g.drawLine(x, y + 20, x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2 + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / (node.getNumberOfChildren() + 1) * (i + 1), y + 50);
                    drawTree(g, node.getChild(i), x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2 + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / (node.getNumberOfChildren() + 1) * (i + 1), y + 50);
                }

            }
        }
    }
}