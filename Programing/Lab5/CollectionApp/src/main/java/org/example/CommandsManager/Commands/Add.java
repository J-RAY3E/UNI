package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.ReaderManager.InputValidator;
import org.example.Storage.CollectionManager;


public class Add extends CommandIn{

    public Add(CollectionManager collectionManager, InputManager inputManager){
        super(collectionManager, inputManager);
    }

    @Override
    public String description(){
        return "add {element} - add new element to the colecction ";
    };
 
    @Override
    public  void execute(String  ...args){
        Worker worker = new CreationManager(new InputValidator(this.inputManager)).creationelement(this.collectionManager.getIndex(), args[0]);
        this.collectionManager.addElement(worker);
        
    };
}
