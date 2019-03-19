
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {

    Node root;

    public BinaryTree() {
        root = new Node(5);
        addRecursive(root, 0);
    }

    
    public static void main(String... args) {
        new BinaryTree();
            
    }
    
    

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    public void traverseLevelOrder() {
        if (root == null) {
            return;
        }

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {

            Node node = nodes.remove();

            System.out.print(" " + node.value);

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
        }
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    private BinaryTree createBinaryTree() {
        BinaryTree bt = new BinaryTree();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        return bt;

    }

    private boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.value) {
            return true;
        }
        return value < current.value
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

//    @Test
//public void givenABinaryTree_WhenAddingElements_ThenTreeContainsThoseElements() {
//    BinaryTree bt = createBinaryTree();
// 
//    assertTrue(bt.containsNode(6));
//    assertTrue(bt.containsNode(4));
//  
//    assertFalse(bt.containsNode(1));
//}
}

class Node {

    int value;
    Node left;
    Node right;

    Node(int value) {
        this.value = value;
        right = null;
        left = null;
    }
}
