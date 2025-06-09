package org.example.CommandsManager.Commands;



import org.example.CommandsManager.Commands.CommnadClasses.Command;

/**
 * Command to remove an element by its ID.
 */
public final class RemoveById extends Command {
    private static final long serialVersionUID = 1L;
    private Integer parameter1;
    /**
     * Constructs a RemoveById command.
     * @param numArguments the expected number of arguments.
     */
    public RemoveById(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "remove_by_id - remove an element by the given ID";
    }

    @Override
    public void setParameters(String... args){
        try{
            this.parameter1 = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            throw  new NumberFormatException("The string given its not possible to be parsed");
        }
    }
}
