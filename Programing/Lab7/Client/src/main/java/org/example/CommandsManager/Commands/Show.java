package org.example.CommandsManager.Commands;



import org.example.CommandsManager.Commands.CommnadClasses.Command;



/**
 * Command to display all elements in the collection.
 */
public final class Show extends Command {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a Show command.
     * @param numArguments the expected number of arguments.
     */
    public Show(Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "show - show all elements information from the object collection";
    }


}
