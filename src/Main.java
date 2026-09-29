import java.util.Scanner;

import model.Student;
import linkedlist.StudentLinkedList;
import stack.ActionStack;
import queue.ServiceQueue;
import tree.StudentBST;
import hashing.StudentHashTable;
import graph.CampusGraph;

/** Console demonstration of the CIT300 data structures. */
public class Main {
    private final Scanner input = new Scanner(System.in);
    private final StudentLinkedList students = new StudentLinkedList();
    private final ActionStack actions = new ActionStack();
    private final ServiceQueue requests = new ServiceQueue();
    private final StudentBST studentTree = new StudentBST();
    private final StudentHashTable studentTable = new StudentHashTable();
    private final CampusGraph campus = new CampusGraph();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("University Student Record and Campus Route Management System");
        try {
            boolean running = true;
            while (running) {
                displayMenu();
                String choice = readText("Choose an option: ");
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": updateStudent(); break;
                    case "3": deleteStudent(); break;
                    case "4": students.displayStudents(); break;
                    case "5":
                        displayStudent(students.searchStudent(readText("Student ID: ")));
                        break;
                    case "6":
                        String request = readText("Service request: ");
                        requests.enqueue(request);
                        recordAction("Added service request: " + request);
                        break;
                    case "7": processRequest(); break;
                    case "8": actions.displayActions(); break;
                    case "9": insertIntoTree(); break;
                    case "10":
                        displayStudent(studentTree.search(readText("Student ID: ")));
                        break;
                    case "11": studentTree.inorderTraversal(); break;
                    case "12": deleteFromTree(); break;
                    case "13":
                        displayStudent(studentTable.search(readText("Student ID: ")));
                        break;
                    case "14":
                        String location = readText("Location name: ");
                        campus.addVertex(location);
                        // The graph returns void, so record the attempt, not success.
                        actions.push("Requested campus location addition: " + location);
                        break;
                    case "15":
                        System.out.println("Removal unavailable: CampusGraph has no public location removal method.");
                        break;
                    case "16":
                        String source = readText("Source location: ");
                        String destination = readText("Destination location: ");
                        // CampusGraph rejects missing locations and duplicate routes.
                        campus.addEdge(source, destination);
                        actions.push("Requested campus connection: " + source + " <-> " + destination);
                        break;
                    case "17":
                        System.out.println("Removal unavailable: CampusGraph has no public connection removal method.");
                        break;
                    case "18": campus.displayGraph(); break;
                    case "19": campus.BFS(readText("Starting location: ")); break;
                    case "20": campus.DFS(readText("Starting location: ")); break;
                    case "0": running = false; break;
                    default: System.out.println("Invalid menu choice. Enter a number from 0 to 20.");
                }
            }
        } catch (java.util.NoSuchElementException exception) {
            System.out.println("\nInput ended.");
        } finally {
            input.close();
        }
        System.out.println("Goodbye.");
    }

    private void displayMenu() {
        System.out.println("\n1. Add Student Record");
        System.out.println("2. Update Student Record");
        System.out.println("3. Delete Student Record");
        System.out.println("4. Display All Student Records");
        System.out.println("5. Search Student using Linked List");
        System.out.println("6. Add Service Request");
        System.out.println("7. Process Service Request");
        System.out.println("8. Display Action History");
        System.out.println("9. Insert Student into BST");
        System.out.println("10. Search Student using BST");
        System.out.println("11. Display BST Inorder Traversal");
        System.out.println("12. Delete Student from BST");
        System.out.println("13. Search Student using Hashing");
        System.out.println("14. Add Campus Location");
        System.out.println("15. Remove Campus Location");
        System.out.println("16. Add Campus Connection");
        System.out.println("17. Remove Campus Connection");
        System.out.println("18. Display Campus Graph");
        System.out.println("19. BFS Traversal");
        System.out.println("20. DFS Traversal");
        System.out.println("0. Exit");
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input must not be empty.");
        }
    }

    private double readMarks() {
        while (true) {
            try {
                double marks = Double.parseDouble(readText("Marks (0-100): "));
                // This range check also rejects NaN and infinities.
                if (marks >= 0 && marks <= 100) {
                    return marks;
                }
            } catch (NumberFormatException exception) {
                // Show the same helpful message for text and out-of-range input.
            }
            System.out.println("Invalid marks. Enter a number from 0 to 100.");
        }
    }

    private void addStudent() {
        String id = readText("Student ID: ");
        if (students.searchStudent(id) != null) {
            System.out.println("Duplicate student ID: " + id);
            return;
        }
        String name = readText("Name: ");
        String programme = readText("Programme: ");
        Student student = new Student(id, name, programme, readMarks());
        students.addStudent(student);
        studentTable.insert(student);
        // Option 9 explicitly adds an existing record to the demonstration BST.
        recordAction("Added student record: " + id);
    }

    private void updateStudent() {
        String id = readText("Student ID: ");
        if (students.searchStudent(id) == null) {
            System.out.println("Student not found: " + id);
            return;
        }
        String name = readText("New name: ");
        String programme = readText("New programme: ");
        // All indexes share the same Student object; its ID stays unchanged.
        students.updateStudent(id, name, programme, readMarks());
        recordAction("Updated student record: " + id);
    }

    private void deleteStudent() {
        String id = readText("Student ID: ");
        if (students.searchStudent(id) == null) {
            System.out.println("Student not found: " + id);
            return;
        }
        students.deleteStudent(id);
        studentTable.delete(id);
        studentTree.delete(id);
        recordAction("Deleted student record from all structures: " + id);
    }

    private void displayStudent(Student student) {
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(student);
        }
    }

    private void processRequest() {
        String request = requests.dequeue();
        if (request == null) {
            System.out.println("No pending service requests.");
        } else {
            recordAction("Processed service request: " + request);
        }
    }

    private void insertIntoTree() {
        String id = readText("Existing student ID to insert into BST: ");
        Student student = students.searchStudent(id);
        if (student == null) {
            System.out.println("Student not found. Add the record using option 1 first.");
        } else if (studentTree.search(id) != null) {
            System.out.println("Duplicate student ID in BST: " + id);
        } else {
            studentTree.insert(student);
            recordAction("Inserted student into BST: " + id);
        }
    }

    private void deleteFromTree() {
        String id = readText("Student ID to delete from BST: ");
        if (studentTree.search(id) == null) {
            System.out.println("Student not found in BST: " + id);
        } else {
            studentTree.delete(id);
            recordAction("Deleted student from BST only: " + id);
        }
    }

    private void recordAction(String action) {
        actions.push(action);
        System.out.println(action);
    }
}
