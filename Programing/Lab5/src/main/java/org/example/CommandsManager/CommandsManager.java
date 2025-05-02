package CommandsManager;

import org.example.commandsManager.commands.*;
import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.Request;
import org.example.readerManager.inputs.Response;
import org.example.storage.CollectionManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages all available commands and their execution.
 */
public final class CommandsManager {

    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final CollectionManager collectionManager;

    /**
     * Constructs a CommandsManager with the given CollectionManager.
     * @param collectionManager The CollectionManager instance for handling data operations.
     */
    public CommandsManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.load();
    }

    /**
     * Loads all commands into the command map.
     */
    private void load() {
        this.loadCommand("info", new Info(this.collectionManager, 0));
        this.loadCommand("show", new Show(this.collectionManager, 0));
        this.loadCommand("save", new Save(this.collectionManager, 0));
        this.loadCommand("exit", new Exit(this.collectionManager, 0));
        this.loadCommand("clear", new Clear(this.collectionManager, 0));
        this.loadCommand("help", new Help((HashMap<String, Command>) this.commands, this.collectionManager, 0));
        this.loadCommand("add", new Add(this.collectionManager, 1));
        this.loadCommand("update_by_id", new UpdateById(this.collectionManager, 1));
        this.loadCommand("remove_last", new RemoveLast(this.collectionManager, 0));
        this.loadCommand("remove_greater", new RemoveGreater(this.collectionManager, 1));
        this.loadCommand("remove_by_id", new RemoveById(this.collectionManager, 1));
        this.loadCommand("min_by_position", new MinByPosition(this.collectionManager, 0));
        this.loadCommand("count_by_end_date", new CountByEndDate(this.collectionManager, 1));
        this.loadCommand("execute_file", new ExecuteFile(this.collectionManager, 1));
        this.loadCommand("add_if_max", new AddIfMax(this.collectionManager, 1));
        this.loadCommand("filter_starts_with_name", new FilterStartsWithName(this.collectionManager, 1));
    }

    /**
     * Registers a command in the command map.
     * @param name The command name.
     * @param command The command instance.
     */
    public void loadCommand(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Executes a command based on the given request.
     * @param request The request containing the command and its parameters.
     * @return The response of the command execution.
     */
    public Response eject(Request request) {
        try {
            if (commands.containsKey(request.getCommand())) {
                Command command = commands.get(request.getCommand());
                if (command.getNumArgs().equals(request.getNumParameters())) {
                    return command.execute(request.getParameters());
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
        } catch (Exception e) {
            return new Response("Use 'help' for command syntax.", RequestState.ERROR);
        }
    }
}
