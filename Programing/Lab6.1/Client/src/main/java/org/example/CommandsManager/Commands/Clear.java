package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

/**
 * Command to clear all elements from the collection.
 */
public final class Clear extends Command {

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
            collectionManager.getCollection().clear();
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }

}