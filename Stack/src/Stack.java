/**
 * Stack Data Structure
 * @param <T> Type contained in the stack.
 * @author Jonathan F DaSilva
 */
public class Stack<T extends Comparable<? super T>> {

    // Top of the Stack.
    private Node top;

    /**
     * Initializes an empty Stack.
     */
    public Stack() {
        top = null;
    }

    /**
     * Adds a T object to the Stack
     * @param value The object to be added
     * @return Return true if the item was successfully added.
     */
    public boolean push(T value) {
        // create Node with T inside
        // set Node to reference to top
        // set new Node to be top
        try {
            Node newTop = new Node(value, top);
            setTop(newTop);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Removes and return a T object from the top of the Stack
     * @return The object removed
     */
    public T pop() {
        if (isEmpty())
            return null;
        else {
            T save = top.getElement();
            setTop(top.getNext());
            return save;
        }
    }

    /**
     * Get the object from the top of the Stack
     * @return The object present on the top of the Stack
     */
    public T peek() {
        return top.getElement();
    }

    /**
     * Return true if the Stack is empty
     * @return a boolean
     */
    public boolean isEmpty() {
        return top == null;
    }

    private void setTop(Node top) {
        this.top = top;
    }

    public String printStack() {
        StringBuilder b = new StringBuilder();
        Node n = top;
        while (n != null) {
            b.append(n.getElement());
            n = n.getNext();
        }
        return b.toString();
    }

    // Node that will store elements in the Stack.
    private class Node {

        // Element of the node
        private T element;

        // Pointer to the next node
        private Node next;

        /**
         * Creates a node
         * @param element Element of the node
         * @param next The next node
         */
        private Node(T element, Node next) {
            this.element = element;
            this.next = next;
        }

        /**
         * Creates a node without a next node.
         * @param element The element of the node
         */
        private Node(T element) {
            this.element = element;
            next = null;
        }

        /**
         * Get the next node
         * @return The next node
         */
        private Node getNext() {
            return next;
        }

        /**
         * Get the object of the node
         * @return The object inside the node
         */
        private T getElement() {
            return element;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.printStack();
    }
}
