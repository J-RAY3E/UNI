package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class Exit extends CommandOut{

    public Exit(CollectionManager storageManager) {
        super(storageManager);
    }
    @Override
    public String description(){
        return "exit - close program";
    };
    @Override
    public void  execute(String  ...args){
        System.exit(0);
    };
}
