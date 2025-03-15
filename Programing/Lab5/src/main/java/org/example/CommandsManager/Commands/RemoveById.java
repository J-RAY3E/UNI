package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.stream.Collectors;

public class RemoveById extends Command {

    public RemoveById(CollectionManager collectionManager,Integer numArguments){
        super(collectionManager, numArguments);
    }
    @Override
    public String description(){
        return "remove_by_id - close program";
    };
    @Override
    public Response execute(String... args){
        this.collectionManager.getCollection().removeIf(Worker ->  Worker.getId() ==  Integer.parseInt(args[0]));
        try {
            String output = this.collectionManager.getCollection().stream().filter(Worker -> Worker.getName().contains(args[0])).map(Worker::getInfo).collect(Collectors.joining("\n"));
            if(!output.isEmpty()){
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };
}
