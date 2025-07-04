package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.util.Comparator;
import java.util.stream.Collectors;


/**
 * Command to filter elements by name substring.
 */
public final class FilterStartsWithName extends Command {
    private static final long serialVersionUID = 1L;
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
    public void setParameters(String... args){
        this.parameter1 = args[0];
    }
}