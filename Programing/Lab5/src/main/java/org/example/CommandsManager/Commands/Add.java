package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.StackInputs;
import org.example.Storage.CollectionManager;


public class Add extends Command {

    public Add(CollectionManager collectionManager, Integer numArguments){
        super(collectionManager,numArguments);
    }

    @Override
    public String description(){
        return "add {element} - add new element to the collection ";
    };
 
    @Override
    public Response execute(String  ...args){
        try{
            Worker worker = new CreationManager(new InputValidator(StackInputs.getCurrentInput())).creationelement(this.collectionManager.getID(), args[0]);
            this.collectionManager.getCollection().addLast(worker);
            return new Response(String.format(this.getClass().toString()), RequestState.DONE);
        } catch (Exception e) {
            return new Response(e.getMessage() + "in command"  + this.getClass(), RequestState.ERROR);
        }
    };
}
