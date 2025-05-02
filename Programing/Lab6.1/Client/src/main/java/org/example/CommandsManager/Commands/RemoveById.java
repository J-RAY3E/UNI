package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Command to remove an element by its ID.
 */
public final class RemoveById extends Command {
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
    public Response execute(CollectionManager collectionManager) {
        try {
            collectionManager.getCollection().removeIf(worker -> worker.getId() == this.parameter1);
            String output = collectionManager.getCollection().stream()
                    .filter(worker -> worker.getName().contains(String.valueOf(this.parameter1)))
                    .map(Worker::getInfo)
                    .collect(Collectors.joining("\n"));

            if (!output.isEmpty()) {
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (NumberFormatException e) {
            return new Response("ID should be an integer value", RequestState.ERROR);
        } catch (NullPointerException e) {
            return new Response("ID value not found", RequestState.ERROR);
        }
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
