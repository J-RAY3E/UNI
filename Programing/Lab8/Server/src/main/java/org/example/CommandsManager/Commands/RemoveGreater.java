package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBLoader;
import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.Storage.CollectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    public Response execute(CollectionManager collectionManager) {
        try {
            this.parameter1.setWhoModificates(this.getUsername());
            return new Response(String.format("%s %n",collectionManager.removeGreaterThan(this.parameter1)), RequestState.UPDATE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command %n" + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }


    @Override
    public void setParameters(String... args){
        this.parameter1 = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                .createWorker(args[0]);
    }
}