package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBLoader;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.stream.IntStream;


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
    public Response execute(CollectionManager collectionManager) {
        try{
            this.parameter1.setWhoModificates(this.getUsername());
            Boolean updated = collectionManager.update(this.parameter1);
            if(updated != null && updated){
                return new Response(String.format("The user %s updated the worker with id %d %n",this.getUsername(),this.parameter1.getId()), RequestState.RETURNED);
            }
            else if(updated != null){
                return new Response(String.format("The worker with id %d was not updated %n",this.parameter1.getId()), RequestState.ERROR);
            }
            return new Response(String.format("The user %s does not inserted the worker with id %d %n" ,this.getUsername(),this.parameter1.getId()), RequestState.ERROR);

        }
        catch (NumberFormatException e) {
            return new Response(e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
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