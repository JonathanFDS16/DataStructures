/**
 * Class that with static functions to manipulate strings.
 */
public class StringManipulation {

    /**
     * Convert a math expression from Infix Notation to PostFix Notation.
     * @param inFix A string in Infix notation format.
     * @return Return a string in Postfix notation.
     */
    public static String toPostFix(String inFix) {
        StringBuilder postFix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        // Loop over the length of inFix String
        for (int i = 0; i < inFix.length(); i++) {
            // If '(' push it to the stack.
            if (inFix.charAt(i) == '(')
                stack.push(inFix.charAt(i));
            // If '*' or '/'.
            else if (inFix.charAt(i) == '*' || inFix.charAt(i) == '/') {
                // Loop pops and append all higher or equal than '*' or '/'.
                while (!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/')) {
                    postFix.append(stack.pop() + " ");
                }
                // Adds the operator after removing all higher or equal priority.
                stack.push(inFix.charAt(i));
            }
            // If '+' or '-'.
            else if (inFix.charAt(i) == '+' || inFix.charAt(i) == '-') {
                // Loop pops and append all higher or equal that '*' or '/'.
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postFix.append(stack.pop() + " ");
                }
                // Adds the operator after removing all higher or equal priority.
                stack.push(inFix.charAt(i));
            }
            // If current char is ')'
            else if (inFix.charAt(i) == ')') {
                // Loop pops all operators inside the '()'
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postFix.append(stack.pop() + " ");
                }
                // Removes the '('.
                stack.pop();
            }
            // If statement makes sure no space from the string is added.
            else if (!Character.isWhitespace(inFix.charAt(i)))
                postFix.append(inFix.charAt(i) + " ");
            // Last if-statement pop and append all remaining operators in the stack.
            if (i == inFix.length() - 1) {
                while (!stack.isEmpty()) {
                    postFix.append(stack.pop() + " ");
                }
                postFix.deleteCharAt(postFix.length() - 1); //delete last whitespace
            }
        }
        return postFix.toString();
    }

    /**
     * Returns the result of a Postfix math expression.
     * @param postFix A String in valid Postfix math expression.
     * @return Return the result of the expression.
     */
    public static int result(String postFix) {
        Stack<Integer> stack = new Stack<>();
        // Loops through all postFix length.
        for (int i = 0; i < postFix.length(); i++) {
            // If character is a number
            if (Character.isDigit(postFix.charAt(i))) {
                int value = Character.getNumericValue(postFix.charAt(i));   // Gets the numerical value of the digit.
                stack.push(value);                                          // Pushes the value to the stack.
            }
            // If character is an operator.
            else if (postFix.charAt(i) == '*' ||postFix.charAt(i) == '/'
                    ||postFix.charAt(i) == '+' ||postFix.charAt(i) == '-') {
                int operand2 = stack.pop();                                 // Gets operand2 from stack.
                int operand1 = stack.pop();                                 // Gets operand1 from stack
                if (postFix.charAt(i) == '*')                               // If char is '*'
                    stack.push(operand1 * operand2);                  // Pushes the result to the stack.
                else if (postFix.charAt(i) == '/')
                    stack.push(operand1 / operand2);
                else if (postFix.charAt(i) == '+')
                    stack.push(operand1 + operand2);
                else if (postFix.charAt(i) == '-')
                    stack.push(operand1 - operand2);
            }
        }
        return stack.peek();                                                // Return the result.
    }

    /**
     * Returns the smallest number obtained after deleting n numbers
     * @param number String representing a number with digits from 0 to 9
     * @param n Number of digits to be deleted
     * @return Return a String with the smaller number
     */
    public static String smallestNumber(String number, int n) {
        StringBuilder builder = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        int deletions = 0;
        // Loop through the number length.
        for (int i = 0; i < number.length(); i++) {
            int value = Character.getNumericValue(number.charAt(i));        // Gets the value of the digit
            if (stack.isEmpty()) {                                          // If stack is empty
                stack.push(value);
            }
            // If top of the stack is bigger than value and there are no more deletions than n
            else if (stack.peek() > value && deletions != n) {
                    stack.pop();
                    stack.push(value);
                    deletions += 1;
            }
            // If all deletions were made than just add the rest of the numbers.
            else if (deletions >= n - 1)
                stack.push(value);
            // Else (a number was skipped) than a deletion was make
            else
                deletions += 1;
        }
        // Loop pops all digits and append to a String
        while (!stack.isEmpty()) {
                builder.append(stack.pop());
        }
        builder.reverse();  // Reverse the String to the right order.

        // Loop to remove leading zeros
        while (builder.toString().charAt(0) == '0')
            builder.deleteCharAt(0);

        return builder.toString();
    }

    /**
     * Take a String with English letters and deletes letters followed by its uppercase or vice-versa.
     * @param s String made of letters
     * @return Return a new string without the letters followed by its uppercase or lowercase counterpart.
     */
    public static String unbelievableString(String s) {
        StringBuilder builder = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        // Loop through s length.
        for (int i = 0; i < s.length(); i++) {
            stack.push(s.charAt(i));
            // Loop that removes the letters that are followed by its upper or lowercase counterpart.
            while (!stack.isEmpty() && i != s.length() - 1 && Math.abs(stack.peek() - s.charAt(i + 1)) == 32) {
                stack.pop();
                i += 1;
            }
        }
        // Loop to append the character into a string
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        builder.reverse();  // reverse the String
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(toPostFix("3 + 4"));
        System.out.println(toPostFix("3 + 4 * 5 - 6"));
        System.out.println(toPostFix("3 + 4 * (5 - 6)"));
        System.out.println(result(toPostFix("3 + 4")));
        System.out.println(result(toPostFix("3 + 4 * 5 - 6")));
        System.out.println(result(toPostFix("3 + 4 * (5 - 6)")));
        System.out.println(smallestNumber("51203728", 4));
        System.out.println(smallestNumber("78987321", 1));
        System.out.println(unbelievableString("abDDdddE"));
        System.out.println(unbelievableString("abBBDDdddEe"));
    }
}
