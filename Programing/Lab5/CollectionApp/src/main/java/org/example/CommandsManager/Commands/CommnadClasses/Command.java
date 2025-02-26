package org.example.CommandsManager.Commands.CommnadClasses;

import org.example.Storage.CollectionManager;


public abstract class Command {

    protected CollectionManager collectionManager;
    

    protected Command(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    public String  description(){
        return String.format("Command storage %s", this.collectionManager.getClass());
    };

    public void execute(String... args){

    };
} 
