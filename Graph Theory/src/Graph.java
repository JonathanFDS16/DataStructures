import javax.xml.crypto.NodeSetData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Graph<K, V> {

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
            neighbors.sort((node1, node2) -> {
                if (node1.getName() instanceof String) {
                    String name1 = (String) node1.getName();
                    String name2 = (String) node2.getName();
                    return name1.compareTo(name2);
                }
                else return 0;
            });
            for (Node n : neighbors) {
                builder.append(n.getName() + " ");
            }
            return builder.toString();
        }
    }

    // Thinking about hashTable
    private Hashtable<K, Node> nodesTable;

    private ArrayList<K> keys = new ArrayList<>();


    public Graph() {
        nodesTable = new Hashtable<>();
    }

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

    public boolean addNodes(K[] names, V[] data) {
        boolean wasAllAdded = true;

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

    public boolean addEdges(K from, K[] toList) {
        boolean check = true;
        // Check is K from exist
        if (!nodesTable.containsKey(from))
            return false;

        for (K element : toList) {
            if (nodesTable.containsKey(element) && !isConnected(from, element)) {
                nodesTable.get(from).addNeighbor(nodesTable.get(element));
                nodesTable.get(element).addNeighbor(nodesTable.get(from));
            }
            else check = false;
        }
        return check;
    }

    public boolean removeNode(K name) {
        if (!nodesTable.containsKey(name))
            return false;

        removeEdges(name);
        return true;
    }

    private void removeEdges(K name) {
        Node temp = nodesTable.get(name);
        if (temp.getNeighbors().isEmpty()) {
            keys.remove(name);
            nodesTable.remove(name);
            return;
        }

        Node first = temp.getNeighbors().getFirst();
        first.getNeighbors().remove(temp);
        temp.getNeighbors().remove(0);
        removeEdges(name);
    }

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

    public void printGraph() {
        for (K name : keys) {
            Node node = nodesTable.get(name);
            System.out.println(node.toString());
        }
    }

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

    public K[] DFS(K from, K to) {
        K[] type = (K[]) Array.newInstance(from.getClass(), 1);
        ArrayList<K> array = new ArrayList<>();
        search(from, to, array);

        // Condition if 'to' is not connected to the graph.
        if (!array.contains(to)) {
            array = new ArrayList<>();
            return array.toArray(type);
        }

        return array.toArray(type);
    }

    private void search(K from, K to, ArrayList<K> list) {
        if (!nodesTable.containsKey(from) || !nodesTable.containsKey(to)) {
            return;
        }
        if (from.equals(to)) {
            list.add(to);
            return;
        }

        K currentNode = from;

        list.add(from);
        LinkedList<Node> neighbors = nodesTable.get(from).getNeighbors();

        for (Node n : neighbors) {
            from = n.getName();
            if (!list.contains(from) && !list.contains(to))
                search(from, to, list);
        }

        if (!list.contains(to))
            list.remove(currentNode);
    }
    public K[] BFS(K from, K to) {
        K[] type = (K[]) Array.newInstance(from.getClass(), 1);

        ArrayList<K> visited = new ArrayList<>();
        ArrayList<K> previous = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();

        if (!nodesTable.containsKey(from) || !nodesTable.containsKey(to)) {
            return previous.toArray(type);
        }

        queue.add(nodesTable.get(from));
        visited.add(from);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            LinkedList<Node> nodeNeighbors = node.getNeighbors();

            if (node.getName().equals(to) || queue.contains(nodesTable.get(to))) {
                previous.add(to);
                return previous.toArray(type);
            }
            for (Node neighbor : nodeNeighbors) {
                if (!visited.contains(neighbor.getName())) {
                    queue.addLast(neighbor);
                    visited.add(neighbor.getName());
                    if (!previous.contains(node.getName()))
                        previous.add(node.getName());
                }
            }
        }

        if (!previous.contains(to)) {
            return new ArrayList<K>().toArray(type);
        }

        return previous.toArray(type);

    }

    private boolean containsNode(K name) {
        return nodesTable.containsKey(name);
    }

    private boolean isConnected(K from, K to) {
        if (nodesTable.containsKey(from) && nodesTable.containsKey(to)) {
           if (nodesTable.get(from).getNeighbors().contains(nodesTable.get(to)))
               return true;
           else return false;
        }
        return false;
    }
}