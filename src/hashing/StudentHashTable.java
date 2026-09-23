package hashing;

import model.Student;

/**
 * A custom hash table with separate chaining and unique, case-sensitive IDs.
 * Do not change a student's ID while it is stored here; delete and reinsert
 * the record instead so that it remains in the correct bucket.
 */
public class StudentHashTable {
    private static final int DEFAULT_CAPACITY = 10;
    private Node[] table;

    /** Each bucket is a singly linked chain of student records. */
    private static class Node {
        private Student data;
        private Node next;

        private Node(Student data) {
            this.data = data;
        }
    }

    public StudentHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /** Creates a fixed-size bucket array; collisions are stored in chains. */
    public StudentHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Hash table capacity must be positive.");
        }
        table = new Node[capacity];
    }

    /**
     * Computes a polynomial hash directly from ID characters. Reducing at each
     * step and using long arithmetic keeps the index non-negative without overflow.
     */
    private int hash(String studentId) {
        long index = 0;
        for (int i = 0; i < studentId.length(); i++) {
            index = (31 * index + studentId.charAt(i)) % table.length;
        }
        return (int) index;
    }

    /** Inserts a valid student at the bucket head, rejecting duplicate IDs. */
    public void insert(Student student) {
        if (student == null || student.getStudentID() == null
                || student.getStudentID().trim().isEmpty()) {
            System.out.println("Cannot insert student: a student and a non-empty ID are required.");
            return;
        }

        String studentId = student.getStudentID();
        int index = hash(studentId);
        Node current = table[index];
        while (current != null) {
            if (studentId.equals(current.data.getStudentID())) {
                System.out.println("Cannot insert student: ID " + studentId + " already exists.");
                return;
            }
            current = current.next;
        }

        // Preserve existing collisions by linking the new node to the old head.
        Node node = new Node(student);
        node.next = table[index];
        table[index] = node;
    }

    /** Returns the matching student, or null for an absent or null ID. */
    public Student search(String studentId) {
        if (studentId == null) {
            return null;
        }
        Node current = table[hash(studentId)];
        while (current != null) {
            if (studentId.equals(current.data.getStudentID())) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /** Removes a matching record; missing or null IDs leave the table unchanged. */
    public void delete(String studentId) {
        if (studentId == null) {
            return;
        }
        int index = hash(studentId);
        Node current = table[index];
        Node previous = null;
        while (current != null) {
            if (studentId.equals(current.data.getStudentID())) {
                if (previous == null) {
                    // Removing a bucket's first node also handles a singleton chain.
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    /** Displays every bucket position and its chain using Student.toString(). */
    public void displayTable() {
        boolean empty = true;
        for (int i = 0; i < table.length; i++) {
            System.out.print("Bucket " + i + ": ");
            Node current = table[i];
            if (current == null) {
                System.out.println("empty");
                continue;
            }
            empty = false;
            while (current != null) {
                System.out.print(current.data);
                current = current.next;
                if (current != null) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
        if (empty) {
            System.out.println("No student records to display: the table is empty.");
        }
    }
}
