package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.StackInputs;
import org.example.Storage.CollectionManager;


public class RemoveGreater extends Command {

    public RemoveGreater(CollectionManager collectionManager,Integer numArguments){
        super(collectionManager, numArguments);
    }
    
    @Override
    public String description(){
        return "remove_greater {element} - remove all the elements greater than current one";
    };

    @Override
    public Response execute(String  ...args){
        try{
            Worker worker = new CreationManager(new InputValidator(StackInputs.getCurrentInput())).creationelement(this.collectionManager.getID(), args[0]);
            this.collectionManager.getCollection().removeIf(Worker ->  Worker.getSalary() >=  worker.getSalary());
            this.collectionManager.getCollection().add(worker);
            return new Response(String.format("The operation %s was successfully complete",this.getClass()), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Error adding element: " + e.getMessage(), RequestState.ERROR);
        }
    };
}
