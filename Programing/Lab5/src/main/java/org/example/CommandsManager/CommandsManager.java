package org.example.CommandsManager;


import java.util.HashMap;

import org.example.CommandsManager.Commands.*;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

public class CommandsManager {

    HashMap<String,Command> commands = new  HashMap<>();
    CollectionManager storage;


    public CommandsManager(){
        this.storage = new CollectionManager();
        this.storage.load();


        this.loadCommand("info", new Info(this.storage,0));
        this.loadCommand("show", new Show(this.storage,0));
        this.loadCommand("save", new Save(this.storage,0));
        this.loadCommand("exit", new Exit(this.storage,0));
        this.loadCommand("clear", new Clear(this.storage,0));
        this.loadCommand("help", new Help(this.commands,this.storage,0));
        this.loadCommand("add", new Add(this.storage,1));
        this.loadCommand("update_by_id", new UpdateById(this.storage,1));
        this.loadCommand("remove_last", new RemoveLast(this.storage,1));
        this.loadCommand("remove_greater", new RemoveGreater(this.storage,1));
        this.loadCommand("min_by_position", new MinByPosition(this.storage,1));
        this.loadCommand("count_by_end_date", new Count_by_end_date(this.storage,1));
        this.loadCommand("execute_file", new ExecuteFile(this.storage,1));
        this.loadCommand("add_if_max", new AddIfMax(this.storage,1));
        this.loadCommand("filter_starts_with_name", new Filter_starts_with_name(this.storage,1));

    }

    public void loadCommand(String name, Command command){
        commands.put(name, command);
    }



    public Response eject(Request request){
        try{
            if(this.commands.containsKey(request.getCommand()) ){
                if(this.commands.get(request.getCommand()).getNumArgs().equals(request.getNumParameters())){
                    return this.commands.get(request.getCommand()).execute(request.getParameters());
                }
                else{
                    return new Response(String.format("Command %s expected %d parameters, were received %s ",request.getCommand(),this.commands.get(request.getCommand()).getNumArgs(), request.getNumParameters()), RequestState.ERROR);
                }
            } else{
                return new Response(String.format(("Command %s not founded"), request.getCommand()), RequestState.ERROR);
            }

        }
        catch(Exception e){
            return new Response(("Use help for command's syntax"), RequestState.ERROR);
        }
    }

}
