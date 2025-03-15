package org.example.CommandsManager.Commands;

import java.io.FileNotFoundException;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.StackInputs;
import org.example.Storage.CollectionManager;


public class ExecuteFile extends Command {

    public ExecuteFile(CollectionManager collectionManager, Integer numArguments){
        super(collectionManager,numArguments);
    }
    @Override
    public String description(){
        return "execute_file file - execute instructions from the file pointed";
    }

    @Override
    public Response execute(String... args){
        if(!FileInputManager.isAvailablePath(args[0])){return new Response("Error: Not available path", RequestState.ERROR );};
        try {
            FileInputManager fileInputManager = new FileInputManager(args[0]);
            StackInputs.addReader(fileInputManager);
            return new Response(String.format(this.getClass().toString()), RequestState.DONE);
        } catch (FileNotFoundException e) {
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);

        }
    }
}
