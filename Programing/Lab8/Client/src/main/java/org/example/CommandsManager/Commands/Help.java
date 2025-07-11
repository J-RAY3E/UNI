package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.CommandsManager.Commands.CommnadClasses.CommandMeta;
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
    private static final long serialVersionUID = 1L;
    private final String listCommands;

    /**
     * Constructs a Help command.
     * @param commands the map of available commands.
     * @param numArguments the expected number of arguments.
     */
    public Help(Map<String, CommandMeta> commands, Integer numArguments) {
        super(numArguments);
        this.listCommands = commands.entrySet().stream()
                .map(e -> String.format("%s - %s", e.getKey(), e.getValue().command.description()))
                .collect(Collectors.joining("\n"));
    }


    @Override
    public String description() {
        return "help - list commands";
    }

}