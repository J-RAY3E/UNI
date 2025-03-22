package org.example.readerManager;

import org.example.commandsManager.CommandsManager;
import org.example.readerManager.inputs.InputManagerRegistry;
import org.example.readerManager.inputs.Request;
import org.example.readerManager.inputs.Response;

import java.util.Arrays;

/**
 * Handler processes user input, executes commands, and handles responses.
 * It is responsible for creating requests, pulling requests, and managing the flow of the program.
 */
public final class Handler {

    private final CommandsManager commandsManager;
    private final ResponseHandler responseHandler;
    private StringBuffer currentRequest;
    private Boolean status = true;

    /**
     * Constructor for the Handler class.
     * @param commandsManager The CommandsManager instance to handle commands.
     * @param responseHandler The ResponseHandler instance to handle responses.
     */
    public Handler(CommandsManager commandsManager, ResponseHandler responseHandler) {
        this.commandsManager = commandsManager;
        this.responseHandler = responseHandler;
    }

    /**
     * Creates a new request by reading user input.
     */
    public void createRequest() {
        this.currentRequest = new StringBuffer();

        InputManagerRegistry inputManager = InputManagerRegistry.getInstance();
        if (inputManager.getCurrentInput() == null) {
            System.out.println("Error: No input source available.");
            setState(false);
            return;
        }

        if (!inputManager.getCurrentInput().hasNextLine()) {
            inputManager.removeCurrentInput();
        }

        this.currentRequest.append(inputManager.getCurrentInput().nextLine());
    }

    /**
     * Pulls the request and executes the corresponding command.
     */
    public void pullRequest() {
        if (this.currentRequest.isEmpty()) {
            return;
        }

        String[] parameters = this.currentRequest.toString().split(" ");
        Response response = this.commandsManager.eject(new Request(parameters[0], Arrays.copyOfRange(parameters, 1, parameters.length), parameters.length - 1));
        responseHandler.handle(response);
    }

    /**
     * Sets the state of the handler.
     * @param status The new status to be set.
     */
    public void setState(Boolean status) {
        this.status = status;
    }

    /**
     * Gets the current state of the handler.
     * @return The current status.
     */
    public boolean getState() {
        return this.status;
    }
}
