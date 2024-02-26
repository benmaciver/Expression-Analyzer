import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * This class represents a Tree data structure.
 * Each Tree has a root Node.
 */
public class Tree {

    private Node root;

    /**
     * Constructor for the Tree class.
     * Initializes the root to null.
     */
    public Tree() {
        this.root = null;
    }

    /**
     * Adds a new Node with the given data to the Tree.
     * If the Tree is empty, the new Node becomes the root.
     * Otherwise, the new Node is inserted as a child of the root.
     * @param data The data for the new Node.
     */
    public void add(String data) {
        if (root == null) {
            root = new Node(data);
        } else {
            insert(root, data);
        }
    }

    /**
     * Inserts a new Node with the given data as a child of the Node with the given parent data.
     * @param parent The data of the parent Node.
     * @param child The data for the new Node.
     */
    public void insert(String parent, String child){
        Node node = findNode(parent,root);
        insert(node,child);
    }

    /**
     * Joins another Tree to this Tree by adding the root of the other Tree as a child of this Tree's root.
     * @param tree The other Tree.
     */
    public void joinTrees(Tree tree){
        this.root.addChild(tree.root);
    }

    /**
     * Finds a Node with the given data in the Tree.
     * @param nodeData The data of the Node to find.
     * @param root The root of the Tree to search.
     * @return The Node with the given data, or null if no such Node exists.
     */
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

    /**
     * Inserts a new Node with the given data as a child of the given Node.
     * @param node The parent Node.
     * @param data The data for the new Node.
     */
    private void insert(Node node, String data) {
        node.addChild(new Node(data));
    }

    /**
     * Returns a Node with the given data.
     * @param node The data of the Node to return.
     * @return The Node with the given data.
     */
    public Node getNode(String node){
        return findNode(node,root);
    }

    /**
     * Displays the Tree in a new JFrame.
     */
    public void display() {
        JFrame frame = new JFrame("Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreePanel panel = new TreePanel(root);
        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setPreferredSize(new Dimension(20000, 20000));
        frame.add(scrollPane);

        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    /**
     * This class represents a Node in the Tree.
     * Each Node has a data field and an array of children Nodes.
     */
    public static class Node {
        String data;
        Node[] children;

        /**
         * Constructor for the Node class.
         * Initializes the data field with the given data and the children array with a size of 10.
         * @param data The data for the Node.
         */
        public Node(String data) {
            this.data = data;
            children = new Node[10];
        }

        /**
         * Adds a new child Node to this Node.
         * @param child The new child Node.
         * @return The new child Node, or null if this Node already has the maximum number of children.
         */
        public Node addChild(Node child){
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    children[i] = child;
                    return child;
                }
            }
            return null;
        }

        /**
         * Checks if this Node has the maximum number of children.
         * @return true if this Node has the maximum number of children, false otherwise.
         */
        public boolean maxChildren(){
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Doubles the size of the children array.
         */
        public void doubleChildren(){
            children = Arrays.copyOf(children, children.length * 2);
        }

        /**
         * Returns the child Node at the given index.
         * @param index The index of the child Node to return.
         * @return The child Node at the given index.
         */
        public Node getChild(int index){
            return children[index];
        }

        /**
         * Sets the number of children of this Node.
         * @param number The new number of children.
         */
        public void setNumberOfChildren(int number){
            children = Arrays.copyOf(children, number);
        }

        /**
         * Returns the number of children of this Node.
         * @return The number of children of this Node.
         */
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

    /**
     * This class represents a JPanel for displaying the Tree.
     */
    private static class TreePanel extends JPanel {

        private Node root;

        /**
         * Constructor for the TreePanel class.
         * Initializes the root with the given Node.
         * @param root The root of the Tree to display.
         */
        public TreePanel(Node root) {
            this.root = root;
        }

        /**
         * Paints the Tree on the JPanel.
         * @param g The Graphics object to protect.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, root, getWidth() / 2, 10,true);
        }

        /**
         * Draws a Node and its children on the JPanel.
         * @param g The Graphics object to protect.
         * @param node The Node to draw.
         * @param x The x-coordinate of the Node.
         * @param y The y-coordinate of the Node.
         * @param firstRecursion Whether this is the first recursion of the method.
         */
        private void drawTree(Graphics g, Node node, int x, int y,boolean firstRecursion) {
            if (node != null) {
                int leftX = (x - (int) (Math.abs(Math.pow(node.data.length(), 1.5)))) / 2;
                int rightX = x + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2;

                g.drawString(node.data, x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2, y);
                for (int i = 0; i < node.getNumberOfChildren(); i++){
                    int a;
                    if (firstRecursion)
                        a = 6;
                    else a = 1;
                    g.drawLine(x, y + 20, x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2 + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / (node.getNumberOfChildren() + 1) * (((100*i)*a) + 1), y + 100);
                    drawTree(g, node.getChild(i), x - (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / 2 + (int) (Math.abs(Math.pow(node.data.length(), 1.5))) / (node.getNumberOfChildren() + 1) * ((100*(i)*a) + 1), y + 100,false);
                }

            }
        }
    }
}