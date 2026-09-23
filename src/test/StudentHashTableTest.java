package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import model.Student;
import hashing.StudentHashTable;

/** Standalone hash table tests without external libraries or collections. */
public class StudentHashTableTest {

    public static void main(String[] args) {
        StudentHashTable students = new StudentHashTable();
        Student first = student("S001");
        Student second = student("S002");
        Student third = student("S003");
        Student fourth = student("S004");

        System.out.println("1. Insert multiple students");
        students.insert(first);
        students.insert(second);
        students.insert(third);
        students.insert(fourth);
        check(students.search("S001") == first && students.search("S002") == second
                && students.search("S003") == third && students.search("S004") == fourth,
                "All four inserted records are accessible");
        students.displayTable();

        System.out.println("\n2. Search by student ID");
        check(students.search("S002") == second,
                "Existing ID returns the original Student object");
        check(students.search("S999") == null, "Missing ID returns null");

        System.out.println("\n3. Handle collisions with separate chaining");
        // A single bucket guarantees collisions without depending on hash details.
        StudentHashTable collisions = new StudentHashTable(1);
        collisions.insert(first);
        collisions.insert(second);
        collisions.insert(third);
        collisions.insert(fourth);
        check(collisions.search("S001") == first && collisions.search("S002") == second
                && collisions.search("S003") == third && collisions.search("S004") == fourth,
                "All colliding records remain accessible in the same bucket");
        collisions.displayTable();

        System.out.println("\n4. Delete existing records");
        students.delete("S002");
        check(students.search("S002") == null, "Deleted student cannot be found");
        check(students.search("S001") == first && students.search("S003") == third
                && students.search("S004") == fourth,
                "Deleting a record preserves other students");

        // Insertion prepends nodes, giving the chain S004 -> S003 -> S002 -> S001.
        collisions.delete("S003");
        check(collisions.search("S003") == null && collisions.search("S004") == fourth
                && collisions.search("S002") == second && collisions.search("S001") == first,
                "Deleting a middle node preserves the rest of the collision chain");
        collisions.delete("S004");
        check(collisions.search("S004") == null && collisions.search("S002") == second
                && collisions.search("S001") == first,
                "Deleting the bucket head preserves its remaining chain");
        collisions.delete("S001");
        check(collisions.search("S001") == null && collisions.search("S002") == second,
                "Deleting the tail preserves the remaining record");
        collisions.delete("S999");
        check(collisions.search("S002") == second,
                "Deleting a missing ID leaves existing records unchanged");
        collisions.delete("S002");
        check(collisions.search("S002") == null, "The final node can be deleted");

        System.out.println("\n5. Reject duplicate IDs");
        // Use the one-bucket table to test duplicate detection inside a chain.
        collisions.insert(first);
        collisions.insert(second);
        collisions.insert(third);
        collisions.insert(new Student("S002", "Duplicate", "Business", 50.0));
        check(collisions.search("S002") == second,
                "Duplicate insertion keeps the original Student record");
        collisions.delete("S002");
        check(collisions.search("S002") == null,
                "Deleting the original leaves no duplicate node");
        check(collisions.search("S001") == first && collisions.search("S003") == third,
                "Duplicate handling preserves other colliding records");

        System.out.println("\n6. Handle empty hash tables");
        checkEmptyTable(new StudentHashTable());
        collisions.delete("S001");
        collisions.delete("S003");
        check(collisions.search("S001") == null && collisions.search("S003") == null,
                "Remaining collision records are removed");
        checkEmptyTable(collisions);
        collisions.insert(fourth);
        check(collisions.search("S004") == fourth,
                "Insertion works after emptying the table");

        System.out.println("\nAll automated hash table checks passed.");
    }

    private static Student student(String id) {
        return new Student(id, "Student " + id, "Computing", 80.0);
    }

    private static void checkEmptyTable(StudentHashTable students) {
        check(students.search("S999") == null, "Searching an empty table returns null");
        students.delete("S999");
        check(students.search("S999") == null, "Deleting from an empty table is harmless");
        String output = captureDisplay(students);
        check(output.contains("Bucket 0: empty") && output.contains("table is empty")
                && !output.contains("Student ID:"),
                "Empty display shows bucket positions and an informative message");
    }

    /** Capture display output and restore standard output even if display fails. */
    private static String captureDisplay(StudentHashTable students) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        PrintStream captured = new PrintStream(bytes);
        try {
            System.setOut(captured);
            students.displayTable();
            captured.flush();
            return bytes.toString();
        } finally {
            System.setOut(original);
            captured.close();
        }
    }

    /** Always runs, without requiring Java's -ea assertion option. */
    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAIL: " + description);
        }
        System.out.println("PASS: " + description);
    }
}
