package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.Storage.CollectionManager;


/**
 * Command to remove elements greater than a given one.
 */
public final class RemoveGreater extends Command {

    private Worker parameter1;
    public RemoveGreater( Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "remove_greater {element} - remove all elements greater than the given one";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            collectionManager.getCollection().removeIf(existingWorker -> existingWorker.getSalary() >= this.parameter1.getSalary());
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }


    @Override
    public void setParameters(String... args){
        this.parameter1 = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                .createWorker(args[0]);
    }
}