package org.example.commandsManager.commands;


import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

/**
 * Command to display metadata of the collection object.
 */
public final class Info extends Command {

    /**
     * Constructs an Info command.
     * @param collectionManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public Info(CollectionManager collectionManager, Integer numArguments) {
        super(collectionManager, numArguments);
    }

    @Override
    public String description() {
        return "info - show metadata of the collection object";
    }

    @Override
    public Response execute(String... args) {
        try {
            String output = this.collectionManager.getInfo();
            if (!output.isEmpty()) {
                return new Response(output, RequestState.RETURNED);
            }
            return new Response(this.getClass().getSimpleName(), RequestState.DONE);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}