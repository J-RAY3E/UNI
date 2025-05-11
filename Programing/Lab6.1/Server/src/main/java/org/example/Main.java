package org.example;


import org.example.CommandsManager.CommandsManager;
import org.example.CommandsManager.ConsoleCommandsManager;
import org.example.ReaderManager.ConsoleHandler;
import org.example.ReaderManager.ServerHandler;
import org.example.ReaderManager.ResponseHandler;
import org.example.ReaderManager.RuntimeManager;
import org.example.Storage.CollectionManager;
import org.example.connection.NotificationManager;
import org.example.connection.Connection;

public class Main {
    public static void main(String[] args){
        NotificationManager.getInstance("Server");
        Connection connection = new Connection(9316);
        ResponseHandler consoleServerResponseHandler = new ResponseHandler();
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.load();

        CommandsManager commandsManager = new CommandsManager(collectionManager);
        ConsoleCommandsManager consoleCommandsManager = new ConsoleCommandsManager(collectionManager);
        ServerHandler serverHandler = new ServerHandler(commandsManager,connection);
        ConsoleHandler consoleHandler = new ConsoleHandler(consoleCommandsManager,consoleServerResponseHandler);
        RuntimeManager runtimeManager = new RuntimeManager(serverHandler,consoleHandler);

        runtimeManager.Reader();


    }

}
