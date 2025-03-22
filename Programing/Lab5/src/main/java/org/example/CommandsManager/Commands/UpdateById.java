package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.CreationFactory;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.OptionalInt;
import java.util.stream.IntStream;


/**
 * Command to update an element by its given ID.
 */
public final class UpdateById extends Command {

    /**
     * Constructs an UpdateById command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public UpdateById(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "update_by_id {id} - update element by given id";
    }

    @Override
    public Response execute(String... args) {
        try{
            int id = Integer.parseInt(args[0]);
            int idx = IntStream.range(0, this.collectionManager.getCollection().size())
                    .filter(i -> this.collectionManager.getCollection().get(i).getId() == id)
                    .findFirst().orElse(-1);
            if(!(idx >= 0)) {
                return new Response("Error:ID not founded", RequestState.ERROR);
            }
            Worker worker = new CreationFactory(new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()))
                    .createWorker(id);

            worker.setCreationDate(this.collectionManager.getCollection().get(idx).getCreationDate());
            this.collectionManager.getCollection().set(idx, worker);

            return new Response(this.getClass().getSimpleName(), RequestState.DONE);

        }
        catch (NumberFormatException e) {
            return new Response(e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}