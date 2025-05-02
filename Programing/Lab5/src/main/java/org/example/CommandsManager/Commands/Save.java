package CommandsManager.Commands;

import CommandsManager.Commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.readerManager.parse.WriteJson;
import org.example.storage.CollectionManager;




/**
 * Command to save the elements of the collection.
 */
public final class Save extends Command {

    /**
     * Constructs a Save command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public Save(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "save - save the elements of the collection";
    }

    @Override
    public Response execute(String... args) {
        try {
            new WriteJson(this.collectionManager).saveCollectionToJson();
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}