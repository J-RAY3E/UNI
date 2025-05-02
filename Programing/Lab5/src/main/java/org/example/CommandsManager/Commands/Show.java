package CommandsManager.Commands;

import org.example.classes.Worker;
import CommandsManager.Commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

import java.util.stream.Collectors;


/**
 * Command to display all elements in the collection.
 */
public final class Show extends Command {

    /**
     * Constructs a Show command.
     * @param storageManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public Show(CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
    }

    @Override
    public String description() {
        return "show - show all elements information from the object collection";
    }

    @Override
    public Response execute(String... args) {
        try {
            String output = this.collectionManager.getCollection().stream()
                    .map(Worker::getInfo)
                    .collect(Collectors.joining(""));

            if (!output.isEmpty()) {
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}
