import org.junit.jupiter.api.Test;

import java.awt.image.Kernel;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    /**
     * Testing addNode()
     * Tests the method with the following possibilities
     * 1. Add a node that does not exist
     * 2. Add a node that exist
     *
     * Testing addNode(arrays)
     * Tests the method with the following possibilities
     * 1. Name array and data array are not equal length
     * 2. Add arrays with all unique keys, therefore, all nodes does not exist
     * 3. Add arrays with some repeated keys, meaning some nodes are repeated
     * @throws FileNotFoundException
     */
    @Test
    public void addNodeTest() throws FileNotFoundException {
        Graph<String, Integer> graph = new Graph<>();
        assertTrue(graph.addNode("A", 1));
        assertTrue(graph.addNode("B", 2));
        assertFalse(graph.addNode("A", 2));

        // Testing addNodes()
        Graph<String, Integer> graph1 = new Graph<>();
        String[] keys = {"A", "B", "C", "D", "F"};
        Integer[] values = {1, 2, 3, 1, 2};
        String[] keys1 = {"A", "A", "C", "C", "F"};
        Integer[] values1 = {1, 2, 3, 1, 2};
        Integer[] values2 = {1, 2, 3, 1, 2, 6};
        assertTrue(graph1.addNodes(keys, values));
        assertFalse(graph1.addNodes(keys1, values1));
        assertThrows( IllegalArgumentException.class,
                () -> {
                    graph1.addNodes(keys, values2);
                }
        );
    }

    /**
     * Testing addEdge()
     * Method is tested with the following possibilities:
     * 1. Add an edge between two unconnected vertices
     * 2. Add an edge between two vertices but one of them does not exist
     * 3. Add an edge that already exist between two vertices
     * 4. Try to add an edge but Node 'from' does not exist
     */
    @Test
    public void addEdgeTest() {
        Graph<String, Integer> graph = createGraphWithNodes();
        assertTrue(graph.addEdge("A", "B"));
        assertTrue(graph.addEdge("A", "C"));
        assertFalse(graph.addEdge("B", "A"));
        assertFalse(graph.addEdge("B", "Z"));
        assertFalse(graph.addEdge("Z", "A"));
        graph.printGraph();

        Graph<String, Integer> graph1 = createGraphWithNodes();
        String[] toList = {"B", "C", "D", "F"};
        String[] toList1 = {"B", "B", "D", "D"};
        String[] toList2 = {"B", "C", "Z", "F"};
        assertTrue(graph1.addEdges("A", toList));
        assertFalse(graph1.addEdges("A", toList1));
        assertFalse(graph1.addEdges("B", toList2));
        assertFalse(graph1.addEdges("Z", toList));

    }

    /**
     * Testing removeNode()
     * Method is tested with the following possibilities
     * 1. Remove a node that does not exist
     * 2. Remove a node that exist
     *      --> Node without any connection
     *      --> Node with connection
     * Testing removeNodes(arrays)
     * Methods is tested with the following possibilities
     * 1. Remove nodes that does not exist
     * 2. Remove nodes that all exist
     *      --> Nodes without connections
     *      --> Nodes with connections
     */
    @Test
    public void removeNodeTest() {
        Graph<String, Integer> graph = createGraphWithConnectedNodes();
        String[] nodeListNonExist = {"A", "B", "Z"};
        String[] nodeList = {"C", "F"};
        assertFalse(graph.removeNode("Z"));
        assertTrue(graph.removeNode("G"));
        assertTrue(graph.removeNode("A"));
        assertTrue(graph.removeNodes(nodeList));
        assertFalse(graph.removeNodes(nodeListNonExist));
    }

    @Test
    public void printGraphTest() {
        Graph<String, Integer> graph = createGraphWithNodes();
        graph.addEdge("A", "F");
        graph.addEdge("A", "D");
        graph.addEdge("A", "C");
        graph.addEdge("A", "B");
        graph.printGraph();
    }

    /**
     * Testing DFS
     * 1. Try to search for nodes that does not exist, either 'from' or 'to'.
     * 2. Try to search for disconnected nodes, therefore no path between them
     * 3. Search in a graph with only two nodes and one edge between them
     * 4. Search from a node with many edges to a node with one edge.
     * 5. Last search in a more complex trees
     */
    @Test
    public void DFSTest() throws FileNotFoundException {
        Graph<String, Integer> graph = createGraphWithNodes();
        assertArrayEquals(new String[1] ,graph.DFS("Z", "Q"));
        assertArrayEquals(new String[1] ,graph.DFS("A", "Q"));
        assertArrayEquals(new String[1] ,graph.DFS("Z", "A"));

        // 2. Searching for disconnected vertices
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "D");
        assertArrayEquals(new String[1] ,graph.DFS("A", "F"));

        // 3. Searching in a graph with only two vertices and one edge
        Graph<String, Integer> graph1 = new Graph<>();
        graph1.addNode("B", 1);
        graph1.addNode("A", 1);
        graph1.addEdge("A", "B"); String[] s = {"A", "B"};
        assertArrayEquals(s, graph1.DFS("A", "B"));

        // 4. Searching in a graph from a vertex with many edges to one with only one edge
        Graph<String, Integer> graph2 = new Graph<>();
        String[] nodes = {"A", "B", "C", "D", "E", "F", "G"};
        String[] edgesA = {"B", "C", "D", "E", "F", "G"};
        graph2.addNodes(nodes, new Integer[7]);
        graph2.addEdges("A", edgesA); String[] s1 = {"A", "G"};
        assertArrayEquals(s1, graph2.DFS("A", "G"));

        // 5. Searching in a complex tree
        Graph<String, Integer> graph3 = Graph.read("test/DFS Test");
        String[][] paths = createDFSPaths();
        // Path A - E
        assertArrayEquals(paths[0], graph3.DFS("A", "E"));
        // Path A - L
        assertArrayEquals(paths[1], graph3.DFS("A", "L"));
        // Path A - Q
        assertArrayEquals(paths[2], graph3.DFS("A", "Q"));
        // Path A - M
        assertArrayEquals(paths[3], graph3.DFS("A", "M"));
        // Path A - G
        assertArrayEquals(paths[4], graph3.DFS("A", "G"));
        // Path A - F
        assertArrayEquals(paths[5], graph3.DFS("A", "F"));
        // Path G - P
        assertArrayEquals(paths[6], graph3.DFS("G", "P"));
        // Path K - O
        assertArrayEquals(paths[7], graph3.DFS("K", "O"));
        // Path Q - J
        assertArrayEquals(paths[8], graph3.DFS("Q", "J"));
        // Path M - 1
        assertArrayEquals(paths[9], graph3.DFS("M", "I"));
    }

    /**
     * Testing BFS
     * 1. Try to search for nodes that does not exist, either 'from' or 'to'
     * 2. Try to search for disconnected nodes, therefore no path between them
     * 3. Search in a graph with only two nodes and one edge between them
     * 4. Search in a simple graph with only one possible path
     * 5. Search in a complex graph with more than one possible path
     */
    @Test
    public void BFSTest() throws FileNotFoundException {
        Graph<String, Integer> graph = createGraphWithConnectedNodes();
        assertArrayEquals(new String[1], graph.BFS("Z", "L"));
        assertArrayEquals(new String[1], graph.BFS("A", "L"));
        assertArrayEquals(new String[1], graph.BFS("Z", "A"));

        // 2.
        graph.addNode("Z", 1);
        assertArrayEquals(new String[1], graph.BFS("A", "Z"));

        // 3.
        Graph<String, Integer> graph1 = new Graph<>();
        graph1.addNode("A", 1);
        graph1.addNode("B", 1);
        graph1.addEdge("A", "B");
        String[] s = {"A", "B"};
        assertArrayEquals(s, graph1.BFS("A", "B"));

        // 4.
        Graph<String, Integer> graph2 = new Graph<>();
        String[] s1 = {"A", "B", "C", "D"}; Integer[] ints = {1, 2, 3, 4};
        graph2.addNodes(s1, ints);
        graph2.addEdge("A", "B");
        graph2.addEdge("A", "C");
        graph2.addEdge("B", "D");
        graph2.addEdge("B", "C");
        String[] path1 = {"A", "B", "D"};
        assertArrayEquals(path1, graph2.BFS("A", "D"));

        // 5.
        Graph<String, Integer> graph3 = Graph.read("test/BFS Test");
        String[] path2 = {"A", "C", "D", "F", "H"};
        assertArrayEquals(path2, graph3.BFS("A", "H"));
        String[] path3 = {"A", "C"};
        assertArrayEquals(path3, graph3.BFS("A", "C"));
        String[] path4 = {"A", "C", "E"};
        assertArrayEquals(path4, graph3.BFS("A", "E"));
    }

    public Graph<String, Integer> createGraphWithNodes() {
        Graph<String, Integer> graph = new Graph<>();
        String[] keys = {"A", "B", "C", "D", "F"};
        Integer[] values = {1, 2, 3, 1, 2};
        graph.addNodes(keys, values);
        return graph;
    }

    public Graph<String, Integer> createGraphWithConnectedNodes() {
        Graph<String, Integer> graph = new Graph<>();
        String[] keys = {"A", "B", "C", "D", "F", "G"};
        String[] aEdges = {"B", "C", "D", "F"};
        Integer[] values = {1, 2, 3, 1, 2, 7};
        graph.addNodes(keys, values);
        graph.addEdges("A", aEdges);
        graph.addEdge("B", "C");
        graph.addEdge("C", "F");
        graph.addEdge("D", "F");
        graph.addEdge("F", "B");
        return graph;
    }

    private String[][] createDFSPaths() {
        String[][] s = new String[10][];
        // Path A - E
        String[] s1 = {"A", "E"};
        s[0] = s1;
        // Path A - L
        String[] s2 = {"A", "E", "L"};
        s[1] = s2;
        // Path A - Q
        String[] s3 = {"A", "B", "Q"};
        s[2] = s3;
        // Path A - M
        String [] s4 = {"A", "C", "M"};
        s[3] = s4;
        // Path A - G
        String[] s5 = {"A", "D", "F", "G"};
        s[4] = s5;
        // Path A - F
        String[] s6 = {"A", "D", "F"};
        s[5] = s6;
        // Path G - P
        String[] s7 = {"G", "F", "D", "A", "B", "P"};
        s[6] = s7;
        // Path K - O
        String[] s8 = {"K", "E", "A", "C", "O"};
        s[7] = s8;
        // Path Q - J
        String[] s9 = {"Q", "B", "A", "E", "J"};
        s[8] = s9;
        // Path M - I
        String[] s10 = {"M", "C", "A", "D", "F", "I"};
        s[9] = s10;
        return s;
    }
}