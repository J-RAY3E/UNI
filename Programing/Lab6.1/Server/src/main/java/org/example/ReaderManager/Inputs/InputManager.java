package org.example.ReaderManager.Inputs;

/**
 * Interface for input management.
 * Defines methods for reading user input from different sources.
 */
public interface InputManager {

    /**
     * Reads the next line of input.
     * @return the next input line as a string.
     */
    String nextLine();

    /**
     * Checks if there is another line available in the input source.
     * @return true if another line is available, false otherwise.
     */
    Boolean hasNextLine();

    /**
     * Gets the source path of the input.
     * @return the input source path as a string.
     */
    String getPath();
}