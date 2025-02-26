package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.CommandOut;
import org.example.Storage.CollectionManager;

public class RemoveById extends CommandOut {

    public RemoveById(CollectionManager collectionManager){
        super(collectionManager);
    }
    @Override
    public String description(){
        return "Exit - close program";
    };
    @Override
    public void execute(String... args){
        this.collectionManager.deleteById(Integer.parseInt(args[0]));
    };
}
