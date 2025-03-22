package org.example.commandsManager.commands;


import org.example.classes.Worker;
import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.CreationFactory;
import org.example.readerManager.inputs.InputValidator;
import org.example.readerManager.inputs.Response;
import org.example.readerManager.inputs.InputManagerRegistry;
import org.example.storage.CollectionManager;


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