package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


public class RemoveLast extends Command {

    public RemoveLast(CollectionManager storageManager, Integer numArguments ){
        super(storageManager,numArguments);
    }
    @Override
    public String description(){
        return "removeLast - remove last element saved in the collection";
    };
    @Override
    public Response execute(String  ...args){
        try{
            this.collectionManager.getCollection().removeLast();
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e){
                return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    }


}
