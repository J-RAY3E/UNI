package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;


public class RemoveGreater extends CommandIn{

    public RemoveGreater(CollectionManager collectionManager,InputManager inputManager){
        super(collectionManager,inputManager);
    }
    
    @Override
    public String description(){
        return "removeGreater {element} - remove all the elements greater than current one";
    };

    @Override
    public  void execute(String  ...args){
        Worker worker = new CreationManager(new InputValidator(this.inputManager)).creationelement(this.collectionManager.getIndex(), args[0]);
        this.collectionManager.removeGreater(worker);
        
    };
}
