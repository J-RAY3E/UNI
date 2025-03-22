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

    public RemoveGreater(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "remove_greater {element} - remove all elements greater than the given one";
    }

    @Override
    public Response execute(String... args) {
        try {
            Worker worker = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                    .createWorker(this.collectionManager.getManagerID().generateID(), args[0]);

            this.collectionManager.getCollection().removeIf(existingWorker -> existingWorker.getSalary() >= worker.getSalary());
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}