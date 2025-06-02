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
    private static final long serialVersionUID = 1L;
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
    public void setParameters(String... args){
        this.parameter1 = args[0];
    }
}
