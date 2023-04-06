import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class TokenizerTest {

    private String[] normalizedText = {"the", "sun", "is", "shining", "bright",
            "the", "birds", "are", "taking", "flight",
            "the", "world", "is", "full", "of", "light",
            "and", "everything", "feels", "right"};

    @Test
    public void TokenizerTest() throws IOException {
        Tokenizer t = new Tokenizer("src/Text File 2");
        int i = 0;
        for (String s : t.wordList()) {
            assertEquals(s, normalizedText[i++]);
        }
    }

    // Checks if tokenizer is normalizing an array
    @Test
    public void TokenizerTestWithArray() throws IOException {
        String[] text = {"i'M", "going", "To", "make", "twenty-five", "pancakes.", "\n :)", "\tAnd", "you're", "are",
                "going", "\r to be", "super", "HAPPY ", "with", "   ", ",this", "breakfast!!?"};
        String[] norm = {"im", "going", "to", "make", "twentyfive", "pancakes", "and", "youre", "are",
                "going", "to", "be", "super", "happy", "with", "this", "breakfast"};
        Tokenizer t = new Tokenizer(text);
        int i = 0;
        for (String s : t.wordList()) {
            assertEquals(s, norm[i++]);
        }
    }

}