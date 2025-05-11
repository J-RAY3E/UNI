package org.example.ReaderManager;

import org.example.CommandsManager.CommandsManager;
import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.NotificationManager;
import org.example.connection.Connection;

import java.io.IOException;
import java.net.Socket;

/**
 * Handler processes incoming client connections using blocking I/O streams.
 */
public final class ServerHandler {
    private final CommandsManager commandsManager;
    private final Connection connection;
    private volatile boolean running = true;

    /**
     * Constructor.
     * @param commandsManager Manager for commands.
     * @param connection      Connection instance for server-side I/O.
     */
    public ServerHandler(CommandsManager commandsManager, Connection connection) {
        this.commandsManager = commandsManager;
        this.connection = connection;
    }

    /**
     * Starts the server loop: accepts connections and delegates to handler threads.
     */
    public void run() {
        Socket client = null;
        NotificationManager.getInstance().pushMessage("The server is ready to accept connections", MessageType.INFO);
        try {
            connection.establishConnection();
            while (running) {
                client = connection.acceptClient();
                Socket finalClient = client;
                handleClient(finalClient);
            }
        } catch (IOException e) {
            NotificationManager.getInstance().pushMessage("Error establishing connection with the client: " + e.getMessage(),MessageType.ERROR);
        }
        finally {
            if(client != null){
                try{client.close();}
                catch (IOException e) {NotificationManager.getInstance().pushMessage("Error trying to interact with the client: " + e.getMessage(),MessageType.ERROR);}
            }
        }

    }

    /**
     * Handles a single client's request/response cycle.
     */
    private void handleClient(Socket client) {
        try {
            while (running && !client.isClosed()) {
                byte[] reqBytes = connection.readMessage(client);
                Request request = Serializer.deserialize(Request.class, reqBytes);
                Response response = commandsManager.eject(request);

                byte[] respBytes = Serializer.serialize(response);
                connection.writeMessage(respBytes, client);

                if (response.getRequestState() == RequestState.EXIT) {
                    commandsManager.autosave();
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            NotificationManager.getInstance().pushMessage("The client has been disconnected: " + e.getMessage(),MessageType.WARNING);
        } finally {
            try {
                connection.closeClientConnection(client);
            } catch (IOException ignored) {}
        }
    }

    /**
     * Stops the server loop.
     */
    public void stop() {
        running = false;
        try {
            connection.closeConnection();
        } catch (IOException e) {
            NotificationManager.getInstance().pushMessage("Error trying to close server: " + e.getMessage(),MessageType.ERROR);
        }
    }
}