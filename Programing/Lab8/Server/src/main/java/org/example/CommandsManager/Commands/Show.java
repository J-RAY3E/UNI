package org.example.CommandsManager.Commands;




import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.Comparator;
import java.util.stream.Collectors;


/**
 * Command to display all elements in the collection.
 */
public final class Show extends Command {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a Show command.
     * @param numArguments the expected number of arguments.
     */
    public Show(Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "show - show all elements information from the object collection";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            Response response = new Response(collectionManager.showAll().stream().map(Worker::getInfo).collect(Collectors.joining("\n")), RequestState.RETURNED);
            response.setReturned(collectionManager.showAll());
            return response;
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}
