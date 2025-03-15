package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.StackInputs;
import org.example.Storage.CollectionManager;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UpdateById extends Command {

    public UpdateById(CollectionManager collectionManager,Integer numArguments){
        super(collectionManager,numArguments);
    }
    @Override
    public String description(){
        return "update_by_id id - update element by given id";
    };
    @Override
    public Response execute(String... args){

        try {
            int id = Integer.parseInt(args[0]);
            Worker worker = new CreationManager(new InputValidator(StackInputs.getCurrentInput())).creationelement(id);
            IntStream.range(0, this.collectionManager.getCollection().size()).filter(i ->  this.collectionManager.getCollection().get(i).getId() == id).findFirst().ifPresent(i -> this.collectionManager.getCollection().set(id, worker));
            return new Response(this.getClass().toString(), RequestState.DONE);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };
}
