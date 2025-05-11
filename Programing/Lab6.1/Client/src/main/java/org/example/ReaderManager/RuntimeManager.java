package org.example.ReaderManager;

import org.example.Enums.MessageType;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.connection.Connection;
import org.example.connection.NotificationManager;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;


/**
 * RuntimeManager is responsible for running the command reading and execution process.
 * It coordinates the handler to process commands and continue the flow.
 */
public final class RuntimeManager {

    private final Handler handler;
    private Connection connection;
    /**
     * Constructor for the RuntimeManager.
     * @param handler The handler responsible for processing commands.
     */
    public RuntimeManager(Handler handler,Connection connection) {
        this.handler = handler;
        this.connection = connection;
    }

    /**
     * Starts the reader loop, continuously processing user input until the application state is false.
     */
    public void Reader() {
        try{
            NotificationManager.getInstance().pushMessage("The client program is ready!", MessageType.INFO);
            while (this.handler.getState()) {
                if (!this.connection.isConnected()) {
                    System.out.print(">> Please insert HOST PORT (e.g., localhost 9316): ");
                    String hostPort = InputManagerRegistry.getInstance().getCurrentInput().nextLine();
                    String[] parameters = hostPort.trim().split("\\s+");
                    if (parameters.length != 2) {
                        System.out.println("Invalid input format. Please provide HOST and PORT separated by a space.");
                        continue;
                    }
                    try {
                        String host = parameters[0];
                        int port = Integer.parseInt(parameters[1]);
                        this.connection = new Connection(host, port);
                        this.connection.establishConnection();
                    } catch (NumberFormatException e) {
                        NotificationManager.getInstance().pushMessage("The port printed is not a integer", MessageType.ERROR);
                    } catch (IllegalStateException e) {
                        NotificationManager.getInstance().pushMessage("No allowed values", MessageType.INFO);
                    }
                } else {

                    System.out.print(">> ");
                    this.handler.createRequest();
                    this.handler.pullRequest(this.connection);

                }
            }
        }
        catch (IOException e){
            System.err.println(e);
        }
        finally {

        if (this.connection != null && this.connection.isConnected()) {
            try {
                this.connection.closeConnection();
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }


    }
}
