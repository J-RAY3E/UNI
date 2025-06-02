package org.example.ReaderManager;


import org.example.CommandsManager.CommandsManager;
import org.example.UserUtils.UserManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.UserUtils.ValidationPassword;
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

    private StringBuffer currentRequest;
    private Boolean status = true;
    private UserManager userManager;
    /**
     * Constructor for the Handler class.
     *
     * @param commandsManager The CommandsManager instance to handle commands.
     * @param responseHandler The ResponseHandler instance to handle responses.
     */
    public Handler(CommandsManager commandsManager, ResponseHandler responseHandler, UserManager userManager) {
        this.commandsManager = commandsManager;
        this.responseHandler = responseHandler;
        this.userManager = userManager;
        this.responseHandler.setHandler(this);

    }
    public void validation(){
        String petition = new ValidationPassword().MainPanel(this.userManager,this.responseHandler);
        this.currentRequest = new StringBuffer().append(petition);
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
    public void pullRequest(Connection connection) throws IOException {
        if (this.currentRequest.isEmpty()) {
            return;
        }


        String[] rawInput = this.currentRequest.toString().trim().split("\\s+");
        String commandName = rawInput[0];

        String[] parameters = Arrays.stream(rawInput)
                .skip(1)
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        Request request = this.commandsManager.eject(
                new Request(commandName, parameters, parameters.length, this.userManager)
        );
        if(!(request.getNumParameters() == -1)){
            try {
                byte[] data = Serializer.serialize(request);
                connection.writeMessage(data);
                byte[] message = connection.readMessage();
                Response response = Serializer.deserialize(Response.class,message);
                responseHandler.handle(response);
            }catch (IOException e) {
                connection.closeConnection();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Error detected at moment to pull query to server" +e);
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

    public boolean getConnectionState() {
        return  this.userManager.getIs_Connected();
    }

    public void finish() {
        this.status = false;
    }

    public void disconnect() {
        this.userManager = new UserManager();
    }

    public void setConnection(String s, String s1,String s2, boolean b) {
        this.userManager.setIs_Connected(b);
        this.userManager.setUsername(s);
        this.userManager.setPrivileges(s2);
        this.userManager.setPassword(s1);
    }
}
