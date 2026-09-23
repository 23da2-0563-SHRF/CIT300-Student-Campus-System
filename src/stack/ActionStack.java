package stack;

/**
 * A manually implemented stack for recent system actions.
 * Actions follow last-in, first-out (LIFO) order.
 */
public class ActionStack {
    private Node top;

    /** Stores one action and a reference to the node below it. */
    private static class Node {
        private final String action;
        private final Node next;

        private Node(String action, Node next) {
            this.action = action;
            this.next = next;
        }
    }

    /** Adds an action to the top in constant time. */
    public void push(String action) {
        // Null is reserved for the empty result from pop() and peek().
        if (action == null) {
            throw new IllegalArgumentException("Action must not be null.");
        }
        top = new Node(action, top);
    }

    /** Removes and returns the latest action, or null if the stack is empty. */
    public String pop() {
        if (isEmpty()) {
            return null;
        }

        String action = top.action;
        top = top.next;
        return action;
    }

    /** Returns the latest action without removing it, or null if empty. */
    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return top.action;
    }

    /** Returns true when there are no stored actions. */
    public boolean isEmpty() {
        return top == null;
    }

    /** Displays actions from newest to oldest without changing the stack. */
    public void displayActions() {
        if (isEmpty()) {
            System.out.println("No recent actions: the stack is empty.");
            return;
        }

        Node current = top;
        while (current != null) {
            System.out.println(current.action);
            current = current.next;
        }
    }
}
