package CommandsManager.Commands;


import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

import java.util.Comparator;


/**
 * Command to find the element with the minimum position value.
 */
public final class MinByPosition extends Command {

    /**
     * Constructs a MinByPosition command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public MinByPosition(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "min_by_position - returns the element with the minimum position";
    }

    @Override
    public Response execute(String... args) {
        try {
            if (collectionManager.getCollection().isEmpty()){
                return new Response("Collection is empty" + this.getClass().getSimpleName(), RequestState.ERROR);
            }
            return this.collectionManager.getCollection().stream()
                    .max(Comparator.comparing(worker -> worker.getPosition().ordinal()))
                    .map(worker -> new Response(worker.getInfo(), RequestState.RETURNED))
                    .orElse(new Response(this.getClass().getName(), RequestState.DONE));
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}
