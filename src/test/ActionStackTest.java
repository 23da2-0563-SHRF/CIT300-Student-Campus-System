package test;

import stack.ActionStack;

/** Standalone stack tests that do not require an external testing library. */
public class ActionStackTest {

    public static void main(String[] args) {
        ActionStack actions = new ActionStack();

        System.out.println("1. Check a new stack");
        check(actions.isEmpty(), "A new stack is empty");
        check(actions.peek() == null, "Peek on a new stack returns null");
        check(actions.pop() == null, "Pop on a new stack returns null");

        System.out.println("\n2. Push multiple actions");
        actions.push("Added student record");
        actions.push("Updated student record");
        actions.push("Deleted student record");
        check(!actions.isEmpty(), "The stack is not empty after pushing actions");

        System.out.println("\n3. Peek without removing the latest action");
        check("Deleted student record".equals(actions.peek()),
                "Peek returns the latest action");
        check("Deleted student record".equals(actions.peek()),
                "Repeated peek returns the same action");

        System.out.println("\n4. Pop actions in LIFO order");
        // The first pop also verifies that peek did not remove the top action.
        check("Deleted student record".equals(actions.pop()),
                "First pop returns Deleted student record");
        check(!actions.isEmpty(), "The stack still contains earlier actions");
        check("Updated student record".equals(actions.pop()),
                "Second pop returns Updated student record");
        check("Added student record".equals(actions.pop()),
                "Third pop returns Added student record");

        System.out.println("\n5. Check empty stack handling after all pops");
        check(actions.isEmpty(), "The stack is empty after removing all actions");
        check(actions.pop() == null, "Pop on an emptied stack returns null");
        check(actions.peek() == null, "Peek on an emptied stack returns null");
        check(actions.isEmpty(), "Empty operations leave the stack empty");

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
