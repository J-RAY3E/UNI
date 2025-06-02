package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.InputValidator;


/**
 * Command to add an element if its salary is the highest in the collection.
 */
public final class AddIfMax extends Command {
    private static final long serialVersionUID = 1L;
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
    public void setParameters(String... args){
        this.parameter1 = new CreationFactory(
                new InputValidator(InputManagerRegistry.getInstance().getCurrentInput())
        ).createWorker(args[0]);

    }
}