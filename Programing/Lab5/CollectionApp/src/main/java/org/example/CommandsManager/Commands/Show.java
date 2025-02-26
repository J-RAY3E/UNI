package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class Show extends CommandOut{

    public Show(CollectionManager storageManager){
        super(storageManager);
    }

    @Override
    public String  description(){
        return "show - show all the elements information from the object collection";
    };

    @Override
    public void execute(String  ...args){
        this.collectionManager.show();
    };
    
}
