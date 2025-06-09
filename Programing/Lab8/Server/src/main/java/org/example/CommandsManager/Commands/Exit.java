package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.Enums.Status;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

/**
 * Command to exit the program.
 */
public final class Exit extends Command {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an Exit command.
     * @param numArguments the expected number of arguments.
     */
    public Exit(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "exit - close program";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        return new Response("Done", RequestState.DISCONNECT);
    }
}