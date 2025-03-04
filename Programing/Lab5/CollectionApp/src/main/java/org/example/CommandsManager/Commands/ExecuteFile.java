package org.example.CommandsManager.Commands;

import java.io.File;
import java.io.FileNotFoundException;

import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.ReaderManager.Handler;
import org.example.ReaderManager.RuntimeManager;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;


public class ExecuteFile extends CommandIn {

    public ExecuteFile(CollectionManager collectionManager, InputManager inputManager){
        super(collectionManager,inputManager);
    }
    @Override
    public String description(){
        return "execute_file file - execute instructions from the file pointed";
    }

    @Override
    public void execute(String... args){
        if(!FileInputManager.isAvailablePath(args[0])){return;};
        try {
            FileInputManager fileInputManager = new FileInputManager(args[0]);
            new RuntimeManager(new Handler(fileInputManager, this.collectionManager)).Reader();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found " + args[0]);
        }
    }
}
