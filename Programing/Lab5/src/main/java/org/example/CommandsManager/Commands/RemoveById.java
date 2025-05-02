package CommandsManager.Commands;


import org.example.classes.Worker;
import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

import java.util.stream.Collectors;

/**
 * Command to remove an element by its ID.
 */
public final class RemoveById extends Command {

    /**
     * Constructs a RemoveById command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public RemoveById(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "remove_by_id - remove an element by the given ID";
    }

    @Override
    public Response execute(String... args) {
        try {
            this.collectionManager.getCollection().removeIf(worker -> worker.getId() == Integer.parseInt(args[0]));
            String output = this.collectionManager.getCollection().stream()
                    .filter(worker -> worker.getName().contains(args[0]))
                    .map(Worker::getInfo)
                    .collect(Collectors.joining("\n"));

            if (!output.isEmpty()) {
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (NumberFormatException e) {
            return new Response("ID should be an integer value", RequestState.ERROR);
        } catch (NullPointerException e) {
            return new Response("ID value not found", RequestState.ERROR);
        }
    }
}
