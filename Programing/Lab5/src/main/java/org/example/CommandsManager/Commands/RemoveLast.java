package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


/**
 * Command to remove the last element saved in the collection.
 */
public final class RemoveLast extends Command {

    /**
     * Constructs a RemoveLast command.
     * @param storageManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public RemoveLast(CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
    }

    @Override
    public String description() {
        return "remove_last - remove last element saved in the collection";
    }

    @Override
    public Response execute(String... args) {
        try {
            if (this.collectionManager.getCollection().isEmpty()) {
                return new Response("Error: Collection is already empty.", RequestState.ERROR);
            }
            this.collectionManager.getCollection().removeLast();
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}