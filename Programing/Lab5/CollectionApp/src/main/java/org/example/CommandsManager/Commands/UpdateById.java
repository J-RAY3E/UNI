package org.example.CommandsManager.Commands;

import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.CreationManager;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;

public class UpdateById extends CommandIn{

    public UpdateById(CollectionManager collectionManager, InputManager inputManager){
        super(collectionManager,inputManager);
    }
    @Override
    public String description(){
        return "updateById id - update element by given id";
    };
    @Override
    public void execute(String... args){
        Worker worker = new CreationManager(new InputValidator(this.inputManager)).creationelement(Integer.parseInt(args[0]));
        this.collectionManager.updateElement(Integer.parseInt(args[0]),worker);
    };
}
