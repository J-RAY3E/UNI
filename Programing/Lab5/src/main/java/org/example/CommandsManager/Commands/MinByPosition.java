package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.Comparator;


public class MinByPosition extends Command {

    public MinByPosition(CollectionManager collectionManager,Integer numArguments){
        super(collectionManager,numArguments);
    }
    @Override
    public String description(){
        return "min_by_position  - returns the element with the minimum position";
    };
    @Override
    public Response execute(String... args){
        try {
            return this.collectionManager.getCollection().stream().min(Comparator.comparing(Worker -> Worker.getPosition().ordinal())).map(worker ->  new Response(worker.getInfo(), RequestState.RETURNED)).orElse(new Response(this.getClass().toString(), RequestState.DONE));
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };
}
