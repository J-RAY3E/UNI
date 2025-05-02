package org.example.CommandsManager.Commands.CommnadClasses;



import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.io.Serializable;

public abstract class Command implements Serializable {

    public Integer numArgs;


    protected Command(Integer numArguments){
        this.numArgs = numArguments;
    }

    public Integer  getNumArgs(){
        return this.numArgs;
    };

    public String  description(){
        return "This is a command";
    };

    public Response execute(CollectionManager collectionManager){
        return new Response("Commands was executed without problems", RequestState.DONE);
    };


    public void setParameters(String... args){

    };
} 
