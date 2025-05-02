package CommandsManager.Commands;

import org.example.classes.Worker;
import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.CreationFactory;
import org.example.readerManager.inputs.InputValidator;
import org.example.readerManager.inputs.InputManagerRegistry;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

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