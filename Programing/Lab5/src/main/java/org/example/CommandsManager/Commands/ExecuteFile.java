package CommandsManager.Commands;

import java.io.FileNotFoundException;

import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.FileInputManager;
import org.example.readerManager.inputs.InputManagerRegistry;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;


/**
 * Command to execute a script from a file.
 */
public final class ExecuteFile extends Command {

    /**
     * Constructs an ExecuteFile command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public ExecuteFile(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "execute_file {file} - execute instructions from the specified file";
    }

    @Override
    public Response execute(String... args) {
        if (!FileInputManager.isAvailablePath(args[0])) {
            return new Response("Error: Not available path", RequestState.ERROR);
        }
        try {
            FileInputManager fileInputManager = new FileInputManager(args[0]);
            InputManagerRegistry.getInstance().addScriptInput(fileInputManager);
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (FileNotFoundException e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}
