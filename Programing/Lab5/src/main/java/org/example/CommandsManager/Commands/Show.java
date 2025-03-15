package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.stream.Collectors;


public class Show extends Command {

    public Show(CollectionManager storageManager, Integer numArguments){
        super(storageManager,numArguments);
    }

    @Override
    public String  description(){
        return "show - show all the elements information from the object collection";
    };

    @Override
    public Response execute(String  ...args){

        try {
            String output = this.collectionManager.getCollection().stream().map(Worker::getInfo).collect(Collectors.joining(""));
            if(!output.isEmpty()){
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    }

};

