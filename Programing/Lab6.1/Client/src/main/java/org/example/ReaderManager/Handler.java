package org.example.ReaderManager;


import org.example.CommandsManager.CommandsManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.Connection;

import java.io.IOException;
import java.util.Arrays;

/**
 * Handler processes user input, executes commands, and handles responses.
 * It is responsible for creating requests, pulling requests, and managing the flow of the program.
 */
public final class Handler {

    private final CommandsManager commandsManager;
    private final ResponseHandler responseHandler;
    private final Connection connection;
    private StringBuffer currentRequest;
    private Boolean status = true;

    /**
     * Constructor for the Handler class.
     * @param commandsManager The CommandsManager instance to handle commands.
     * @param responseHandler The ResponseHandler instance to handle responses.
     */
    public Handler(CommandsManager commandsManager, ResponseHandler responseHandler, Connection connection) {
        this.commandsManager = commandsManager;
        this.responseHandler = responseHandler;
        this.connection = connection;
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
        Request request   = this.commandsManager.eject(new Request(parameters[0], Arrays.copyOfRange(parameters, 1, parameters.length), parameters.length - 1));
        if(!(request.getNumParameters() == -1)){
            try {
                byte[] data = Serializer.serialize(request);
                connection.writeMessage(data);
                byte[] message = connection.readMessage();
                Response response = Serializer.deserialize(Response.class,message);
                responseHandler.handle(response);
            }catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            responseHandler.handle(new Response(request.getCommand(), RequestState.ERROR));
        }
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
