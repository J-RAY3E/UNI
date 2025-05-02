package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Parse.WriteJson;
import org.example.Storage.CollectionManager;

/**
 * Command to save the elements of the collection.
 */
public final class Save extends Command {

    /**
     * Constructs a Save command.

     * @param numArguments the expected number of arguments.
     */
    public Save(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "save - save the elements of the collection";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            new WriteJson(collectionManager).saveCollectionToJson();
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}