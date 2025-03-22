package org.example;

import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Handler;
import org.example.ReaderManager.ResponseHandler;
import org.example.ReaderManager.RuntimeManager;
import org.example.Storage.CollectionManager;

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
