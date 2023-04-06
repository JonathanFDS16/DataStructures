import org.junit.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class WordStatTest {

    /**
     * Testing WordStat
     * If the constructor is working properly then all following tests should work.
     * 1. wordCount() is tested, with a K that does not exist and one that exist.
     * 2. wordRank() is tests with K that does and doesn't exist.
     * 3. mostCommonWords() tested with k < 0, k == 0, and k > 0
     * 4. leastCommonWords() tested with k < 0, k = 0, k > 0
     * 5. mostCommonCollocations() is tested with:
     *      * k < 0, k = 0, k > 0
     *      * A baseWord that does and doesn't exist in the list.
     *      * Precede = true and then false.
     * @throws IOException
     */
    @Test
    public void constructorTest() throws IOException {
        WordStat w = new WordStat("src/Text File.txt");

        // 1. wordCount()
        assertEquals(9, w.wordCount("the"));
        assertEquals(7, w.wordCount("is"));
        assertEquals(6, w.wordCount("and"));
        assertEquals(4, w.wordCount("everything"));
        assertEquals(3, w.wordCount("world"));
        assertEquals(3, w.wordCount("feels"));
        assertEquals(2, w.wordCount("are"));
        assertEquals(0, w.wordCount("Jonathan"));

        // 2. wordRank()
        assertEquals(1, w.wordRank("the"));
        assertEquals(2, w.wordRank("is"));
        assertEquals(3, w.wordRank("and"));
        assertEquals(4, w.wordRank("everything"));
        assertEquals(5, w.wordRank("world"));
        assertEquals(5, w.wordRank("feels"));
        assertEquals(6, w.wordRank("are"));
        assertEquals(0, w.wordRank("Jonathan"));

        // 3. mostCommonWords
        String[] mostCommon1 = {"the", "is", "and", "everything", "feels", "world", "are"};
        String[] mostCommon2 = {"the", "is", "and"};
        String[] mostCommon3 = {};
        assertArrayEquals(mostCommon1, w.mostCommonWords(7));
        assertArrayEquals(mostCommon2, w.mostCommonWords(3));
        assertArrayEquals(mostCommon3, w.mostCommonWords(0));
        assertThrows(IllegalArgumentException.class,
                () -> {
                    w.mostCommonWords(-1);
                });

        // 4. leastCommonWords
        String[] leastCommon1 = {"with", "will", "way", "this", "that", "taking", "sun", "sky"};
        String[] leastCommon2 = {"with", "will", "way"};
        String[] leastCommon3 = {};
        assertArrayEquals(leastCommon1, w.leastCommonWords(8));
        assertArrayEquals(leastCommon2, w.leastCommonWords(3));
        assertArrayEquals(leastCommon3, w.leastCommonWords(0));
        assertThrows(IllegalArgumentException.class,
                () -> {
                    w.leastCommonWords(-1);
                });

        //5. mostCommonCollocations
        String[] commonCol1 = {};
        String[] commonCol2 = {"the", "is", "and", "everything", "feels", "world", "are", "free", "from", "in"};
        String[] commonCol3 = {"the", "is", "and", "are", "everything", "feels", "world", "a", "air", "birds"};
        String[] commonCol4 = {"and", "is", "the", "everything", "so", "be", "blue", "breeze", "clear", "cool"};
        assertArrayEquals(commonCol1, w.mostCommonCollocations(10, "the", true));
        assertArrayEquals(commonCol2, w.mostCommonCollocations(10, "the", false));
        assertArrayEquals(commonCol3, w.mostCommonCollocations(10, "boon", true));
        assertArrayEquals(commonCol4, w.mostCommonCollocations(10, "boon", false));
        assertArrayEquals(commonCol2, w.mostCommonCollocations(10, "okay", true));
        assertArrayEquals(commonCol1, w.mostCommonCollocations(10, "okay", false));
        assertArrayEquals(commonCol1, w.mostCommonCollocations(0, "boon", true));
        assertThrows(IllegalArgumentException.class,
                () -> {
                    w.mostCommonCollocations(-1, "boon", false);
                    w.mostCommonCollocations(10, "Jonathan", true);
                });
    }

}