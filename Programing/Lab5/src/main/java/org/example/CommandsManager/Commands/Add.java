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
 * Command to add a new element to the collection.
 */
public final class Add extends Command {

    /**
     * Constructs an Add command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public Add(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "add {element} - add new element to the collection";
    }

    @Override
    public Response execute(String... args) {
        try {
            Worker worker = new CreationFactory(
                    new InputValidator(InputManagerRegistry.getInstance().getCurrentInput())
            ).createWorker(this.collectionManager.getManagerID().generateID(), args[0]);

            this.collectionManager.getCollection().add(worker);
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}

