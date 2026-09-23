package graph;

/**
 * An undirected campus graph built entirely from custom linked nodes.
 * Location names are case-sensitive. Vertices and neighbours retain insertion
 * order so that display and traversal results are predictable.
 */
public class CampusGraph {
    private Vertex head;
    private Vertex tail;

    /** A campus location and the head of its adjacency list. */
    private static class Vertex {
        private String location;
        private Vertex next;
        private Edge neighbours;
        private Edge lastNeighbour;
        private boolean visited;

        private Vertex(String location) {
            this.location = location;
        }
    }

    /** One connection in a vertex's manually linked adjacency list. */
    private static class Edge {
        private Vertex destination;
        private Edge next;

        private Edge(Vertex destination) {
            this.destination = destination;
        }
    }

    /** Queue link used by BFS; no Java collection is needed. */
    private static class QueueNode {
        private Vertex vertex;
        private QueueNode next;

        private QueueNode(Vertex vertex) {
            this.vertex = vertex;
        }
    }

    /** Adds a unique, non-blank campus location. */
    public void addVertex(String location) {
        if (location == null || location.trim().isEmpty()) {
            System.out.println("Cannot add location: a non-empty name is required.");
            return;
        }
        if (findVertex(location) != null) {
            System.out.println("Cannot add location: " + location + " already exists.");
            return;
        }
        Vertex vertex = new Vertex(location);
        if (head == null) {
            head = vertex;
        } else {
            tail.next = vertex;
        }
        tail = vertex;
    }

    /** Adds a route in both directions; both locations must already exist. */
    public void addEdge(String source, String destination) {
        Vertex from = findVertex(source);
        Vertex to = findVertex(destination);
        if (from == null || to == null) {
            System.out.println("Cannot add route: both locations must exist.");
            return;
        }
        for (Edge edge = from.neighbours; edge != null; edge = edge.next) {
            if (edge.destination == to) {
                System.out.println("Cannot add route: the route already exists.");
                return;
            }
        }
        appendEdge(from, to);
        // A self-loop needs only one adjacency entry.
        if (from != to) {
            appendEdge(to, from);
        }
    }

    private void appendEdge(Vertex from, Vertex to) {
        Edge edge = new Edge(to);
        if (from.neighbours == null) {
            from.neighbours = edge;
        } else {
            from.lastNeighbour.next = edge;
        }
        from.lastNeighbour = edge;
    }

    /** Displays reachable locations in breadth-first order, one per line. */
    public void BFS(String startLocation) {
        Vertex start = prepareTraversal(startLocation);
        if (start == null) {
            return;
        }
        QueueNode front = new QueueNode(start);
        QueueNode rear = front;
        start.visited = true;
        while (front != null) {
            Vertex current = front.vertex;
            front = front.next;
            if (front == null) {
                rear = null;
            }
            System.out.println(current.location);
            for (Edge edge = current.neighbours; edge != null; edge = edge.next) {
                Vertex neighbour = edge.destination;
                if (!neighbour.visited) {
                    // Mark on enqueue to avoid adding a vertex twice in a cycle.
                    neighbour.visited = true;
                    QueueNode queued = new QueueNode(neighbour);
                    if (rear == null) {
                        front = queued;
                    } else {
                        rear.next = queued;
                    }
                    rear = queued;
                }
            }
        }
    }

    /** Displays reachable locations in depth-first order, one per line. */
    public void DFS(String startLocation) {
        Vertex start = prepareTraversal(startLocation);
        if (start != null) {
            visitDepthFirst(start);
        }
    }

    private void visitDepthFirst(Vertex vertex) {
        vertex.visited = true;
        System.out.println(vertex.location);
        for (Edge edge = vertex.neighbours; edge != null; edge = edge.next) {
            if (!edge.destination.visited) {
                visitDepthFirst(edge.destination);
            }
        }
    }

    /** Validates the start and resets state so repeated traversals are independent. */
    private Vertex prepareTraversal(String startLocation) {
        if (head == null) {
            System.out.println("Cannot traverse: the graph is empty.");
            return null;
        }
        Vertex start = findVertex(startLocation);
        if (start == null) {
            System.out.println("Cannot traverse: the starting location was not found.");
            return null;
        }
        for (Vertex vertex = head; vertex != null; vertex = vertex.next) {
            vertex.visited = false;
        }
        return start;
    }

    /** Displays every location, including isolated vertices, and its neighbours. */
    public void displayGraph() {
        if (head == null) {
            System.out.println("No campus locations to display: the graph is empty.");
            return;
        }
        for (Vertex vertex = head; vertex != null; vertex = vertex.next) {
            System.out.print(vertex.location + " -> ");
            if (vertex.neighbours == null) {
                System.out.print("No connected routes");
            }
            for (Edge edge = vertex.neighbours; edge != null; edge = edge.next) {
                System.out.print(edge.destination.location);
                if (edge.next != null) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    private Vertex findVertex(String location) {
        if (location == null || location.trim().isEmpty()) {
            return null;
        }
        for (Vertex vertex = head; vertex != null; vertex = vertex.next) {
            if (vertex.location.equals(location)) {
                return vertex;
            }
        }
        return null;
    }
}
