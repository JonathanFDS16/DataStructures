/**
 * WordStat class provides statistics about a text file
 * @author Jonathan F Da Silva
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class WordStat {

    private ArrayList<String> wordList;

    private ArrayList<HashTable.Entry> wordRank = new ArrayList<>();

    private HashTable<Integer> hashCount = new HashTable<>();

    private HashTable<Integer> hashRank = new HashTable<>();

    /**
     * Creates a instance with statistics of a text file
     * @param file The text file
     * @throws IOException
     */
    public WordStat(String file) throws IOException {
        Tokenizer t = new Tokenizer(file);
        wordList = t.wordList();
        // Method that will create all statistics.
        takeWholeStats(wordList, hashCount, hashRank, wordRank);
    }

    /**
     * Creates an insatnce with statistics based on a String array input
     * @param text The array of Strings.
     */
    public WordStat(String[] text) {
        Tokenizer t = new Tokenizer(text);
        wordList = t.wordList();
        // Method that will crate all statistics.
        takeWholeStats(wordList, hashCount, hashRank, wordRank);
    }

    /**
     * Counts how many times a word occurred in a text file.
     * @param word the word to be counted.
     * @return how many times the word occurred in the text file.
     */
    public int wordCount(String word) {
        int wordCount = 0;
        // If there is not such word in hashCount an exception is thrown.
        try {
            wordCount = hashCount.get(word);
        } catch (NoSuchElementException e) {    // Catch the exception, because such word does not exist.
            return 0;
        }
        return wordCount;
    }

    /**
     * Rank of a word according to how many times it appeared in the text file.
     * @param word the word
     * @return the rank of the word
     */
    public int wordRank(String word) {
        int wordRank = 0;
        try {
            wordRank = hashRank.get(word);
        } catch (NoSuchElementException e) {
            return 0;
        }
        return wordRank;
    }

    /**
     * Returns an array with the k most common words in the text file.
     * @param k the number of common words
     * @return an array with the common words
     */
    public String[] mostCommonWords(int k) {
        if (k < 0)
            throw new IllegalArgumentException();
        // If k is way bigger than words in text file.
        else if (k > wordRank.size())
            k = wordList.size();
        String[] array = new String[k];
        // Loop to add the common words in array.
        for (int i = 0; i < k && i < wordRank.size(); i++) {
            array[i] = wordRank.get(i).getKey();
        }
        return array;
    }

    /**
     * Returns an array with the k least common words in the text file.
     * @param k the number of common words
     * @return an array with the common words.
     */
    public String[] leastCommonWords(int k) {
        if (k < 0)
            throw new IllegalArgumentException();
        else if (k > wordRank.size())
            k = wordRank.size();

        String[] array = new String[k];
        // Loop to add the common words to the String array.
        for (int i = wordRank.size() - 1, j = 0; i >= wordRank.size() - k; i--) {
            array[j++] = wordRank.get(i).getKey();
        }
        return array;
    }

    /**
     * Returns the k most common words that comes before a base word or the most common after the base word.
     * @param k the number of most common words
     * @param baseWord the baseWord in relation to the search.
     * @param precede false - for most common words before baseWord / true - for most common words after baseWord
     * @return
     */
    public String[] mostCommonCollocations(int k, String baseWord, boolean precede) {
        HashTable<Integer> h = new HashTable<>();
        ArrayList<String> newWordList = new ArrayList<>();
        ArrayList<HashTable.Entry> rank = new ArrayList<>();

        if (k < 0)
            throw new IllegalArgumentException();
        else if (k > wordList.size())
            k = wordList.size();
        // If baseWord is not in text file it will throw an exception.
        try {
            hashRank.get(baseWord);
        }
        catch (NoSuchElementException e) {          // If exception is catch then no baseWord does not exist.
            throw new IllegalArgumentException();
        }

        if (precede) {
            int i = 0;
            // Will add words to array until it encounters baseWord
            while (i < wordList.size() && !wordList.get(i).equals(baseWord)) {
                newWordList.add(wordList.get(i++));
            }
        }
        else {
            // Loop will add words to array after in encounters baseWord
            for (int i = 0, j = 0; i < wordList.size(); i++)
                if (j != 1 && wordList.get(i).equals(baseWord))
                    j++;
                else if (j == 1)
                    newWordList.add(wordList.get(i));
        }
        // Method that counts how many times a word occurred and rank them in an array
        hashCountAndArrayRank(newWordList, h, rank);

        String[] commonCol = {};
        // Check for emptiness to avoid IndexOutOfBounds
        if (!rank.isEmpty())
            commonCol = new String[k];
        for (int i = 0; i < k && !rank.isEmpty() && i < rank.size(); i++) {
            commonCol[i] = rank.get(i).getKey();
        }
        return commonCol;
    }

    /**
     * Takes the statistics of the text file. Word count and word ranking.
     * @param normalized the array of with normalized words
     * @param hashCount the hashTable that will store entries with wordCount value
     * @param hashRank the hashTable that will store entries with their wordRank value
     * @param wordRank an array that will store the entries sorted by their rank.
     */
    private void takeWholeStats(ArrayList<String> normalized ,
                                HashTable<Integer> hashCount,
                                HashTable<Integer> hashRank,
                                ArrayList<HashTable.Entry> wordRank) {
        hashCountAndArrayRank(normalized, hashCount, wordRank);
        // Loop that takes entries from ArrayRank and will add to hashTable with wordRank values
        for (int i = 0, j = 1; i < wordRank.size(); i++) {     // For each word sorted, rank them in new hashtable
            // If the first entry
            if (i == 0)
                hashRank.put(wordRank.get(i).getKey(), j);
            // Or if have the same rank as previous index
            else if (wordRank.get(i - 1).getValue() == wordRank.get(i).getValue())
                hashRank.put(wordRank.get(i).getKey(), (j));
            // Otherwise increase the rank.
            else
                hashRank.put(wordRank.get(i).getKey(), ++j);
        }
    }

    /**
     * Method that will create the wordCount statistics and an array with ranked words.
     * @param normalized an array with the normalized words
     * @param hashCount the HashTable that will store words with wordCount values
     * @param wordRank the array with the words ranked by their ranking
     */
    private void hashCountAndArrayRank(ArrayList<String> normalized,
                                       HashTable<Integer> hashCount,
                                       ArrayList<HashTable.Entry> wordRank) {
        // Array that won't have repeated words.
        ArrayList<String> singleWords = new ArrayList<>();
        // For each string in the normalized string array/
        for (String s : normalized) {
            // This count keeps track of the words counting
            int count = -1;
            // Try to see is such key exist
            try {
                count = hashCount.get(s);
            } catch (NoSuchElementException e) {    // If key don't exist then add to hashCount with its new default count
                hashCount.put(s, 1);
                singleWords.add(s);                           // Then add word to array without repeated words
            }
            // If element exist, then add to hashtable with a count increased by 1
            if (count != -1)
                hashCount.put(s, ++count);
        }
        // Loop through array without repeated words to create an array with Entries and their wordCount value
        for (String s : singleWords) {
            wordRank.add(new HashTable(). new Entry(s, hashCount.get(s)));
        }
        // Sort the wordRank array, then all words will be ranked by their wordCount.
        Collections.sort(wordRank);
    }
}
