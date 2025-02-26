package org.example.CommandsManager;

import java.util.Arrays;
import java.util.HashMap;

import org.example.CommandsManager.Commands.*;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;

public class CommandsManager {

    HashMap<String,Command> commands = new  HashMap<>();
    CollectionManager storage;
    InputManager inputManager;

    public CommandsManager(InputManager inputManager, CollectionManager storage){
        this.storage = storage;
        this.inputManager = inputManager;
    }

    public void loadCommand(String name, Command command){
        commands.put(name,command);
    }

    public void loadCommands(){
        this.loadCommand("info", new Info(this.storage));
        this.loadCommand("show", new Show(this.storage));
        this.loadCommand("save", new Save(this.storage));
        this.loadCommand("exit", new Exit(this.storage));
        this.loadCommand("clear", new Clear(this.storage));
        this.loadCommand("help", new Help(this.commands,this.storage));
        this.loadCommand("add", new Add(this.storage,this.inputManager));
        this.loadCommand("update_by_id", new UpdateById(this.storage,this.inputManager));
        this.loadCommand("remove_last", new RemoveLast(this.storage));
        this.loadCommand("min_by_position", new MinByPosition(this.storage));
        this.loadCommand("count_by_end_date", new Count_by_end_date(this.storage,this.inputManager));
        this.loadCommand("execute_file", new ExecuteFile(this.storage,this.inputManager));
        this.loadCommand("add_if_max", new AddIfMax(this.storage,this.inputManager));
        this.loadCommand("filter_starts_with_name", new Filter_starts_with_name(this.storage,this.inputManager));
    }

    public void ejectCommand(String parameters){
        String[] paramSplit = parameters.split(" ",2);
        try{
            if(paramSplit.length> 1){
                commands.get(paramSplit[0]).execute(Arrays.copyOfRange(paramSplit,1,paramSplit.length));
            }else{
                commands.get(paramSplit[0]).execute();
            }
        }catch(Exception e){
            System.out.println("Check the syntax using command help");
        }

    }
}
