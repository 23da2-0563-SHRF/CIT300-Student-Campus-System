package test;

import linkedlist.StudentLinkedList;
import model.Student;

/** Standalone tests; no external testing library or collections are required. */
public class StudentLinkedListTest {

    public static void main(String[] args) {
        StudentLinkedList students = new StudentLinkedList();

        Student first = new Student("S001", "Amal", "Computing", 78.5);
        Student second = new Student("S002", "Nimali", "Engineering", 85.0);
        Student third = new Student("S003", "Kamal", "Business", 72.0);

        System.out.println("1. Add multiple students");
        students.addStudent(first);
        students.addStudent(second);
        students.addStudent(third);
        check(students.searchStudent("S001") == first, "First student was added");
        check(students.searchStudent("S002") == second, "Second student was added");
        check(students.searchStudent("S003") == third, "Third student was added");

        System.out.println("\n2. Display students (expected order: S001, S002, S003)");
        students.displayStudents();

        System.out.println("\n3. Search by student ID");
        Student found = students.searchStudent("S002");
        check(found == second, "Search returns the matching Student object");
        System.out.println("Found: " + found);
        check(students.searchStudent("S999") == null, "Missing ID returns null");

        System.out.println("\n4. Update a student record");
        students.updateStudent("S002", "Nimali Perera", "Software Engineering", 91.5);
        Student updated = students.searchStudent("S002");
        check(updated != null
                && "S002".equals(updated.getStudentID())
                && "Nimali Perera".equals(updated.getName())
                && "Software Engineering".equals(updated.getProgramme())
                && Double.compare(updated.getMarks(), 91.5) == 0,
                "Update changes all requested details and preserves the ID");
        System.out.println("Updated: " + updated);

        System.out.println("\n5. Delete a student record");
        students.deleteStudent("S002");
        check(students.searchStudent("S002") == null, "Deleted student is absent");
        check(students.searchStudent("S001") == first
                && students.searchStudent("S003") == third,
                "Other students remain accessible after deletion");
        students.displayStudents();

        System.out.println("\n6. Reject a duplicate student ID");
        students.addStudent(new Student("S001", "Duplicate", "Science", 50.0));
        check(students.searchStudent("S001") == first,
                "Duplicate insertion does not replace the original record");

        // Removing the original must leave no second node with the same ID.
        students.deleteStudent("S001");
        check(students.searchStudent("S001") == null,
                "No duplicate node remains after deleting the original");
        check(students.searchStudent("S003") == third,
                "Deleting the head preserves the remaining student");

        System.out.println("\n7. Empty list and missing records");
        students.deleteStudent("S003");
        check(students.searchStudent("S003") == null, "Last student was deleted");
        students.displayStudents();
        students.deleteStudent("S999");
        students.updateStudent("S999", "Missing", "Computing", 60.0);
        check(students.searchStudent("S999") == null,
                "Missing-record operations do not create a student");

        System.out.println("\nAll automated checks passed.");
    }

    /** Always runs, without requiring Java's -ea assertion option. */
    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAIL: " + description);
        }
        System.out.println("PASS: " + description);
    }
}
