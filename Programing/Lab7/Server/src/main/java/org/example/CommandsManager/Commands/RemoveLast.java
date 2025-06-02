package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBLoader;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.Comparator;
import java.util.Optional;


/**
 * Command to remove the last element saved in the collection.
 */
public final class RemoveLast extends Command {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a RemoveLast command.

     * @param numArguments the expected number of arguments.
     */
    public RemoveLast( Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "remove_last - remove last element saved in the collection";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            if (collectionManager.getCollection().isEmpty()) {
                return new Response("Collection is empty.", RequestState.ERROR);
            } else {
                return new Response(collectionManager.removeLast(this.getUsername()),RequestState.RETURNED);
            }
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}