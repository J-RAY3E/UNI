package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
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
    private Integer parameter1;
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
            int idx = IntStream.range(0, collectionManager.getCollection().size())
                    .filter(i -> collectionManager.getCollection().get(i).getId() == parameter1)
                    .findFirst().orElse(-1);
            if(!(idx >= 0)) {
                return new Response("Error:ID not founded", RequestState.ERROR);
            }
            Worker worker = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                    .createWorker(parameter1);

            worker.setCreationDate(collectionManager.getCollection().get(idx).getCreationDate());
            collectionManager.getCollection().set(idx, worker);

            return new Response(this.getClass().getSimpleName(), RequestState.DONE);

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
            this.parameter1 = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            throw  new NumberFormatException("The string given its not possible to be parsed");
        }
    }
}