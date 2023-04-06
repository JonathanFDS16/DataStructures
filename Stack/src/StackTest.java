import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void pushTest() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals("1", stack.printStack());
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertEquals("54321", stack.printStack());
    }

    @Test
    public void popTest() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        stack.pop();
        stack.pop();
        assertEquals("12", stack.printStack());
    }

    @Test
    public void peekTest() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Optional.of(3), Optional.of(stack.peek()));
    }
}