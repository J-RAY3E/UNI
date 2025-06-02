package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

/**
 * Command to display metadata of the collection object.
 */
public final class Info extends Command {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs an Info command.
     * @param numArguments the expected number of arguments.
     */
    public Info(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "info - show metadata of the collection object";
    }

}