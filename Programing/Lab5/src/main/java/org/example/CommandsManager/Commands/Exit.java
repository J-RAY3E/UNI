package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


public class Exit extends Command {

    public Exit(CollectionManager storageManager,Integer numArguments) {
        super(storageManager,numArguments);
    }
    @Override
    public String description(){
        return "exit - close program";
    };
    @Override
    public Response execute(String  ...args){
        return  new Response(this.getClass().toString(), RequestState.EXIT);
    };
}
