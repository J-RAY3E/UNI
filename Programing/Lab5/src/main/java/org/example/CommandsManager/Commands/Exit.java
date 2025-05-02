package CommandsManager.Commands;

import CommandsManager.Commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;



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