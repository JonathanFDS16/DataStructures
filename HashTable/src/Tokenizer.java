/**
 * Tokenizer.
 * @author Jonathan F Da Silva
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class Tokenizer creates an array with normalized text.
 */
public class Tokenizer {

    private ArrayList<String> arrayList = new ArrayList<>();

    /**
     * Creates an instance with normalized text from a file.
     * @param file the file with text
     * @throws IOException
     */
    public Tokenizer(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineRead = reader.readLine();
        // Loop through all lines in the text file.
        while (lineRead != null) {
            normalize(lineRead);                    // Normalize each line.
            lineRead = reader.readLine();           // Next line.
        }
    }

    /**
     * Creates an instance with normalized text from an array.
     * @param text a array of Strings
     */
    public Tokenizer(String[] text) {
        for (int i = 0; i < text.length; i++) {
            normalize(text[i]);
        }
    }

    /**
     * Returns the array with the normalized words.
     * @return an arrayList with normalized words
     */
    public ArrayList<String> wordList() {
        return  arrayList;
    }

    private void normalize(String lineRead) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lineRead.length(); i++) {
            // If cahr is a letter append to builder.
            if (Character.isLetter(lineRead.charAt(i))) {
                builder.append(Character.toLowerCase(lineRead.charAt(i)));
            }
            // If is the last char or ' ' or '\n' or ... Add word to array and delete the builder.
            if (!builder.toString().equals("") && (i + 1 == lineRead.length() || lineRead.charAt(i) == '\t'
                    || lineRead.charAt(i) == '\n' || lineRead.charAt(i) == '\r' || lineRead.charAt(i) == ' ')) {
                arrayList.add(builder.toString());
                builder.delete(0, Integer.MAX_VALUE);
            }
        }
    }
}
