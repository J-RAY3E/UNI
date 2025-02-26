package org.example.CommandsManager.Commands;

import java.util.HashMap;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class Help extends CommandOut {

    HashMap<String,Command> commands;

    public Help(HashMap<String,Command> commands, CollectionManager storageManager){
        super(storageManager);
        this.commands = commands;
    }

    @Override
    public String  description(){
        return  "help - list commands";
    }
    
    @Override
    public void execute(String  ...args){
        for(Command command:  commands.values()){
            System.out.println(command.description());
        }

    }
    
}
