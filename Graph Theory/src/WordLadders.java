import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class WordLadders {

    private static Hashtable<String, Integer> table = new Hashtable<>();

    private static Hashtable<Integer, String> inverseTable = new Hashtable<>();

    public static Graph<Integer, String> readWordGraph(String fileName) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        // Stores the name of each node from each line
        ArrayList<Integer> names = new ArrayList<>();
        // Stores the data of from each line
        ArrayList<String> data = new ArrayList<>();
        // Stores an array of neighbors for each node from each line
        ArrayList<ArrayList<Integer>> neighborsList = new ArrayList<>();

        bufferedReader.lines().forEach(line -> {
            StringBuilder builder = new StringBuilder();
            ArrayList<Integer> neighbors = new ArrayList<>();   // Stores the neighbors from each line
            boolean firstName = true;                           // Keep track of the first word, therefore name of the node
            boolean firstData = false;                          // Keep track of the 2nd word, therefore data of the node
            for (int i = 0; i < line.length(); i++) {
                builder.append(line.charAt(i));
                // If is the 1st word, and the next char is a whitespace --> save word in 'names'
                if (firstName && Character.isWhitespace(line.charAt(i + 1))) {
                    firstName = false;
                    firstData = true;
                    names.add(Integer.parseInt(builder.toString()));
                    builder.delete(0, Integer.MAX_VALUE);
                    i++;        // increment to avoid saving the next whitespace
                }
                // If is the 2nd word, and either the last char or next char is a whitespace --> save word to data
                else if (firstData && (i + 1 == line.length() || Character.isWhitespace(line.charAt(i + 1)))) {
                    firstData = false;
                    data.add(builder.toString());
                    builder.delete(0, Integer.MAX_VALUE);
                    i++;        // increment to avoid saving the next whitespace
                }
                // if not the above, the word is the neighbors --> save each neighbor
                else if (i + 1 == line.length() || Character.isWhitespace(line.charAt(i + 1))) {
                    neighbors.add(Integer.parseInt(builder.toString()));
                    builder.delete(0, Integer.MAX_VALUE);
                    i++;        // increment to avoid saving the next whitespace
                }
            }
            neighborsList.add(neighbors);
        });
        addToHashtable(data, names);
        return createGraph(names, data, neighborsList);
    }

    private static void addToHashtable(ArrayList<String> key, ArrayList<Integer> value) {
        for (int i = 0; i < key.size(); i++) {
            table.put(key.get(i), value.get(i));
            inverseTable.put(value.get(i), key.get(i));
        }
    }

    private static Graph<Integer, String> createGraph(ArrayList<Integer> n,
                                                      ArrayList<String> d,
                                                      ArrayList<ArrayList<Integer>> neighbors) {
        Integer[] names = n.toArray(new Integer[1]);
        String[] data = d.toArray(new String[1]);
        Graph<Integer, String> graph = new Graph<>();
        graph.addNodes(names, data);
        for (int i = 0; i < names.length; i++) {
            graph.addEdges(names[i], neighbors.get(i).toArray(new Integer[1]));
        }
        return graph;
    }

    public static void main(String... args) throws FileNotFoundException {
        Graph<Integer, String> graph = readWordGraph(args[0]);


        Scanner scan = new Scanner(System.in);
        System.out.println("Input the starting word:");
        String firstWord = scan.nextLine();
        System.out.println("Now type the last word:");
        String secondWord = scan.nextLine();
        System.out.println("Which search would you like to try? DFS or BFS?");
        String searchMethod = scan.nextLine();
        System.out.println();

        while (!firstWord.equals("") && !secondWord.equals("")) {
            if (searchMethod.equalsIgnoreCase("dfs")) {
                System.out.println("Here is your Word Ladder");
                if (table.containsKey(firstWord) && table.containsKey(secondWord)) {
                    if (table.containsKey(firstWord) && table.containsKey(secondWord)) {
                        Integer[] path = graph.DFS(table.get(firstWord), table.get(secondWord));
                        for (Integer i : path) {
                            if (i != null)
                                System.out.println(inverseTable.get(i));
                        }
                    }
                }
                System.out.println();
            } else if (searchMethod.equalsIgnoreCase("bfs")) {
                if (table.containsKey(firstWord) && table.containsKey(secondWord)) {
                    Integer[] path = graph.BFS(table.get(firstWord), table.get(secondWord));
                    System.out.println("Here is your Word Ladder");
                    for (Integer i : path) {
                        if (i != null)
                            System.out.println(inverseTable.get(i));
                    }
                }
                System.out.println();
            }

            System.out.println("Input the another starting word:");
            firstWord = scan.nextLine();
            System.out.println("Type another end word:");
            secondWord = scan.nextLine();
            System.out.println("Which search method? DFS or BFS?");
            searchMethod = scan.nextLine();
            System.out.println();
        }
        System.out.println("Thanks for playing! Bye :)");
    }
}
