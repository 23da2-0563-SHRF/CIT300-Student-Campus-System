package test;

import queue.ServiceQueue;

/** Standalone queue tests that do not require an external testing library. */
public class ServiceQueueTest {

    public static void main(String[] args) {
        ServiceQueue requests = new ServiceQueue();

        System.out.println("1. Check a new queue");
        check(requests.isEmpty(), "A new queue is empty");
        check(requests.peek() == null, "Peek on a new queue returns null");
        check(requests.dequeue() == null, "Dequeue on a new queue returns null");

        System.out.println("\n2. Enqueue multiple service requests");
        requests.enqueue("ID Card Request");
        requests.enqueue("Transcript Request");
        requests.enqueue("Payment Issue");
        check(!requests.isEmpty(), "The queue is not empty after adding requests");

        System.out.println("\n3. Peek without removing the first request");
        check("ID Card Request".equals(requests.peek()),
                "Peek returns the first request");
        check("ID Card Request".equals(requests.peek()),
                "Repeated peek returns the same request");

        System.out.println("\n4. Dequeue requests in FIFO order");
        // The first dequeue also verifies that peek did not remove the request.
        check("ID Card Request".equals(requests.dequeue()),
                "First dequeue returns ID Card Request");
        check(!requests.isEmpty(), "The queue still contains pending requests");
        check("Transcript Request".equals(requests.dequeue()),
                "Second dequeue returns Transcript Request");
        check("Payment Issue".equals(requests.dequeue()),
                "Third dequeue returns Payment Issue");

        System.out.println("\n5. Check empty queue handling after all removals");
        check(requests.isEmpty(), "The queue is empty after removing all requests");
        check(requests.dequeue() == null, "Dequeue on an emptied queue returns null");
        check(requests.peek() == null, "Peek on an emptied queue returns null");
        check(requests.isEmpty(), "Empty operations leave the queue empty");

        System.out.println("\n6. Reuse the emptied queue");
        // Checks that front and rear work correctly after removing the last node.
        requests.enqueue("ID Card Request");
        requests.enqueue("Transcript Request");
        check(!requests.isEmpty(), "New requests can be added after emptying the queue");
        check("ID Card Request".equals(requests.peek()),
                "The reused queue has the correct front request");
        check("ID Card Request".equals(requests.dequeue()),
                "The reused queue removes its first request correctly");
        check("Transcript Request".equals(requests.dequeue()),
                "The reused queue preserves FIFO order");
        check(requests.isEmpty(), "The reused queue becomes empty again");

        System.out.println("\nAll automated checks passed.");
    }

    /** Always checks the condition, without requiring Java's -ea option. */
    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAIL: " + description);
        }
        System.out.println("PASS: " + description);
    }
}
