package org.example.CommandsManager.Commands;

import java.io.FileNotFoundException;

import org.example.CommandsManager.Commands.CommnadClasses.CommandIn;
import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.RuntimeManager;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.Storage.CollectionManager;


public class ExecuteFile extends CommandIn{

    public ExecuteFile(CollectionManager storageManager, InputManager inputManager){
        super(storageManager,inputManager);
    }
    @Override
    public String description(){
        return "execute_file file - execute instructions from the file pointed";
    }

    @Override
    public void execute(String... args){
        System.out.println(args[0]);
        try {
            FileInputManager fileInputManager = new FileInputManager(args[0]);
            new RuntimeManager(fileInputManager, new CommandsManager(fileInputManager, this.collectionManager)).Reader();;
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontró el archivo " + args[0]);
        }
    }
}
