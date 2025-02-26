package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;

public class Filter_starts_with_name extends CommandIn{


    public Filter_starts_with_name (CollectionManager storageManager, InputManager inputManager){
        super(storageManager,inputManager);
    }
    
    @Override
    public String description(){
        return "filter_starts_with_name name - print all the elements that contains the substring name";
    }

    @Override
    public void execute(String... args){
        this.collectionManager.filter_starts_with_name(args[0]);
    }
    
    
}
