# CIT300 Student Campus System

## Project Overview

The CIT300 Student Campus System is a Java-based student campus management system developed to demonstrate fundamental data structures as part of a university assignment. It applies custom data structure implementations to student records, action history, service requests, student searches, and campus routes.

The project demonstrates how different structures organize and retrieve information, with standalone Java test classes used to validate their behavior.

## Data Structures Implemented

| Data Structure | Application | Implemented Features |
| --- | --- | --- |
| Linked List | Student Record Management | Add, search, update, delete, and display student records; reject duplicate student IDs. |
| Stack | Action History Management | Store actions using Last In, First Out (LIFO) order, with push, pop, peek, and empty-state checks. |
| Queue | Service Request Management | Process requests using First In, First Out (FIFO) order, with enqueue, dequeue, peek, and empty-state checks. |
| Binary Search Tree (BST) | Student Search | Insert, search, and delete students by ID; display records through inorder traversal. |
| Hash Table | Student Lookup | Insert, search, and delete records by student ID, using separate chaining to handle collisions. |
| Graph | Campus Route Management | Represent campus locations and connecting routes; explore reachable locations using Breadth-First Search (BFS) and Depth-First Search (DFS). |

## Technologies Used

- **Java** — implementation of the data structures and custom test classes.
- **Eclipse IDE** — Java project development and execution.
- **Git** — version control and tracking contributions.
- **GitHub** — repository hosting and team collaboration through commits, branches, and pull requests.

## Team Members and Contributions

| Team Member | Student ID | GitHub Profile | Contribution |
|-------------|------------|----------------|--------------|
| A.H.M.Sharfan | 23da2-0563 | [23da2-0563-SHRF](https://github.com/23da2-0563-SHRF) | Implemented Linked List-based student record management, including adding, searching, updating, deleting, and displaying records. |
| MRM. Ansir Siman | 23da2-0742 | [ansir-2004](https://github.com/ansir-2004) | Implemented Stack for action history management and Queue for service request management using LIFO and FIFO operations. |
| Salhan Burhan | 23da2-0926 | [Salhanburhan88](https://github.com/Salhanburhan88) | Implemented Binary Search Tree (BST) for student searching and ordered traversal, and Hash Table for student lookup with collision handling. |
| K. Ahamed Sanaj | 23da2-0541 | [KamarudeenAhamedSanaj](https://github.com/KamarudeenAhamedSanaj) | Implemented Graph-based campus route management using BFS and DFS traversal algorithms. |

## Project Structure

```text
CIT300-Student-Campus-System/
├── src/
│   ├── model/          # Shared Student model
│   ├── linkedlist/     # Student record management
│   ├── stack/          # Action history management
│   ├── queue/          # Service request management
│   ├── tree/           # Binary search tree
│   ├── hashing/        # Hash table with separate chaining
│   ├── graph/          # Campus routes, BFS, and DFS
│   └── test/           # Standalone Java test classes
├── .classpath          # Eclipse classpath configuration
├── .project            # Eclipse project configuration
└── README.md
```

## Getting Started

### Requirements

- A Java Development Kit (JDK) with Java 8 or later language support.
- Eclipse IDE with Java development support.
- Git to clone the repository, or a downloaded ZIP of the project.

### Open and Run in Eclipse

1. Clone this GitHub repository or download and extract its ZIP archive.
2. Open Eclipse and select **File → Import → General → Existing Projects into Workspace**.
3. Select the project root directory and finish the import.
4. Ensure a JDK is configured for the project's Java Build Path.
5. Expand `src/test`, select a test class, and choose **Run As → Java Application**.
6. Review the Eclipse Console for check results, and repeat for the remaining test classes.

The standalone test classes serve as executable demonstrations of the implemented modules.

## Testing

Custom Java test classes were used to test the data structures without an external testing framework. Each test class provides a `main` method and explicit checks that throw an `AssertionError` when an expected condition is not met.

Testing covered the following operations across the relevant structures:

- **Insertion:** adding student records, pushing actions, enqueuing requests, and adding campus locations and routes.
- **Searching:** retrieving students by ID and handling records that do not exist.
- **Deletion:** removing student records, popping actions, and dequeuing requests; BST deletion includes leaf, one-child, and two-child cases.
- **Duplicate handling:** rejecting duplicate student IDs, campus locations, and routes.
- **Empty structure handling:** checking searches, removals, peeks, displays, and traversals where applicable.
- **Traversal operations:** linked list display, BST inorder traversal, and graph BFS and DFS.

Additional checks cover student updates, stack LIFO order, queue FIFO order and reuse, and hash table collisions with deletion from collision chains.

| Test Class | Main Focus |
| --- | --- |
| `StudentLinkedListTest` | Student record operations, updates, duplicates, and empty-list behavior. |
| `ActionStackTest` | Push, pop, peek, LIFO order, and empty-stack behavior. |
| `ServiceQueueTest` | Enqueue, dequeue, peek, FIFO order, and queue reuse. |
| `StudentBSTTest` | Insertion, searching, deletion, duplicate handling, and inorder traversal. |
| `StudentHashTableTest` | Student lookup, separate chaining collisions, deletion, and duplicate handling. |
| `CampusGraphTest` | Locations and routes, BFS, DFS, duplicates, invalid locations, and empty graphs. |

## GitHub Collaboration

The team used Git and GitHub to coordinate development through commits, branches, and a pull request workflow. Commits recorded each member's implementation work, branches organized changes, and pull requests supported review and integration into the shared project.

The `documentation` branch contains the README preparation work for the university assignment submission.
