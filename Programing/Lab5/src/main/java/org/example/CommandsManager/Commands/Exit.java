package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;



/**
 * Command to exit the program.
 */
public final class Exit extends Command {

    /**
     * Constructs an Exit command.
     * @param storageManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public Exit(CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
    }

    @Override
    public String description() {
        return "exit - close program";
    }

    @Override
    public Response execute(String... args) {
        return new Response(this.getClass().getName(), RequestState.EXIT);
    }
}