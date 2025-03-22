package org.example.CommandsManager.Commands;

import java.io.FileNotFoundException;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


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
