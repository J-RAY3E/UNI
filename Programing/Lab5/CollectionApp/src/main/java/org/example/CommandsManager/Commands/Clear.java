package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class Clear extends CommandOut{

    public Clear(CollectionManager storageManager){
        super(storageManager);
    }
    @Override
    public String  description(){
        return "clear - clear the all the storage";
    };

    @Override
    public void execute(String  ...args){
        this.collectionManager.clear();
    };
    
}
