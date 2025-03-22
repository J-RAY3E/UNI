package org.example.readerManager.inputs;

import org.example.readerManager.validators.Validator;
import org.example.readerManager.parse.ValueParse;

/**
 * Handles user input validation and parsing.
 */
public final class InputValidator {

    private final InputManager inputManager;

    /**
     * Constructs an InputValidator with the specified input source.
     * @param inputManager The input manager to read user input.
     */
    public InputValidator(final InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * Reads, validates, and parses user input.
     * @param <T> The type of the expected input.
     * @param message The message to display to the user.
     * @param validator The validator to check the input.
     * @param parser The parser to convert the input to the expected type.
     * @return The parsed value.
     */
    public <T> T execute(String message, Validator<T> validator, ValueParse<T> parser) {
        T value = null;
        do {
            System.out.print(message);
            String input = this.inputManager.nextLine();
            if (input.isEmpty() && validator.validate(null)) {
                return null;
            }
            try {
                value = parser.parse(input);
                if (!validator.validate(value)) {
                    System.out.println("Invalid Format: " + validator.getErrorMessage());
                    value = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid Type.");
            }
        } while (value == null);
        return value;
    }

    /**
     * Reads, validates, and parses user input.
     * @param <T> The type of the expected input.
     * @param validator The validator to check the input.
     * @param parser The parser to convert the input to the expected type.
     * @return The parsed value.
     */
    public  <T> T execute(  Validator<T> validator,ValueParse<T> parser, String input){
        T value = null;
        try{

            value = (input.isEmpty() || input.equalsIgnoreCase("null")) ? null : parser.parse(input);;
            if(!validator.validate(value)){
                System.out.println("Invalid Format: " + validator.getErrorMessage());
                value = null;
            }
        }
        catch (Exception e){
            System.out.println("Wrong Syntax");
        }
        return value;
    }
}
