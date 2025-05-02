package org.example.CommandsManager;



import org.example.CommandsManager.Commands.Save;
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

        return request.getCommandLoaded().execute(this.collectionManager);
    }

    public void autosave(){
        new Save(0).execute(this.collectionManager);
    }
}
