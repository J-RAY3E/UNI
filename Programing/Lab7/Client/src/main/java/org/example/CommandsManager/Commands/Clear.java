package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;

/**
 * Command to clear all elements from the collection.
 */
public final class Clear extends Command {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a Clear command.
     * @param numArguments the expected number of arguments.
     */
    public Clear( Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "clear - remove all elements from the storage";
    }

}