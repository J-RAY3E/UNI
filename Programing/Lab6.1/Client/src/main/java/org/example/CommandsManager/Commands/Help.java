package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * Command to list all available commands.
 */
public final class Help extends Command {

    private final String listCommands;

    /**
     * Constructs a Help command.
     * @param commands the map of available commands.
     * @param numArguments the expected number of arguments.
     */
    public Help(Map<String, Command> commands, Integer numArguments) {
        super(numArguments);
        this.listCommands = commands.values().stream()
                .map(Command::description)
                .collect(Collectors.joining("\n"));;
    }

    @Override
    public String description() {
        return "help - list commands";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            return new Response(listCommands.concat("\n"), RequestState.RETURNED);
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}