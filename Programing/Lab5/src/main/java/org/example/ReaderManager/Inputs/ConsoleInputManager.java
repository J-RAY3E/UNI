package org.example.readerManager.inputs;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Handles input from the console.
 */
public final class ConsoleInputManager implements InputManager {

    private final Scanner reader;
    private final String path = "Console";

    /**
     * Constructs a ConsoleInputManager instance.
     */
    public ConsoleInputManager() {
        this.reader = new Scanner(System.in);
    }

    /**
     * Retrieves the input source path.
     * @return the path of the input source.
     */
    public String getPath() {
        return path;
    }

    /**
     * Reads the next line from the console.
     * @return the next line as a string.
     */
    public String nextLine() {

        try {
            return this.reader.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("\n >> Input closed. Exiting...");
            System.exit(0);
            return null;
        }
    }

    /**
     * Checks if there is more input available.
     * @return true if input is available, false otherwise.
     */
    public Boolean hasNextLine() {
        return reader.hasNextLine();
    }
}
