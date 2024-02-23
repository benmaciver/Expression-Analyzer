public class TestMain {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        tree.add("Root");
        tree.insert("Root", "Child1"); // Insert as first child of Root
        tree.insert("Root", "Child2"); // Insert as second child of Root
        tree.insert("Root", "Child3"); // Insert as third child of Root
        tree.insert("Child1", "Grandchild1"); // Insert as first child of Child1
        tree.insert("Child2", "Grandchild2"); // Insert as second child of Child1


        BinaryTree tree2 = new BinaryTree();
        tree2.add("Root2");
        tree2.insert("Root2", "Child4"); // Insert as first child of Root
        tree2.insert("Root2", "Child5"); // Insert as second child of Root

        tree.joinTrees(tree2);

        System.out.println("Displaying the tree:");
        tree.display();
    }
}