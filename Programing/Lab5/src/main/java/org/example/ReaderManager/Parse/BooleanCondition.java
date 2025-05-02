package ReaderManager.Parse;

/**
 * Utility class for parsing boolean conditions from user input.
 */
public final class BooleanCondition {

    private BooleanCondition() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    /**
     * Parses a string input into a boolean value.
     * @param input the user input.
     * @return true for 'y', false for 'n'.
     * @throws IllegalArgumentException if input is invalid.
     */
    public static boolean parse(String input) {
        input = input.trim().toLowerCase();
        if (input.equals("y")) {
            return true;
        }
        if (input.equals("n")) {
            return false;
        }
        throw new IllegalArgumentException("Only 'y' or 'n' are valid inputs.");
    }
}
