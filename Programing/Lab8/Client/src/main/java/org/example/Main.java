package org.example;


import javafx.application.Application;
import org.example.CommandsManager.CommandsManager;
import org.example.GUI.ClientApp;
import org.example.GUI.ViewController;
import org.example.UserUtils.UserManager;
import org.example.ReaderManager.Handler;
import org.example.ReaderManager.ResponsePrint;
import org.example.connection.Connection;
import org.example.connection.NotificationManager;
import org.example.connection.Receiver;
import org.example.connection.ResponseHandler;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        NotificationManager.getInstance("clientConsole");
        ResponsePrint responsePrint = new ResponsePrint();
        CommandsManager commandsManager = new CommandsManager();
        ResponseHandler responseHandler = new ResponseHandler();
        Handler handler = new Handler(commandsManager,responsePrint,responseHandler, new UserManager());
        Connection connection = new Connection("localhost",9313);
        connection.establishConnection();

        Receiver receiver = new Receiver(connection, responseHandler);
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();

        ViewController.initiate(handler,connection);
        ClientApp.setController(ViewController.getInstance());
        Application.launch(ClientApp.class, args);

    }


}