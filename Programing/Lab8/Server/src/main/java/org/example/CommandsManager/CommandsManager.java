package org.example.CommandsManager;



import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

/**
 * Manages all available commands and their execution.
 */
public final class CommandsManager {
    private final CollectionManager collectionManager;
    /**
     * Constructs a CommandsManager with the given CollectionManager.

     */
    public CommandsManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /**
     * Executes a command based on the given request.
     * @param request The request containing the command and its parameters.
     * @return The response of the command execution.
     */
    public Response eject(Request request) {
        if((request.getCommand().equals("authorization") || request.getCommand().equals("registration")) || this.collectionManager.getDBLoader().authorization(request.getUserManager()) ) {
            return request.getCommandLoaded().execute(this.collectionManager);
        } else if (request.getCommand().equals("exit")) {
            return new Response("The programs is been finished", RequestState.EXIT);
        }
        else{
            return new Response("The user does not exist more", RequestState.DISCONNECT);
        }
    }


}
