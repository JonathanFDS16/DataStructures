import org.junit.Test;

import static org.junit.Assert.*;

public class StringManipulationTest {

    @Test
    public void toPostFixTest() {
        assertEquals("3 4 +", StringManipulation.toPostFix("3 + 4"));
        assertEquals("3 4 -", StringManipulation.toPostFix("3 - 4"));
        assertEquals("3 4 *", StringManipulation.toPostFix("3 * 4"));
        assertEquals("3 4 /", StringManipulation.toPostFix("3 / 4"));
        assertEquals("3 4 + 5 * 6 -", StringManipulation.toPostFix("(3 + 4) * 5 - 6"));
        assertEquals("3 4 5 * + 6 -", StringManipulation.toPostFix("3 + 4 * 5 - 6"));
        assertEquals("3 4 5 6 - / +", StringManipulation.toPostFix("3 + 4 / (5 - 6)"));
        assertEquals("3 4 + 5 - 6 *", StringManipulation.toPostFix("(3 + 4 - 5) * 6"));
    }

    @Test
    public void resultTest() {
        assertEquals(7, StringManipulation.result("3 4 +"));
        assertEquals(29, StringManipulation.result(StringManipulation.toPostFix("(3 + 4) * 5 - 6")));
        assertEquals(17, StringManipulation.result("3 4 5 * + 6 -"));
        assertEquals(-1, StringManipulation.result("3 4 5 6 - * +"));
    }

    @Test
    public void smallestNumber() {
        assertEquals("123", StringManipulation.smallestNumber("987123", 3));
        assertEquals("78123", StringManipulation.smallestNumber("789123", 1));
        assertEquals("4", StringManipulation.smallestNumber("70004", 1));
    }

    @Test
    public void unbelievableNumberTest() {
        assertEquals("abdE", StringManipulation.unbelievableString("abDDdddE"));
        assertEquals("A", StringManipulation.unbelievableString("A"));
        assertEquals("a", StringManipulation.unbelievableString("a"));
        assertEquals("opT", StringManipulation.unbelievableString("ZzzZOooPppT"));
    }
}