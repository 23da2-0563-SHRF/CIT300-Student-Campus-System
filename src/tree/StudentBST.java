package tree;

import model.Student;

/**
 * A manually implemented BST of students with unique, case-sensitive IDs.
 * IDs are ordered lexicographically using String.compareTo(). Do not change a
 * student's ID while it is stored here; delete and reinsert the record instead.
 */
public class StudentBST {
    private Node root;

    /** Each node owns a student reference and links to its two subtrees. */
    private static class Node {
        private Student data;
        private Node left;
        private Node right;

        private Node(Student data) {
            this.data = data;
        }
    }

    /** Inserts a student without replacing an existing record with the same ID. */
    public void insert(Student student) {
        if (student == null || student.getStudentID() == null
                || student.getStudentID().trim().isEmpty()) {
            System.out.println("Cannot insert student: a student and a non-empty ID are required.");
            return;
        }
        if (root == null) {
            root = new Node(student);
            return;
        }

        Node current = root;
        while (true) {
            int comparison = student.getStudentID().compareTo(current.data.getStudentID());
            if (comparison == 0) {
                System.out.println("Cannot insert student: ID " + student.getStudentID()
                        + " already exists.");
                return;
            }
            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node(student);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(student);
                    return;
                }
                current = current.right;
            }
        }
    }

    /** Returns the matching student, or null for an absent or null ID. */
    public Student search(String studentId) {
        if (studentId == null) {
            return null;
        }
        Node current = root;
        while (current != null) {
            int comparison = studentId.compareTo(current.data.getStudentID());
            if (comparison == 0) {
                return current.data;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return null;
    }

    /** Displays all students in ascending ID order using Student.toString(). */
    public void inorderTraversal() {
        if (root == null) {
            System.out.println("No student records to display: the tree is empty.");
            return;
        }
        inorderTraversal(root);
    }

    private void inorderTraversal(Node node) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left);
        System.out.println(node.data);
        inorderTraversal(node.right);
    }

    /** Removes a matching student; an absent or null ID leaves the tree unchanged. */
    public void delete(String studentId) {
        if (studentId != null) {
            // Reassigning root also handles removal of the root itself.
            root = delete(root, studentId);
        }
    }

    /** Returns the subtree root after removal, reconnecting links on the way back. */
    private Node delete(Node node, String studentId) {
        if (node == null) {
            return null;
        }
        int comparison = studentId.compareTo(node.data.getStudentID());
        if (comparison < 0) {
            node.left = delete(node.left, studentId);
        } else if (comparison > 0) {
            node.right = delete(node.right, studentId);
        } else {
            // These cases cover both a leaf and a node with only one child.
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            // Two children: replace with the smallest record in the right subtree.
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.data = successor.data;
            node.right = delete(node.right, successor.data.getStudentID());
        }
        return node;
    }
}
