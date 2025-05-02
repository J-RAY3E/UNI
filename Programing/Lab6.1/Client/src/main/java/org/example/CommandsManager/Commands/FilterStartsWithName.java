package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.stream.Collectors;


/**
 * Command to filter elements by name substring.
 */
public final class FilterStartsWithName extends Command {
    private String parameter1;
    /**
     * Constructs a FilterStartsWithName command.
     * @param numArguments the expected number of arguments.
     */
    public FilterStartsWithName(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "filter_starts_with_name {name} - print all elements that contain the substring in their name";
    }

    @Override
    public Response execute(CollectionManager collectionManager ) {
        try {
            String output = collectionManager.getCollection().stream()
                    .filter(worker -> worker.getName().toLowerCase().startsWith(this.parameter1.toLowerCase()))
                    .map(Worker::getInfo)
                    .collect(Collectors.joining("\n"));

            if (!output.isEmpty()) {
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
    @Override
    public void setParameters(String... args){
        this.parameter1 = args[0];
    }
}