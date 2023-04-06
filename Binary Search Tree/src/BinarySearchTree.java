import com.sun.xml.internal.ws.api.pipe.SyncStartForAsyncFeature;

import java.sql.SQLSyntaxErrorException;

/**
 * Binary Search Tree
 * @author Jonathan F Da Silva -- jfd69@case.edu
 */
public class BinarySearchTree {

    private Node root;

    private int nodesVisited;

    private Node kthSmallest;

    /**
     * Creates an empty Binary Search Tree
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a key to the Binary Search Tree
     * @param key the key to be inserted in the BST.
     */
    public void insert(int key) {
        Node newNode = new Node(key);                       // Node to be inserted.
        if (root == null) {                                 // If the tree is empty
            setRoot(newNode);                               // Set the tree's root.
        }
        // This part finds where the node should be placed.
        else {                                              // If tree is not empty.
            Node newParent = null;                          // Parent of the new node.
            Node pointer = getRoot();                       // Pointer to the nodes
            while (pointer != null) {
                newParent = pointer;
                if (newNode.getKey() < pointer.getKey()) {  // If newNode is smaller that pointer.
                    pointer = pointer.getLeft();
                } else {
                    pointer = pointer.getRight();
                }
            }

            // This part connects the parent with the new child.
            if (newNode.getKey() < newParent.getKey()) {    // If newNode is smaller
                newParent.setLeft(newNode);                 // Set newNode as newParent's left child.
            }
            else {
                newParent.setRight(newNode);
            }
        }
    }

    /**
     * Creates a binary tree from a list of keys
     * @param values the list of keys to be inserted in the Binary Tree.
     */
    public void createTree(int[] values) {
        setRoot(null);
        for (int i = 0; i < values.length; i++) {
            insert(values[i]);
        }
    }

    /**
     * Searches for a key inside the BST
     * @param key the key to be searched
     * @return Return true if key was found and false if it is not in the BST.
     */
    public boolean search(int key) {
        Node pointer = getRoot();
        // Loop traverses in the BST comparing nodes.
        while (pointer != null) {
            if (pointer.getKey() == key) {
                return true;
            } else if (key < pointer.getKey()) {
                pointer = pointer.getLeft();
            }
            else pointer = pointer.getRight();
        }
        return false;
    }

    /**
     * Deletes a key from the BST
     * @param key the key to be deleted in the BST
     * @return Return the key deleted or null if not found.
     */
    public Node delete(int key) {
        // If BST is empty return null
        if (root == null) {
            return null;
        }

        // Traverses through the BST to find the key to be deleted and its parent.
        Node parent = null;
        Node toDelete = getRoot();
        while (toDelete != null && toDelete.getKey() != key) {
            parent = toDelete;
            if (key < toDelete.getKey()) {
                toDelete = toDelete.getLeft();
            }
            else toDelete = toDelete.getRight();
        }

        // If toDelete was not found, hence null.
        if (toDelete == null) {
            return null;
        }
        else {
            return deleteNode(toDelete, parent);
        }
    }

    /**
     * Prints the BST In Order traversal.
     */
    public void inorderRec() {
        if (getRoot() != null) {
            inOrder(getRoot());
        }
        System.out.println("");
    }

    /**
     * Prints the BST in Pre Order traversal.
     */
    public void preorderRec() {
        if (getRoot() != null) {
            preOrder(getRoot());
        }
        System.out.println("");
    }

    /**
     * Prints the BST in Post Order traversal.
     */
    public void postorderRec() {
        if (getRoot() != null) {
            postOrder(getRoot());
        }
        System.out.println("");
    }

    /**
     * Return the Kth smallest key in the BST
     * @param k the kth value
     * @return return the smallest key according to k.
     */
    public Node kthSmallest(int k) {
        nodesVisited = 0;
        if (getRoot() != null)
            inOrder(getRoot(), k);
        return kthSmallest;
    }

