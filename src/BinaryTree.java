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
    public void joinTrees(BinaryTree tree){
        this.root.addChild(tree.root);
    }

    private Node findNode (String nodeData, Node root) {
        if (root == null) return null;

        if ( root.data.equals(nodeData)) {
            return root;
        } else {
            for (int i = 0; i < root.getNumberOfChildren(); i++) {
                Node foundNode = findNode(nodeData, root.getChild(i));
                if (foundNode != null && !foundNode.maxChildren()) {
                    return foundNode;
                }
            }
        }
        return null;

    }

    private void insert(Node node, String data) {
        node.addChild(new Node(data));
//        System.out.println("Inserted " + data + " as child of " + node.data);
//        System.out.println("Number of children of " + node.data + " is " + node.getNumberOfChildren());
    }
    public Node getNode(String node){
        return findNode(node,root);
    }

    public void display() {
        JFrame frame = new JFrame("Binary Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JScrollPane with both horizontal and vertical scrollbars
        TreePanel panel = new TreePanel(root);
        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setPreferredSize(new Dimension(20000, 20000));
        frame.add(scrollPane);

        frame.setSize(500, 500); // Size the frame, not the TreePanel
        frame.setVisible(true);
    }


    public static class Node {
        String data;
        Node[] children;

        public Node(String data) {
            this.data = data;
            children = new Node[10];
        }
        public Node addChild(Node child){
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    children[i] = child;
                    return child;
                }
            }
            return null;
        }
        public boolean maxChildren(){
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    return false;
                }
            }
            return true;
        }

        public void doubleChildren(){
            children = Arrays.copyOf(children, children.length * 2);
        }
        public Node getChild(int index){
            return children[index];
        }
        public void setNumberOfChildren(int number){
            children = Arrays.copyOf(children, number);
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
            drawTree(g, root, getWidth() / 2, 10,true);
        }

        private void drawTree(Graphics g, Node node, int x, int y,boolean firstRecursion) {
            if (node != null) {
                int leftX = (x - (int) (Math.abs(Math.pow(node.data.length(), 1.5)))) / 2;
                int rightX = x + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2;

                g.drawString(node.data, x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2, y);
//                System.out.println(node.getNumberOfChildren());
                for (int i = 0; i < node.getNumberOfChildren(); i++){
                    int a;
                    if (firstRecursion)
                        a = 4;
                    else a = 1;
                    g.drawLine(x, y + 20, x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2 + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / (node.getNumberOfChildren() + 1) * (((100*i)*a) + 1), y + 100);
                    drawTree(g, node.getChild(i), x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2 + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / (node.getNumberOfChildren() + 1) * ((100*(i)*a) + 1), y + 100,false);
                }

            }
        }
    }
}