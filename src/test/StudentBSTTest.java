package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import model.Student;
import tree.StudentBST;

/** Standalone BST tests; no external testing library or collections are required. */
public class StudentBSTTest {

    public static void main(String[] args) {
        StudentBST students = new StudentBST();
        Student third = student("S003");
        Student first = student("S001");
        Student fifth = student("S005");
        Student second = student("S002");

        System.out.println("1. Insert multiple students");
        students.insert(third);
        students.insert(first);
        students.insert(fifth);
        students.insert(second);
        check(students.search("S003") == third
                && students.search("S001") == first
                && students.search("S005") == fifth
                && students.search("S002") == second,
                "All four inserted records are accessible");

        System.out.println("\n2. Search by student ID");
        check(students.search("S002") == second,
                "Existing ID returns the original Student object");
        check(students.search("S999") == null, "Missing ID returns null");

        System.out.println("\n3. Verify inorder traversal");
        checkTraversal(students, first, second, third, fifth);
        students.inorderTraversal();

        System.out.println("\n4. Delete leaf, one-child, and two-child nodes");
        // S005 is a leaf in the original tree.
        students.delete("S005");
        check(students.search("S005") == null, "Leaf node is removed");
        checkTraversal(students, first, second, third);

        // S001 has exactly one child, S002.
        students.delete("S001");
        check(students.search("S001") == null && students.search("S002") == second,
                "Deleting a one-child node preserves its child");
        checkTraversal(students, second, third);

        // Restore a right subtree so root S003 has two children.
        students.insert(fifth);
        Student fourth = student("S004");
        students.insert(fourth);
        students.delete("S003");
        check(students.search("S003") == null, "Two-child root is removed");
        check(students.search("S002") == second && students.search("S004") == fourth
                && students.search("S005") == fifth,
                "Two-child deletion preserves all other Student records");
        checkTraversal(students, second, fourth, fifth);

        System.out.println("\n5. Reject duplicate IDs");
        students.insert(new Student("S002", "Duplicate", "Business", 50.0));
        check(students.search("S002") == second,
                "Duplicate insertion keeps the original record");
        checkTraversal(students, second, fourth, fifth);
        students.delete("S002");
        check(students.search("S002") == null,
                "Deleting the original leaves no duplicate node");
        checkTraversal(students, fourth, fifth);

        System.out.println("\n6. Handle empty trees and missing IDs");
        students.delete("S999");
        checkTraversal(students, fourth, fifth);
        students.delete("S004");
        students.delete("S005");
        check(students.search("S004") == null && students.search("S005") == null,
                "Deleting the remaining records empties the tree");
        checkEmptyTree(students);
        checkEmptyTree(new StudentBST());
        students.insert(first);
        check(students.search("S001") == first, "Insertion works after emptying the tree");
        checkTraversal(students, first);

        System.out.println("\nAll automated BST checks passed.");
    }

    private static Student student(String id) {
        return new Student(id, "Student " + id, "Computing", 80.0);
    }

    /** Compare complete output to catch missing, duplicated, or misordered records. */
    private static void checkTraversal(StudentBST students, Student... expected) {
        StringBuilder output = new StringBuilder();
        for (Student student : expected) {
            output.append(student).append(System.lineSeparator());
        }
        check(captureTraversal(students).equals(output.toString()),
                "Inorder traversal displays exactly the expected records in ascending ID order");
    }

    /** Always restore standard output, including when traversal throws an exception. */
    private static String captureTraversal(StudentBST students) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        PrintStream captured = new PrintStream(bytes);
        try {
            System.setOut(captured);
            students.inorderTraversal();
            captured.flush();
            return bytes.toString();
        } finally {
            System.setOut(original);
            captured.close();
        }
    }

    private static void checkEmptyTree(StudentBST students) {
        check(students.search("S999") == null, "Searching an empty tree returns null");
        students.delete("S999");
        check(students.search("S999") == null, "Deleting from an empty tree is harmless");
        String output = captureTraversal(students);
        check(output.contains("tree is empty") && !output.contains("Student ID:"),
                "Empty traversal displays an informative message without records");
    }

    /** Always runs, without requiring Java's -ea assertion option. */
    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAIL: " + description);
        }
        System.out.println("PASS: " + description);
    }
}
