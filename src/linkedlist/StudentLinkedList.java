package linkedlist;

import model.Student;

/**
 * A custom singly linked list that stores student records with unique IDs.
 * Student IDs should not be changed directly while records are in this list.
 */
public class StudentLinkedList {
    private Node head;

    /** Each node stores one student and a link to the next node. */
    private static class Node {
        private Student data;
        private Node next;

        private Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    /** Adds a student at the end, provided the student ID is unique. */
    public void addStudent(Student student) {
        if (student == null || student.getStudentID() == null
                || student.getStudentID().trim().isEmpty()) {
            System.out.println("Cannot add student: a student and a non-empty ID are required.");
            return;
        }

        if (searchStudent(student.getStudentID()) != null) {
            System.out.println("Cannot add student: ID " + student.getStudentID()
                    + " already exists.");
            return;
        }

        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    /** Updates a matching student's details while preserving the student ID. */
    public void updateStudent(String studentID, String name, String programme, double marks) {
        Student student = searchStudent(studentID);
        if (student == null) {
            System.out.println("Cannot update student: ID " + studentID + " was not found.");
            return;
        }

        student.setName(name);
        student.setProgramme(programme);
        student.setMarks(marks);
    }

    /** Removes a matching node by reconnecting the surrounding links. */
    public void deleteStudent(String studentID) {
        if (head == null) {
            System.out.println("Cannot delete student: the list is empty.");
            return;
        }

        Node current = head;
        Node previous = null;

        while (current != null) {
            if (studentID != null && studentID.equals(current.data.getStudentID())) {
                if (previous == null) {
                    // Removing the first node requires moving the head.
                    head = current.next;
                } else {
                    // This also handles the last node, whose next link is null.
                    previous.next = current.next;
                }
                return;
            }
            previous = current;
            current = current.next;
        }

        System.out.println("Cannot delete student: ID " + studentID + " was not found.");
    }

    /** Returns the matching student, or null when there is no match. */
    public Student searchStudent(String studentID) {
        if (studentID == null) {
            return null;
        }

        Node current = head;
        while (current != null) {
            if (studentID.equals(current.data.getStudentID())) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /** Displays students in insertion order using Student.toString(). */
    public void displayStudents() {
        if (head == null) {
            System.out.println("No student records to display: the list is empty.");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
