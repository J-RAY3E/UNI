package org.example.CommandsManager.Commands;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


public class Help extends Command {

    HashMap<String,Command> commands;

    public Help(HashMap<String,Command> commands, CollectionManager storageManager,Integer numArguments){
        super(storageManager,numArguments);
        this.commands = commands;
    }

    @Override
    public String  description(){
        return  "help - list commands";
    }
    
    @Override
    public Response execute(String  ...args) {

        try {
            String output = this.commands.values().stream().map(Command::description).collect(Collectors.joining("\n"));
            return new Response(output, RequestState.RETURNED);
        } catch (Exception e) {
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };


}

