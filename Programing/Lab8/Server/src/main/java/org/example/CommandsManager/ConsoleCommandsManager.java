package org.example.CommandsManager;

import org.example.CommandsManager.Commands.*;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.CommandsManager.Commands.CommnadClasses.CommandMeta;
import org.example.Enums.PrivilegeLevel;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages all available commands and their execution.
 */
public final class ConsoleCommandsManager {


    private final Map<String, CommandMeta> commands = new LinkedHashMap<>();
    private final CollectionManager collectionManager;

    /**
     * Constructs a CommandsManager with the given CollectionManager.
     * @param collectionManager The CollectionManager instance for handling data operations.
     */
    public ConsoleCommandsManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.load();
    }

    /**
     * Loads all commands into the command map.
     */
    public void load() {
        loadCommand("info", new Info(0), PrivilegeLevel.CLIENT);
        loadCommand("show", new Show(0), PrivilegeLevel.CLIENT);
        loadCommand("exit", new Exit(0), PrivilegeLevel.CLIENT);
        loadCommand("clear", new Clear(0), PrivilegeLevel.CLIENT);
        loadCommand("add", new Add(1), PrivilegeLevel.CLIENT);
        loadCommand("update_by_id", new UpdateById(1), PrivilegeLevel.CLIENT);
        loadCommand("remove_last", new RemoveLast(0), PrivilegeLevel.CLIENT);
        loadCommand("remove_greater", new RemoveGreater(1), PrivilegeLevel.CLIENT);
        loadCommand("remove_by_id", new RemoveById(1), PrivilegeLevel.CLIENT);
        loadCommand("min_by_position", new MinByPosition(0), PrivilegeLevel.CLIENT);
        loadCommand("count_by_end_date", new CountByEndDate(1), PrivilegeLevel.CLIENT);
        loadCommand("execute_file", new ExecuteFile(1), PrivilegeLevel.CLIENT);
        loadCommand("add_if_max", new AddIfMax(1), PrivilegeLevel.CLIENT);
        loadCommand("filter_starts_with_name", new FilterStartsWithName(1), PrivilegeLevel.CLIENT);
        loadCommand("help", new Help(this.commands, 0), PrivilegeLevel.CLIENT);

        loadCommand("authorization", new Authorization(2), PrivilegeLevel.SCRIPT);
        loadCommand("registration", new Registration(2), PrivilegeLevel.SCRIPT);
    }

    /**
     * Registers a command in the command map.
     * @param name The command name.
     * @param command The command instance.
     */
    public void loadCommand(String name, Command command, PrivilegeLevel privilege) {
        commands.put(name, new CommandMeta(command, privilege));
    }

    /**
     * Executes a command based on the given request.
     * @param request The request containing the command and its parameters.
     * @return The response of the command execution.
     */
    public Response eject(Request request) {
        try {
            String userPrivilegeStr = request.getUserManager().getPrivileges().toUpperCase();
            PrivilegeLevel userPrivilege = PrivilegeLevel.valueOf(userPrivilegeStr);

            if (commands.containsKey(request.getCommand())) {
                CommandMeta meta = commands.get(request.getCommand());

                if (!isPrivilegeSufficient(userPrivilege, meta.privilege)) {
                    return new Response("Insufficient privileges to execute this command.", RequestState.ERROR);
                }

                Command command = meta.command;
                if (command.getNumArgs().equals(request.getNumParameters())) {
                    command.setParameters(request.getParameters());
                    command.setUsername(request.getUserManager().getUsername());
                    return command.execute(this.collectionManager);
                } else {
                    return new Response(
                            String.format("Command %s expected %d parameters, received %s.",
                                    request.getCommand(), command.getNumArgs(), request.getNumParameters()),
                            RequestState.ERROR
                    );
                }
            } else {
                return new Response(String.format("Command %s not found.", request.getCommand()), RequestState.ERROR);
            }

        } catch (IllegalArgumentException e) {
            return new Response("Invalid user privilege.", RequestState.ERROR);
        } catch (Exception e) {
            return new Response("Use 'help' for command syntax.", RequestState.ERROR);
        }
    }

    private boolean isPrivilegeSufficient(PrivilegeLevel user, PrivilegeLevel required) {
        return user.ordinal() >= required.ordinal();
    }

}