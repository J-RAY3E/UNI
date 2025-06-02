package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;



/**
 * Command to find the element with the minimum position value.
 */
public final class MinByPosition extends Command {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a MinByPosition command.

     * @param numArguments the expected number of arguments.
     */
    public MinByPosition(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "min_by_position - returns the element with the minimum position";
    }


}
