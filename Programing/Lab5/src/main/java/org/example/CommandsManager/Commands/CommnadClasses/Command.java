package org.example.CommandsManager.Commands.CommnadClasses;

import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

public abstract class Command {

    protected CollectionManager collectionManager;
    public Integer numArgs;


    protected Command(CollectionManager collectionManager, Integer numArguments){
        this.collectionManager = collectionManager;
        this.numArgs = numArguments;
    }

    public Integer  getNumArgs(){
        return this.numArgs;
    };

    public String  description(){
        return String.format("Command storage %s", this.collectionManager.getClass());
    };

    public Response execute(String... args){
        return new Response("Commands was executed without problems", RequestState.DONE);
    };
} 
