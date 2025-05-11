package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.io.FileNotFoundException;


/**
 * Command to execute a script from a file.
 */
public final class ExecuteFile extends Command {
    private String parameter1;
    /**
     * Constructs an ExecuteFile command.

     * @param numArguments the expected number of arguments.
     */
    public ExecuteFile(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "execute_file {file} - execute instructions from the specified file";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        if (!FileInputManager.isAvailablePath(this.parameter1)) {
            return new Response("Error: Not available path", RequestState.ERROR);
        }
        try {
            FileInputManager fileInputManager = new FileInputManager(this.parameter1);
            InputManagerRegistry.getInstance().addScriptInput(fileInputManager);
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (FileNotFoundException e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }

    @Override
    public void setParameters(String... args){
        this.parameter1 = args[0];
    }
}
