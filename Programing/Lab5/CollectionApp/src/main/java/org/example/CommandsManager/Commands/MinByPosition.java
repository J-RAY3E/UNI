package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;


public class MinByPosition extends CommandOut{

    public MinByPosition(CollectionManager collectionManager){
        super(collectionManager);
    }
    @Override
    public String description(){
        return "min_by_position  - returns the element with the minimum position";
    };
    @Override
    public void  execute(String... args){
        this.collectionManager.min_by_position();
    };
}
