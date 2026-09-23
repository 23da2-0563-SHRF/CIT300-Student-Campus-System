package queue;

/**
 * A manually implemented queue for student service requests.
 * Requests follow first-in, first-out (FIFO) order.
 */
public class ServiceQueue {
    private Node front;
    private Node rear;

    /** Stores one request and a reference to the next node. */
    private static class Node {
        private final String request;
        private Node next;

        private Node(String request) {
            this.request = request;
            this.next = null;
        }
    }

    /** Adds a request to the rear in constant time. */
    public void enqueue(String request) {
        // Null is reserved for the empty result from dequeue() and peek().
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null.");
        }

        Node newNode = new Node(request);
        if (isEmpty()) {
            // The first node is both the front and rear of the queue.
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
    }

    /** Removes and returns the oldest request, or null if the queue is empty. */
    public String dequeue() {
        if (isEmpty()) {
            return null;
        }

        String request = front.request;
        front = front.next;

        // Removing the last node must clear both queue references.
        if (front == null) {
            rear = null;
        }
        return request;
    }

    /** Returns the next request without removing it, or null if empty. */
    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return front.request;
    }

    /** Returns true when there are no pending requests. */
    public boolean isEmpty() {
        return front == null;
    }

    /** Displays requests from oldest to newest without changing the queue. */
    public void displayRequests() {
        if (isEmpty()) {
            System.out.println("No pending service requests: the queue is empty.");
            return;
        }

        Node current = front;
        while (current != null) {
            System.out.println(current.request);
            current = current.next;
        }
    }
}
