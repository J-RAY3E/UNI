package org.example.CommandsManager.Commands.CommnadClasses;

import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;


public abstract class CommandIn extends Command {
    
    protected InputManager inputManager;
    public CommandIn(CollectionManager storageManager, InputManager inputManager) {
        super(storageManager);
        this.inputManager = inputManager;
    }
}
