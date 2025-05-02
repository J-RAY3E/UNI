package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


import java.util.Comparator;


/**
 * Command to find the element with the minimum position value.
 */
public final class MinByPosition extends Command {

    /**
     * Constructs a MinByPosition command.

     * @param numArguments the expected number of arguments.
     */
    public MinByPosition(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "min_by_position - returns the element with the minimum position";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            if (collectionManager.getCollection().isEmpty()){
                return new Response("Collection is empty" + this.getClass().getSimpleName(), RequestState.ERROR);
            }
            return collectionManager.getCollection().stream()
                    .max(Comparator.comparing(worker -> worker.getPosition().ordinal()))
                    .map(worker -> new Response(worker.getInfo(), RequestState.RETURNED))
                    .orElse(new Response(this.getClass().getName(), RequestState.DONE));
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}
