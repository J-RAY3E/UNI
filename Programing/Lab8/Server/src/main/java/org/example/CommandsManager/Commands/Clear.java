package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBLoader;
import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;
import org.example.connection.NotificationManager;

import java.util.ArrayList;

/**
 * Command to clear all elements from the collection.
 */
public final class Clear extends Command {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a Clear command.
     * @param numArguments the expected number of arguments.
     */
    public Clear( Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "clear - remove all elements from the storage";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            boolean cleared = collectionManager.clear(this.getUsername());
            if (cleared){
                return new Response(this.getClass().getSimpleName(), RequestState.UPDATE);
            }
            return new Response("The user does no have elements", RequestState.ERROR);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }

}