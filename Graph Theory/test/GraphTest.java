import org.junit.jupiter.api.Test;

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
}