package CommandsManager.Commands;

import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;


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
            this.collectionManager.getCollection().remove(this.collectionManager.getCollection().size()-1);
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}