    /**
     * Helper method to delete a node.
     * @param toDelete node to be deleted
     * @param parent parent of the node to be deleted
     * @return returns the deleted node
     */
    private Node deleteNode(Node toDelete, Node parent) {
        Node copy = new Node(toDelete.getKey());                // Save a copy of the node to be deleted
        // If toDelete have no children or just one.
        if (toDelete.getLeft() == null || toDelete.getRight() == null) {
            Node toDeleteChild = null;
            if (toDelete.getLeft() != null) {                   // If toDelete have a left child
                toDeleteChild = toDelete.getLeft();
            }
            else toDeleteChild = toDelete.getRight();           // If only have right child

            if (toDelete.getKey() < parent.getKey()) {      // If not, if toDelete is smalller than parent.
                parent.setLeft(toDeleteChild);                  // Set parent's left toDeleteChild
            }
            else parent.setRight(toDeleteChild);                // Otherwise set parent's right toDeleteChild
        }
        else {
            Node rightSmallestParent = toDelete;                // Saves the right smallest parent
            Node rightSmallest = toDelete.getRight();           // Saves the right smallest node
            while (rightSmallest.getLeft() != null) {           // While there is still a left child
                rightSmallestParent = rightSmallest;
                rightSmallest = rightSmallest.getLeft();        // Copy rightSmallest Key
            }
            toDelete.setKey(rightSmallest.getKey());
            if (rightSmallest.getKey() == toDelete.getRight().getKey()) {         // If the rightSmallest is the next right node.
                toDelete.setRight(rightSmallest.getRight());
            } else {                                            // If the rightSmallest is the left child of a node.
                rightSmallestParent.setLeft(rightSmallest.getRight());
            }
        }
        return copy;                                            // Return the copy of toDelete node.
    }

    /**
     * Helper method find the kth smallest key in the key.
     * @param root the root of the BST
     * @param k the kth smallest element
     */
    private void inOrder(Node root, int k) {
        if (root.getLeft() != null)
            inOrder(root.getLeft(), k);
        nodesVisited += 1;
        if (nodesVisited == k)
            kthSmallest = root;
        if (root.getRight() != null)
            inOrder(root.getRight(), k);
    }

    /**
     * Helper method to print a BST in order.
     * @param root the root of the BST
     */
    private void inOrder(Node root) {
        if (root.getLeft() != null)
            inOrder(root.getLeft());
        System.out.print(root.getKey() + " ");
        if (root.getRight() != null)
            inOrder(root.getRight());
    }

    /**
     * Helper method print the BST in pre-order traversal.
     * @param root the root of the tree.
     */
    private void preOrder(Node root) {
        System.out.print(root.getKey() + " ");
        if (root.getLeft() != null)
            preOrder(root.getLeft());
        if (root.getRight() != null)
            preOrder(root.getRight());
    }

    /**
     * Helper method to print the BST in post order traversa;.
     * @param root the root of the tree.
     */
    private void postOrder(Node root) {
        if (root.getLeft() != null)
            postOrder(root.getLeft());
        if (root.getRight() != null)
            postOrder(root.getRight());
        System.out.print(root.getKey() + " ");
    }

    // Return the root of the tree
    private Node getRoot() {
        return root;
    }

    // Set a root for the tree. Only used to create a new tree.
    private void setRoot(Node root) {
        this.root = root;
    }


    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        /*Testing void insert(int key)
         * 1. Insert in an empty tree
         * 2. Insert in the left of a parent
         * 3. Insert in the right of a parent*/
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(8);
        System.out.println("Testing insert(int key):");
        System.out.println("Expected: 5, Result --> " + bst.getRoot());
        System.out.println("Expected: 3, Result --> " + bst.getRoot().getLeft());
        System.out.println("Expected: 7, Result --> " + bst.getRoot().getRight());
        System.out.println("Expected: 8, Result --> " + bst.getRoot().getRight().getRight() + "\n");

        /* Testing void createTree(int[] values)
         * 1. Creating a BST with previous BST
         * 2. Creating a BST without previous BST */
        int[] values = {4, 1, 5, 7};
        int[] values1 = {7, 4, 10, 8, 2, 5, 9, 6};
        bst.createTree(values);
        System.out.println("Testing createTree(int[] values):");
        System.out.println("Expected: 4, Result --> " + bst.getRoot());
        System.out.println("Expected: 1, Result --> " + bst.getRoot().getLeft());
        System.out.println("Expected: 5, Result --> " + bst.getRoot().getRight());
        System.out.println("Expected: 7, Result --> " + bst.getRoot().getRight().getRight() + "\n");

