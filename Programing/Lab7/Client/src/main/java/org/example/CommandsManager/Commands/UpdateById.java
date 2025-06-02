package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.InputValidator;


/**
 * Command to update an element by its given ID.
 */
public final class UpdateById extends Command {
    private static final long serialVersionUID = 1L;
    private Worker parameter1;
    /**
     * Constructs an UpdateById command.

     * @param numArguments the expected number of arguments.
     */
    public UpdateById(Integer numArguments) {
        super( numArguments);
    }

    @Override
    public String description() {
        return "update_by_id {id} - update element by given id";
    }

    @Override
    public  void setParameters(String... args){
        try{
            this.parameter1 = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                    .createWorker( Integer.parseInt(args[0]));
        }catch (NumberFormatException e){
            throw  new NumberFormatException("The string given its not possible to be parsed");
        }
    }
}