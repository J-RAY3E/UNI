package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;

import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.InputManagerRegistry;



/**
 * Command to remove elements greater than a given one.
 */
public final class RemoveGreater extends Command {
    private static final long serialVersionUID = 1L;
    private Worker parameter1;
    public RemoveGreater( Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "remove_greater {element} - remove all elements greater than the given one";
    }


    @Override
    public void setParameters(String... args){
        this.parameter1 = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                .createWorker(args[0]);
    }
}