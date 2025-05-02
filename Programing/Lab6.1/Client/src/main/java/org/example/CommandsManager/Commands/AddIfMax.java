package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

/**
 * Command to add an element if its salary is the highest in the collection.
 */
public final class AddIfMax extends Command {
    private Worker parameter1;
    /**
     * Constructs an AddIfMax command.
     * @param numArguments the expected number of arguments.
     */
    public AddIfMax(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "add_if_max {element} - add current element if its salary is higher than existing ones";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            if (collectionManager.getCollection().stream()
                    .noneMatch(existingWorker -> existingWorker.getSalary() > this.parameter1.getSalary())) {
                collectionManager.getCollection().add(this.parameter1);
            }

            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
    @Override
    public void setParameters(String... args){
        this.parameter1 = new CreationFactory(
                new InputValidator(InputManagerRegistry.getInstance().getCurrentInput())
        ).createWorker(args[0]);

    }
}