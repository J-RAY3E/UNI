package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


public class Clear extends Command {

    public Clear(CollectionManager storageManager,Integer numArguments){
        super(storageManager,numArguments);
    }
    @Override
    public String  description(){
        return "clear - clear the all the storage";
    };

    @Override
    public Response execute(String  ...args){
        try {
            this.collectionManager.getCollection().clear();
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e) {
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };
    
}
