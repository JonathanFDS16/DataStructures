import javax.xml.crypto.NodeSetData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Graph class.
 * Representation of an unweighted graph
 * @param <K> The key type: name of the vertices
 * @param <V> The value type: value of each vertex.
 */
public class Graph<K, V> {

    /**
     * Node class stores, K, V and its neighbors.
     */
    public class Node {
        private K name;
        private V value;

        private LinkedList<Node> neighbors = new LinkedList<>();

        public Node(K name, V value) {
            this.name = name;
            this.value = value;
        }

        private K getName() {
            return name;
        }

        private V getValue() {
            return value;
        }

        private void addNeighbor(Node neighbor) {
            neighbors.add(neighbor);
        }

        private LinkedList<Node> getNeighbors() {
            return neighbors;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.getName() + " ");
            builder.append(this.getValue() + " ");
            /*
            neighbors.sort((node1, node2) -> {
                if (node1.getName() instanceof String) {
                    String name1 = (String) node1.getName();
                    String name2 = (String) node2.getName();
                    return name1.compareTo(name2);
                } else if (node1.getName() instanceof Integer) {
                    Integer name1 = (Integer) node1.getName();
                    Integer name2 = (Integer) node2.getName();
                    return name2.compareTo(name1);
                } else {
                    return 0;
                }
            });*/
            for (Node n : neighbors) {
                builder.append(n.getName() + " ");
            }
            return builder.toString();
        }
    }

    // Stores all vertices
    private Hashtable<K, Node> nodesTable;

    // Stores all the nodes' keys/names
    private ArrayList<K> keys = new ArrayList<>();

    /**
     * Instantiate a new graph
     */
    public Graph() {
        nodesTable = new Hashtable<>();
    }

    /**
     * Add a node to the graph with name and value
     * @param name the name of the vertex/node
     * @param value the value of the vertex/node
     * @return return true if the node was added to the graph and false if the node already exist in the graph
     */
    public boolean addNode(K name, V value) {
        // Check duplicates and then add
        if (nodesTable.containsKey(name))
            return false;

        // Otherwise add the node to the hashtable
        keys.add(name);
        Node temp = new Node(name, value);
        nodesTable.put(name, temp);
        return true;
    }

    /**
     * Add multiple nodes with name and values
     * @param names an array with nodes names
     * @param data an array with nodes datas
     * @return return true if all nodes were added to the graph and false if some nodes were not added because they are
     * duplicated
     */
    public boolean addNodes(K[] names, V[] data) {
        boolean wasAllAdded = true;     // Keeps track if one node was not added to the graph
        // If arrays are different length
        if (names.length != data.length)
            throw new IllegalArgumentException();

        // Loop goes checks for duplicates and adds nodes with keys not in hashTable
        for (int i = 0; i < names.length; i++) {
           if (!nodesTable.containsKey(names[i])) {
               keys.add(names[i]);
               nodesTable.put(names[i], new Node(names[i], data[i]));
           }
           else wasAllAdded = false;
        }
        return wasAllAdded;
    }

    /**
     * Add an edge between two vertices/nodes
     * @param from the first vertex
     * @param to the second vertex
     * @return return true if both nodes exist and an edge was connected, and false if there was already an edge
     * or if there is no vertex either 'from' or 'to
     */
    public boolean addEdge(K from,K to) {
        // I need to check if both exist
        if (nodesTable.containsKey(from) && nodesTable.containsKey(to)
                && !isConnected(from, to)) {
            // Adds the neighbor for the node with K from
            nodesTable.get(from).addNeighbor(nodesTable.get(to));
            // Adds the neighbor to the node with K to.
            nodesTable.get(to).addNeighbor(nodesTable.get(from));
            return true;
        }
        return false;
    }

    /**
     * Add multiple edges to a single vertex
     * @param from the single vertex
     * @param toList the list of edges
     * @return true if all edges were successfully added and false otherwise
     */
    public boolean addEdges(K from, K[] toList) {
        boolean check = true;
        // Check is K from exist
        if (!nodesTable.containsKey(from) || toList.length == 1)
            return false;

        for (K element : toList) {
            // If element does not exist and is not connected to 'from'
            if (nodesTable.containsKey(element) && !isConnected(from, element)) {
                nodesTable.get(from).addNeighbor(nodesTable.get(element));
                nodesTable.get(element).addNeighbor(nodesTable.get(from));
            }
            else check = false;
        }
        return check;
    }

    /**
     * Remove a node from the graph
     * @param name the name of the node
     * @return true if the node was successfully removed, and false is the node does not exist
     */
    public boolean removeNode(K name) {
        if (!nodesTable.containsKey(name))
            return false;

        removeEdges(name);
        return true;
    }

    // Remove edges from a node in recursion


    /**
     * Remove multiple nodes from the graph
     * @param nodeList the nodes name to be removed
     * @return true if all nodes were removed and false otherwise
     */
    public boolean removeNodes(K[] nodeList) {
        boolean check = true;
        for (int i = 0; i < nodeList.length; i++) {
            if (nodesTable.containsKey(nodeList[i])) {
                removeEdges(nodeList[i]);
            }
            else check = false;
        }
        return check;
    }

    /**
     * Prints the graph with the following format:
     * <nodename> <nodeNeighbor1> <nodeNeighbor2> <nodeNeighbor3> ...
     * ...
     */
    public void printGraph() {
        for (K name : keys) {
            Node node = nodesTable.get(name);
            System.out.println(node.toString());
        }
    }

    /**
     * Cretes a graph from a file that has the same format as printGraph()
     * @param filename the path to the file
     * @return a Graph with all the node and connections
     * @param <V> the Value type for this graph.
     * @throws FileNotFoundException
     */
    public static <V> Graph<String, V> read(String filename) throws FileNotFoundException {
        Graph<String, V> graph = new Graph<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        // For each line of the text file
        reader.lines().forEach( line -> {
            StringBuilder nodeName = new StringBuilder();   // stores the nodeName being read in line
            boolean firstNode = true;                       // keeps track of the first word of the line
            String firstNodeName = null;                    // saves the name of the firstNode
            // Loop through line to do operations in each word found
            for (int i = 0; i < line.length(); i++) {
                nodeName.append(line.charAt(i));            // Append the character to the nodeName
                // If next char is a whitespace or current char is the last of the line
                if (i + 1 == line.length() ||  Character.isWhitespace(line.charAt(i + 1))) {
                    if (firstNode) {
                        firstNode = false;
                        firstNodeName = nodeName.toString();
                        graph.addNode(firstNodeName, null);
                    } else {
                        // If graph does not contain the node/neighbor --> add node to graph
                        if (!graph.containsNode(nodeName.toString()))
                            graph.addNode(nodeName.toString(), null);
                        // If the firstNode is not connected to its neighbor --> add an edge between them
                        if (!graph.isConnected(firstNodeName, nodeName.toString()))
                            graph.addEdge(firstNodeName, nodeName.toString());
                    }
                }
                nodeName.delete(0, Integer.MAX_VALUE);          // Reset the nodeName for the next one
            }
        });
        return graph;
    }

    /**
     * Perform a DFS search in the graph.
     * @param from the first vertex
     * @param to the end vertex
     * @return an array with the path taken to the end vertex
     */
    public K[] DFS(K from, K to) {
        // An array K to save the K type
        K[] type = (K[]) Array.newInstance(from.getClass(), 1);
        ArrayList<K> array = new ArrayList<>();
        // Search method and
        search(from, to, array);

        // If 'to' is not connected to the graph, return an empty array.
        if (!array.contains(to)) {
            array = new ArrayList<>();
            return array.toArray(type);
        }

        return array.toArray(type);
    }

    // Search method using recursion
    private void search(K from, K to, ArrayList<K> list) {
        // If from or to does not exist
        if (!nodesTable.containsKey(from) || !nodesTable.containsKey(to) || nodesTable.get(to).getNeighbors().isEmpty()) {
            return;
        }
        // If to is found, add to list and return
        if (from.equals(to)) {
            list.add(to);
            return;
        }

        K currentNode = from;   // Saves the current node
        list.add(from);     // Adds the first node to list
        LinkedList<Node> neighbors = nodesTable.get(from).getNeighbors(); // 'from' neighbors

        // For each neighbor of 'from'
        for (Node n : neighbors) {
            from = n.getName();                                 // Update from to be the next neighbor
            if (!list.contains(from) && !list.contains(to))     // If the neighbor is not in the list and 'to' was not found
                search(from, to, list);                             // search again
        }

        // no neighbors are 'to' delete the parent. This case only happens at leave nodes
        if (!list.contains(to))
            list.remove(currentNode);
    }

    /**
     * Perform a BFS search in the graph
     * @param from the first vertex
     * @param to the end vertex
     * @return the shortest path taken to find 'to'
     */
    public K[] BFS(K from, K to) {

        ArrayList<K> visited = new ArrayList<>();
        // <Node, itsParent>
        Hashtable<K, K> connectionList = new Hashtable<>();
        LinkedList<Node> queue = new LinkedList<>();
        // If from or to does not exist
        if (!nodesTable.containsKey(from) || !nodesTable.containsKey(to)) {
            return (K[]) Array.newInstance(from.getClass(), 1);
        }

        queue.add(nodesTable.get(from));
        visited.add(from);
        connectionList.put(from, from);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            LinkedList<Node> neighborList = nodesTable.get(node.getName()).getNeighbors();

            for (Node neighbor : neighborList) {
                if (!visited.contains(neighbor.getName())) {
                    queue.addLast(neighbor);
                    visited.add(neighbor.getName());
                    connectionList.put(neighbor.getName(), node.getName());
                }
            }
        }

        return createPath(from, to, connectionList);

    }

    private K[] createPath(K from, K node, Hashtable<K, K> connectionList) {
        K[] type = (K[]) Array.newInstance(from.getClass(), 1);
        ArrayList<K> path = new ArrayList<>();

        path.add(node);
        if (!connectionList.containsKey(node))
            return type;

        K nodeParent = connectionList.get(node);
        while (!nodeParent.equals(from)) {
            path.add(nodeParent);
            nodeParent = connectionList.get(nodeParent);
        }
        path.add(connectionList.get(nodeParent));
        Collections.reverse(path);
        if (path.contains(from) && path.contains(node))
            return path.toArray(type);
        else return type;
    }

    private void removeEdges(K name) {
        Node temp = nodesTable.get(name);
        // If there is no neighbors in 'name'
        if (temp.getNeighbors().isEmpty()) {
            keys.remove(name);          // Remove the name from the list of keys
            nodesTable.remove(name);    // Remove the node from the hashtable
            return;
        }

        Node first = temp.getNeighbors().getFirst();        // Saves the first neighbor
        first.getNeighbors().remove(temp);                  // Remove the node 'temp' from first's neighbors
        temp.getNeighbors().remove(0);                // Remove the 'first' from temp's neighbors
        removeEdges(name);                                  // Do the same thing again
    }

    protected boolean containsNode(K name) {
        return nodesTable.containsKey(name);
    }

    protected boolean isConnected(K from, K to) {
        if (nodesTable.containsKey(from) && nodesTable.containsKey(to)) {
           if (nodesTable.get(from).getNeighbors().contains(nodesTable.get(to)))
               return true;
           else return false;
        }
        return false;
    }
}