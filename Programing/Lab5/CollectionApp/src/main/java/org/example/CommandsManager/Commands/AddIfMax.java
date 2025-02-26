package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.ReaderManager.InputValidator;
import org.example.Storage.CollectionManager;


public class AddIfMax extends CommandIn{
    
    public AddIfMax(CollectionManager storageManager, InputManager inputManager){
        super(storageManager,inputManager);
    }

    @Override
    public String description(){
        return "add_if_max {element} - add current element if its higher than the storage ones";
    }
    @Override
    public void execute(String... args){
        Worker worker =  new CreationManager(new InputValidator(this.inputManager)).creationelement(this.collectionManager.getIndex(),args[0]);
        this.collectionManager.add_if_max(worker);
    }

}
