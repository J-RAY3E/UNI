package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class RemoveLast extends CommandOut{

    public RemoveLast(CollectionManager storageManager){
        super(storageManager);
    }
    @Override
    public String description(){
        return "removeLast - remove last element saved in the storege";
    };
    @Override
    public void  execute(String  ...args){
        this.collectionManager.deleteLast();
    };
}
