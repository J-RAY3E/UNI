package org.example.CommandsManager;


import java.util.HashMap;


import org.example.CommandsManager.Commands.*;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.ReaderManager.Inputs.Request;
import org.example.Storage.CollectionManager;

public class CommandsManager {

    HashMap<Integer, HashMap<String,Command>> commands = new  HashMap<>();
    InputManager inputManager;
    CollectionManager storage;

    public CommandsManager(InputManager inputManager){
        this.inputManager = inputManager;
        this.storage = new CollectionManager();
        this.loadCommands();
    }

    public CommandsManager(InputManager inputManager, CollectionManager storage){
        this.inputManager = inputManager;
        this.storage = storage;
        this.loadCommands();
    }

    public void loadCommand(String name,Integer nParameter, Command command){
        if (commands.containsKey(nParameter)){
            commands.get(nParameter).put(name, command);
        }
        else{
            HashMap<String,Command> addCommand = new HashMap<>();
            addCommand.put(name,command);
            commands.put(nParameter, addCommand);
        }
    }

    public void loadCommands(){
        this.loadCommand("info",0, new Info(this.storage));
        this.loadCommand("show",0, new Show(this.storage));
        this.loadCommand("save",0, new Save(this.storage));
        this.loadCommand("exit",0, new Exit(this.storage));
        this.loadCommand("clear",0, new Clear(this.storage));
        this.loadCommand("help",0, new Help(this.commands,this.storage));
        this.loadCommand("add",1, new Add(this.storage,this.inputManager));
        this.loadCommand("update_by_id",1, new UpdateById(this.storage,this.inputManager));
        this.loadCommand("remove_last", 0,new RemoveLast(this.storage));
        this.loadCommand("min_by_position",1, new MinByPosition(this.storage));
        this.loadCommand("count_by_end_date", 1,new Count_by_end_date(this.storage,this.inputManager));
        this.loadCommand("execute_file", 1,new ExecuteFile(this.storage,this.inputManager));
        this.loadCommand("add_if_max", 1,new AddIfMax(this.storage,this.inputManager));
        this.loadCommand("filter_starts_with_name", 1,new Filter_starts_with_name(this.storage,this.inputManager));
    }

    public void eject(Request request){
        System.out.println(this.commands.get(request.getNumParameters()).get(request.getCommand()).description());
        this.commands.get(request.getNumParameters()).get(request.getCommand()).execute(request.getParameters());
    }

    public CollectionManager getCollectionManager(){return this.storage;};
    public void setCollectionManager(CollectionManager collectionManager){this.storage = collectionManager;}
}
