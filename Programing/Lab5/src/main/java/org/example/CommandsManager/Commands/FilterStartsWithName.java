package CommandsManager.Commands;

import org.example.classes.Worker;
import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

import java.util.stream.Collectors;


/**
 * Command to filter elements by name substring.
 */
public final class FilterStartsWithName extends Command {

    /**
     * Constructs a FilterStartsWithName command.
     * @param storageManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public FilterStartsWithName(CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
    }

    @Override
    public String description() {
        return "filter_starts_with_name {name} - print all elements that contain the substring in their name";
    }

    @Override
    public Response execute(String... args) {
        try {
            String output = this.collectionManager.getCollection().stream()
                    .filter(worker -> worker.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                    .map(Worker::getInfo)
                    .collect(Collectors.joining("\n"));

            if (!output.isEmpty()) {
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}