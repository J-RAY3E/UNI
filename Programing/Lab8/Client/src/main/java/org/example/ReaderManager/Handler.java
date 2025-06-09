package org.example.ReaderManager;


import org.example.CommandsManager.CommandsManager;
import org.example.UserUtils.UserManager;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.UserUtils.ValidationPassword;
import org.example.connection.Connection;
import org.example.connection.ResponseHandler;

import java.io.IOException;
import java.util.Arrays;

/**
 * Handler processes user input, executes commands, and handles responses.
 * It is responsible for creating requests, pulling requests, and managing the flow of the program.
 */
public final class Handler {

    private final CommandsManager commandsManager;
    private final ResponsePrint responsePrint;
    private final ResponseHandler responseHandler;
    private String currentRequest;
    private Boolean status = true;
    private UserManager userManager;
    /**
     * Constructor for the Handler class.
     *
     * @param commandsManager The CommandsManager instance to handle commands.
     * @param responsePrint The ResponseHandler instance to handle responses.
     */
    public Handler(CommandsManager commandsManager, ResponsePrint responsePrint, ResponseHandler responseHandler,UserManager userManager) {
        this.commandsManager = commandsManager;
        this.responsePrint = responsePrint;
        this.userManager = userManager;
        this.responseHandler = responseHandler;
        this.responsePrint.setHandler(this);

    }

    public void setCurrentRequest(String request){
        this.currentRequest = request;
    }
    public Response pullRequest(Connection connection) throws IOException {
        if (this.currentRequest.isEmpty()) {
            return null;
        }

        String[] rawInput = this.currentRequest.trim().split("\\s+");
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
                return responseHandler.waitForResponse();
            }catch (IOException e) {
                connection.closeConnection();
            }
        }
        return null;
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

    public void ejectRequestPrint(Response response) {
        this.responsePrint.handle(response);
    }

    public UserManager getUserManager(){
        return this.userManager;
    }
}