        bst.createTree(values1);
        System.out.println("Testing createTree(int[] values) with new values:");
        System.out.println("Expected: 7, Result --> " + bst.getRoot());
        System.out.println("Expected: 4, Result --> " + bst.getRoot().getLeft());
        System.out.println("Expected: 10, Result --> " + bst.getRoot().getRight());
        System.out.println("Expected: 8, Result --> " + bst.getRoot().getRight().getLeft() + "\n");

        /* Testing boolean search(int key)
         * 1. Searching in an empty tree
         * 2. Searching a key that does not exist
         * 3. Searching for a key that exists */
        BinarySearchTree a = new BinarySearchTree();
        System.out.println("Testing search(int key):");
        System.out.println("Searching 1 in a empty tree --> " + a.search(1));

        System.out.println("Searching 1 but it does not exist in the tree --> " + bst.search(1));
        System.out.println("Searching 7 and it exist in the tree --> " + bst.search(7) + "\n");

        /*Testing Node delete(int key) & inorderRec()
         * 1. Tries to delete from an empty tree.
         * 2. Tries to delete but no key found in the tree
         * 3. Tries to delete the root
         * 4. Tries to delete and the node has 0 children
         * 5. Tries to delete and the node has 1 left children
         * 6. Tries to delete and the node has 1 right children
         * 7. Tries to delete and the node has 2 children */
        System.out.println("Testing delete(int key) & inorderRec():");
        // 1.
        System.out.println("Deleting 1, in empty tree --> " + a.delete(1));
        // 2.
        System.out.println("Deleting 3, it does not exist --> " + bst.delete(3));
        // 3.
        System.out.print("Deleted: " + bst.delete(7) + ", Tree result --> ");
        bst.inorderRec();
        // 4.
        System.out.print("Deleted: " + bst.delete(9) + ", Tree result --> ");
        bst.inorderRec();
        // 5.
        System.out.print("Deleted: " + bst.delete( 10) + ", Tree result --> ");
        bst.inorderRec();
        // 6.
        System.out.print("Deleted: " + bst.delete(5) + ", Tree result --> ");
        bst.inorderRec();
        // 7.
        System.out.print("Deleted: " + bst.delete(4) + ", Tree result --> ");
        bst.inorderRec();
        System.out.println("");

        /* Testing preorderRec()*/
        BinarySearchTree bst2 = new BinarySearchTree();
        int[] values3 = {6, 4, 8, 7, 3, 5 , 9};
        bst2.createTree(values3);
        System.out.println("The following 3 tests uses this tree: \n" + "\t\t  6\n" +
                "\t     / \\\n" +
                "\t    4   8\n" +
                "\t   / \\ / \\\n" +
                "\t  3  5 7  9");
        System.out.println("Testing preorderRec()");
        bst2.preorderRec();

        /*Testing postorderRec()*/
        System.out.println("Testing postorderRec()");
        bst2.postorderRec();

        /*Testing kthSmallest(int k)*/
        System.out.println("Testing for kthSmallest()" + "\n" +
                "1st smallest " + bst2.kthSmallest(1) + "\n" +
                "2nd smallest " + bst2.kthSmallest(2) + "\n" +
                "3rd smallest " + bst2.kthSmallest(3) + "\n" +
                "4th smallest " + bst2.kthSmallest(4) + "\n" +
                "5th smallest " + bst2.kthSmallest(5) + "\n" +
                "6th smallest " + bst2.kthSmallest(6) + "\n" +
                "7th smallest " + bst2.kthSmallest(7));
    }

    /**
     * The node that holds the key, left, and right child.
     */
    private class Node {

        private int key;

        private Node left;

        private Node right;

        private Node(int key) {
            this.key = key;
        }

        private Node getRight() {
            return right;
        }

        private Node getLeft() {
            return left;
        }

        private int getKey() {
            return key;
        }

        private void setKey(int key) {
            this.key = key;
        }

        private void setLeft(Node left) {
            this.left = left;
        }

        private void setRight(Node right) {
            this.right = right;
        }

        public String toString() {
            return "" + key;
        }
    }

}
