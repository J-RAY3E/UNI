package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


import java.util.Comparator;


/**
 * Command to find the element with the minimum position value.
 */
public final class MinByPosition extends Command {
    private static final long serialVersionUID = 1L;
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
            return new Response(collectionManager.minByPosition()+"\n ",RequestState.RETURNED);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}
