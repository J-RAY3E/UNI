package org.example;


import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Handler;
import org.example.ReaderManager.ResponseHandler;
import org.example.ReaderManager.RuntimeManager;
import org.example.connection.Connection;
import org.example.connection.NotificationManager;


public class Main {
    public static void main(String[] args) {
        NotificationManager.getInstance("clientConsole");
        ResponseHandler responseHandler = new ResponseHandler();
        CommandsManager commandsManager = new CommandsManager();
        Handler handler = new Handler(commandsManager,responseHandler);
        RuntimeManager runtimeManager = new RuntimeManager(handler,new Connection());
        runtimeManager.Reader();

    }


}