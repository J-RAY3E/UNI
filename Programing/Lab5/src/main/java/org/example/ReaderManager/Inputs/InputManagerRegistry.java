package org.example.readerManager.inputs;

import java.util.Stack;

/**
 * Singleton class for managing different input sources.
 * It maintains a stack-like structure for handling multiple input sources.
 */
public class InputManagerRegistry {
    private static InputManagerRegistry instance;
    private final Stack<InputManager> readers = new Stack<>();
    /**
     * Private constructor initializes the registry with console input.
     */
    private InputManagerRegistry() {
        readers.push(new ConsoleInputManager());
    }
    /**
     * Retrieves the singleton instance of the registry.
     * @return the singleton instance.
     */
    public static InputManagerRegistry getInstance() {
        if (instance == null) {
            instance = new InputManagerRegistry();
        }
        return instance;
    }
    /**
     * Adds a new script input source if it is not already in the stack.
     * @param scriptInput the FileInputManager to add.
     */
    public void addScriptInput(FileInputManager scriptInput) {
        if(readers.stream().noneMatch(InputManager -> InputManager.getPath().equals(scriptInput.getPath()) )){
            readers.push(scriptInput);
        }else{
            System.out.println("Current input already in process");
        }
    }
    /**
     * Removes the current input source if more than one exists.
     */
    public void removeCurrentInput() {
        if (readers.size() > 1) {
            readers.pop();
        }
    }
    /**
     * Retrieves the current input source.
     * @return the current InputManager.
     */
    public InputManager getCurrentInput() {
        return readers.peek();
    }
}