package org.example.CommandsManager.Commands;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


/**
 * Command to list all available commands.
 */
public final class Help extends Command {

    private final HashMap<String, Command> commands;

    /**
     * Constructs a Help command.
     * @param commands the map of available commands.
     * @param storageManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public Help(HashMap<String, Command> commands, CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
        this.commands = commands;
    }

    @Override
    public String description() {
        return "help - list commands";
    }

    @Override
    public Response execute(String... args) {
        try {
            String output = this.commands.values().stream()
                    .map(Command::description)
                    .collect(Collectors.joining("\n"));
            return new Response(output.concat("\n"), RequestState.RETURNED);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}