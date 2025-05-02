package org.example;


import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Handler;
import org.example.ReaderManager.ResponseHandler;
import org.example.ReaderManager.RuntimeManager;
import org.example.connection.Connection;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Connection connection = new Connection("localhost",9316);
        connection.establishConnection();
        ResponseHandler responseHandler = new ResponseHandler();
        CommandsManager commandsManager = new CommandsManager();
        Handler handler = new Handler(commandsManager,responseHandler,connection);
        RuntimeManager runtimeManager = new RuntimeManager(handler);
        runtimeManager.Reader();

    }


}