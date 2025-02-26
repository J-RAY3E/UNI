package org.example;
// DataStructures


//Tools IO 

import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Inputs.FileInputManager;
import org.example.ReaderManager.Parse.ReadJSON;
import org.example.ReaderManager.RuntimeManager;
import org.example.ReaderManager.Inputs.ConsoleInputManager;
import org.example.Storage.CollectionManager;

import java.io.FileNotFoundException;


public class Main{
    public static void main(String[] args) {

        CollectionManager collectionManager = new CollectionManager();

        if (args.length > 0) {
            try {
                new ReadJSON(new FileInputManager(args[0])).loadData(collectionManager);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + args[0]);
            }
        } else {
            System.out.println("No file loaded.");
        }
        ConsoleInputManager mainInput = new ConsoleInputManager();
        CommandsManager commandsManager = new CommandsManager(mainInput, collectionManager);
        commandsManager.loadCommands(); 
        System.out.println("Welcome to StorageManager");
        new RuntimeManager(mainInput,commandsManager).Reader();



    }

}