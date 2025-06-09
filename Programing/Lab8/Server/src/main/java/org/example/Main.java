package org.example;


import org.example.CommandsManager.CommandsManager;
import org.example.CommandsManager.ConsoleCommandsManager;
import org.example.DataBaseManager.DBLoader;
import org.example.DataBaseManager.DBManager;
import org.example.DataBaseManager.DatabaseVerifier;
import org.example.UserUtils.UserManager;
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
        Connection connection = new Connection(9313);
        connection.establishConnection();
        //DBManager db = DBManager.getInstance("studs","s466103","Nr8be1ed7toqQ8sY");
        DBManager db = DBManager.getInstance("lab7","postgres","2312");
        db.establishConnection();
        DatabaseVerifier.verifySchema(db);
        DBLoader.initiate(db);

        RuntimeManager runtimeManager = getRuntimeManager(connection);

        runtimeManager.Reader();


    }

    private static RuntimeManager getRuntimeManager(Connection connection) {
        ResponseHandler consoleServerResponseHandler = new ResponseHandler();
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.load();

        CommandsManager commandsManager = new CommandsManager(collectionManager);
        ConsoleCommandsManager consoleCommandsManager = new ConsoleCommandsManager(collectionManager);
        ServerHandler serverHandler = new ServerHandler(commandsManager, connection);
        ConsoleHandler consoleHandler = new ConsoleHandler(consoleCommandsManager,consoleServerResponseHandler,new UserManager("admin","2312",true,"admin"));
        RuntimeManager runtimeManager = new RuntimeManager(serverHandler,consoleHandler);
        return runtimeManager;
    }

}
