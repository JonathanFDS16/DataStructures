
import org.junit.Test;

import static org.junit.Assert.*;

public class TAUnitTest {
    @Test
    public void testPopPush() {

        Stack<Integer> testStack = new Stack<>();

        // test no element
        assertNull(testStack.pop());

        // add 5 elements
        testStack.push(1);
        testStack.push(2);
        testStack.push(3);
        testStack.push(4);
        testStack.push(5);

        // remove 3 of them
        assertEquals((Integer) 5, testStack.pop());
        assertEquals((Integer) 4, testStack.pop());
        assertEquals((Integer) 3, testStack.pop());

        // add two more elements
        testStack.push(6);
        testStack.push(7);

        // remove all elements
        assertEquals((Integer) 7, testStack.pop());
        assertEquals((Integer) 6, testStack.pop());
        assertEquals((Integer) 2, testStack.pop());
        assertEquals((Integer) 1, testStack.pop());

        // check stack is now empty
        assertNull(testStack.pop());

        // push 3 more values
        testStack.push(8);
        testStack.push(9);
        testStack.push(10);

        // pop 3 values
        assertEquals((Integer) 10, testStack.pop());
        assertEquals((Integer) 9, testStack.pop());
        assertEquals((Integer) 8, testStack.pop());

        // check stack is empty
        assertNull(testStack.pop());

        // check stack is still empty
        assertNull(testStack.pop());

    }

    @Test
    public void testPeek() {

        Stack<String> testStack = new Stack<>();

        // check stack is initially empty
        assertNull(testStack.pop());
        assertNull(testStack.peek());

        // push 1 value
        testStack.push("A");

        // peek one value
        assertEquals("A", testStack.peek());

        // push 4 values
        testStack.push("B");
        testStack.push("C");
        testStack.push("D");
        testStack.push("E");

        // check peek does not change state of stack
        assertEquals("E", testStack.peek());
        assertEquals("E", testStack.peek());

        // pop 3 values -- should only be 2 values left
        assertEquals("E", testStack.pop());
        assertEquals("D", testStack.pop());
        assertEquals("C", testStack.pop());

        // add two more -- total 4
        testStack.push("F");
        testStack.push("G");

        // check peek then pop -- 3 left
        assertEquals("G", testStack.peek());
        assertEquals("G", testStack.pop());

        // check peek then pop -- 2 left
        assertEquals("F", testStack.peek());
        assertEquals("F", testStack.pop());

        // check peek then pop -- 1 left
        assertEquals("B", testStack.peek());
        assertEquals("B", testStack.pop());

        // pop then peek should be empty -- empty now
        assertEquals("A", testStack.pop());
        assertNull(testStack.peek());

        // stack should be empty
        assertNull(testStack.pop());

    }

    @Test
    public void testToPostfix() {
        String infix = "2+3*4";
        String expectedPostfix = "234*+";
        String actualPostfix = StringManipulation.toPostFix(infix);
        assertEquals(expectedPostfix, actualPostfix);

        infix = "2*3+4";
        expectedPostfix = "23*4+";
        actualPostfix = StringManipulation.toPostFix(infix);
        assertEquals(expectedPostfix, actualPostfix);

        infix = "(1+2)*3";
        expectedPostfix = "12+3*";
        actualPostfix = StringManipulation.toPostFix(infix);
        assertEquals(expectedPostfix, actualPostfix);

        infix = "2+3*4/(5-1)";
        expectedPostfix = "234*51-/+";
        actualPostfix = StringManipulation.toPostFix(infix);
        assertEquals(expectedPostfix, actualPostfix);

        infix = "1+2+3+4+5";
        expectedPostfix = "12+3+4+5+";
        actualPostfix = StringManipulation.toPostFix(infix);
        assertEquals(expectedPostfix, actualPostfix);
    }

    @Test
    public void testResult() {
        String postfix = "2 3 * 4 +";
        int expectedValue = 10;
        int actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);

        postfix = "2 3 + 4 *";
        expectedValue = 20;
        actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);

        postfix = "1 2 + 3 *";
        expectedValue = 9;
        actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);

        postfix = "2 3 4 * 5 1 - / +";
        expectedValue = 5;
        actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);

        postfix = "1 2 + 3 + 4 + 5 +";
        expectedValue = 15;
        actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);

        postfix = "12 23 + 34 + 45 + 56 + 67 + 78 + 89 + 99 +";
        expectedValue = 503;
        actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);

        postfix = "123456789";
        expectedValue = 123456789;
        actualValue = StringManipulation.result(postfix);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testSmallestNumber() {
        assertEquals("4", StringManipulation.smallestNumber("70004", 1));
        assertEquals("123", StringManipulation.smallestNumber("123456789", 6));
        assertEquals("1219", StringManipulation.smallestNumber("1432219", 3));
        assertEquals("123", StringManipulation.smallestNumber("123", 0));
        assertEquals("0", StringManipulation.smallestNumber("200", 1));
        assertEquals("0", StringManipulation.smallestNumber("100", 2));
        assertEquals("0", StringManipulation.smallestNumber("123", 3));
    }

    @Test
    public void testUnbelievableString() {
    }
}
