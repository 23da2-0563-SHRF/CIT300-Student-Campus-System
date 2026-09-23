package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import graph.CampusGraph;

/** Standalone graph checks; no testing libraries or collections are required. */
public class CampusGraphTest {
    public static void main(String[] args) {
        CampusGraph graph = new CampusGraph();
        String[] locations = {"Library", "Engineering Faculty", "Cafeteria",
                "Hostel", "Main Gate"};

        System.out.println("1. Add campus locations");
        for (String location : locations) {
            graph.addVertex(location);
        }
        StringBuilder isolated = new StringBuilder();
        for (String location : locations) {
            isolated.append(location).append(" -> No connected routes")
                    .append(System.lineSeparator());
        }
        check(capture(() -> graph.displayGraph()).equals(isolated.toString()),
                "All five locations are present before routes are added");

        System.out.println("\n2. Add routes");
        graph.addEdge("Library", "Engineering Faculty");
        graph.addEdge("Engineering Faculty", "Cafeteria");
        graph.addEdge("Cafeteria", "Hostel");
        graph.addEdge("Hostel", "Main Gate");

        System.out.println("\n3. Verify graph display");
        String expectedGraph = lines(
                "Library -> Engineering Faculty",
                "Engineering Faculty -> Library, Cafeteria",
                "Cafeteria -> Engineering Faculty, Hostel",
                "Hostel -> Cafeteria, Main Gate",
                "Main Gate -> Hostel");
        check(capture(() -> graph.displayGraph()).equals(expectedGraph),
                "Display contains all locations and both directions of every route");
        graph.displayGraph();

        System.out.println("\n4. Verify BFS from Library");
        String expectedTraversal = lines(locations);
        check(capture(() -> graph.BFS("Library")).equals(expectedTraversal),
                "BFS visits every reachable location exactly once in order");

        System.out.println("\n5. Verify DFS from Library");
        check(capture(() -> graph.DFS("Library")).equals(expectedTraversal),
                "DFS visits every reachable location exactly once in order");
        check(capture(() -> graph.BFS("Library")).equals(expectedTraversal),
                "Repeated traversals reset visited state");
        // Starting at the opposite end also verifies that routes are undirected.
        check(capture(() -> graph.BFS("Main Gate")).equals(lines(
                "Main Gate", "Hostel", "Cafeteria", "Engineering Faculty", "Library")),
                "Routes can be traversed in the reverse direction");

        System.out.println("\n6. Reject duplicate vertices");
        check(capture(() -> graph.addVertex("Library")).contains("already exists"),
                "Duplicate vertex reports rejection");
        check(capture(() -> graph.displayGraph()).equals(expectedGraph),
                "Duplicate vertex does not change the graph");

        System.out.println("\n7. Reject duplicate edges");
        check(capture(() -> graph.addEdge("Library", "Engineering Faculty"))
                .contains("already exists"), "Duplicate edge reports rejection");
        check(capture(() -> graph.addEdge("Engineering Faculty", "Library"))
                .contains("already exists"), "Reversed duplicate edge is also rejected");
        check(capture(() -> graph.displayGraph()).equals(expectedGraph),
                "Duplicate edges do not add adjacency entries");

        System.out.println("\n8. Handle invalid locations");
        check(capture(() -> graph.addVertex(null)).contains("non-empty"),
                "Null vertex is rejected");
        check(capture(() -> graph.addVertex("   ")).contains("non-empty"),
                "Blank vertex is rejected");
        String[] invalid = {"Unknown", null, "   "};
        for (String location : invalid) {
            check(capture(() -> graph.addEdge(location, "Library")).contains("must exist"),
                    "Invalid route source is rejected");
            check(capture(() -> graph.addEdge("Library", location)).contains("must exist"),
                    "Invalid route destination is rejected");
            check(capture(() -> graph.BFS(location)).contains("not found"),
                    "BFS handles an invalid starting location");
            check(capture(() -> graph.DFS(location)).contains("not found"),
                    "DFS handles an invalid starting location");
        }
        check(capture(() -> graph.displayGraph()).equals(expectedGraph),
                "Invalid operations leave the graph unchanged");
        check(capture(() -> graph.BFS("Library")).equals(expectedTraversal)
                && capture(() -> graph.DFS("Library")).equals(expectedTraversal),
                "Valid traversals still work after invalid operations");

        System.out.println("\n9. Handle an empty graph");
        CampusGraph empty = new CampusGraph();
        check(capture(() -> empty.displayGraph()).contains("graph is empty"),
                "Empty graph display is informative");
        check(capture(() -> empty.BFS("Library")).contains("graph is empty"),
                "BFS handles an empty graph");
        check(capture(() -> empty.DFS("Library")).contains("graph is empty"),
                "DFS handles an empty graph");
        check(capture(() -> empty.addEdge("Library", "Hostel")).contains("must exist"),
                "An edge cannot be added without vertices");
        check(capture(() -> empty.displayGraph()).contains("graph is empty"),
                "Invalid edge insertion leaves the graph empty");
        empty.addVertex("Library");
        check(capture(() -> empty.BFS("Library")).equals(lines("Library"))
                && capture(() -> empty.DFS("Library")).equals(lines("Library")),
                "Traversals handle a single isolated vertex");

        System.out.println("\nAll automated campus graph checks passed.");
    }

    private static String lines(String... values) {
        StringBuilder result = new StringBuilder();
        for (String value : values) {
            result.append(value).append(System.lineSeparator());
        }
        return result.toString();
    }

    /** Capture console output while always restoring the original stream. */
    private static String capture(Runnable action) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        PrintStream captured = new PrintStream(bytes);
        try {
            System.setOut(captured);
            action.run();
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
