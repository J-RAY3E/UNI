package org.example;

import org.example.commandsManager.CommandsManager;
import org.example.readerManager.Handler;
import org.example.readerManager.ResponseHandler;
import org.example.readerManager.RuntimeManager;
import org.example.storage.CollectionManager;

/**
 * Main class that initializes the program by setting up all the necessary managers
 * and starts the command processing loop.
 */
public final class Main {

    /**
     * Main method that initializes and runs the application.
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        System.out.println(System.getenv("data"));

        CollectionManager collectionManager = new CollectionManager();
        collectionManager.load();

        CommandsManager commandsManager = new CommandsManager(collectionManager);
        ResponseHandler responseHandler = new ResponseHandler();

        System.out.println("Welcome to StorageManager");
        new RuntimeManager(new Handler(commandsManager, responseHandler)).Reader();
    }
}
