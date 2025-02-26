package org.example.ReaderManager.Parse;

public class BooleanCondition {

    public static boolean parse(String input) {
        input = input.trim().toLowerCase();
        if (input.equals("y")) return true;
        if (input.equals("n")) return false;
        throw new IllegalArgumentException("Just valid 'y' or 'n'.");
    }
}
