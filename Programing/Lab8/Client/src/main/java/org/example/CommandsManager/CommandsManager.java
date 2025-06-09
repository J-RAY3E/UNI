package org.example.CommandsManager;



import org.example.CommandsManager.Commands.*;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.CommandsManager.Commands.CommnadClasses.CommandMeta;
import org.example.Enums.PrivilegeLevel;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages all available commands and their execution.
 */
public final class CommandsManager {

    private final Map<String, CommandMeta> commands = new LinkedHashMap<>();


    /**
     * Constructs a CommandsManager with the given CollectionManager.

     */
    public CommandsManager() {

        this.load();
    }

    public void load() {
        loadCommand("info", new Info(0), PrivilegeLevel.CLIENT);
        loadCommand("show", new Show(0), PrivilegeLevel.CLIENT);
        loadCommand("exit", new Exit(0), PrivilegeLevel.CLIENT);
        loadCommand("clear", new Clear(0), PrivilegeLevel.CLIENT);
        loadCommand("add", new Add(4), PrivilegeLevel.CLIENT);
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

        loadCommand("authorization", new Authorization(2), PrivilegeLevel.CLIENT);
        loadCommand("registration", new Registration(2), PrivilegeLevel.CLIENT);
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

    public Request eject(Request request) {
        try {
            if (commands.containsKey(request.getCommand())) {
                CommandMeta meta = commands.get(request.getCommand());
                Command command = meta.command;

                command.setUsername(request.getUserManager().getUsername());

                // Validar privilegios excepto para "authorization" y "registration"
                if (!request.getCommand().equalsIgnoreCase("authorization") &&
                        !request.getCommand().equalsIgnoreCase("registration")) {

                    String privilegeStr = request.getUserManager().getPrivileges(); // puede ser null

                    if (privilegeStr == null) {

                        return new Request("You do not have permission to execute this command.", null, -1, request.getUserManager());
                    }

                    PrivilegeLevel userPrivilege;
                    try {
                        userPrivilege = PrivilegeLevel.valueOf(privilegeStr.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        return new Request("Invalid user privilege.", null, -1, request.getUserManager());
                    }

                    if (!(userPrivilege.ordinal() >= meta.privilege.ordinal())) {
                        return new Request("You do not have permission to execute this command.", null, -1, request.getUserManager());
                    }
                }

                // Verificación número de argumentos
                if (command.getNumArgs().equals(request.getNumParameters())) {
                    if (command.getNumArgs() >= 1) {
                        command.setParameters(request.getParameters());
                    }
                    request.setCommand(command);
                    return request;
                } else {
                    return new Request(
                            String.format("Command %s expected %d parameters, received %s.",
                                    request.getCommand(), command.getNumArgs(), request.getNumParameters()),
                            null, -1, request.getUserManager());
                }
            } else {
                return new Request(String.format("Command %s not found.", request.getCommand()), null, -1, request.getUserManager());
            }
        } catch (Exception e) {
            return new Request("Use 'help' for command syntax.", null, -1, request.getUserManager());
        }
    }



}
