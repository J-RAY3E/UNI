package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class Info extends CommandOut{

    public Info(CollectionManager collectionManager){
        super(collectionManager);
    }

    @Override
    public String  description(){
        return "info - show metada of the colection object";
    };

    @Override
    public void execute(String  ...args){
        this.collectionManager.getInfo();
    };
    
}
