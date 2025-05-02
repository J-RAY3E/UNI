package org.example.ReaderManager;



import org.example.CommandsManager.CommandsManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.Checker;
import org.example.connection.Connection;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

/**
 * Handler processes user input, executes commands, and handles responses.
 * It is responsible for creating requests, pulling requests, and managing the flow of the program.
 */
public final class ServerHandler {

    private final CommandsManager commandsManager;
    private final Connection connection;
    private Boolean status = true;

    /**
     * Constructor for the Handler class.
     * @param commandsManager The CommandsManager instance to handle commands.

     */
    public ServerHandler(CommandsManager commandsManager , Connection connection) {
        this.commandsManager = commandsManager;
        this.connection = connection;

    }

    /**
     * Creates a new request by reading user input.
     */
    public void run() {
        Checker.getInstance().getLogger().info("Esta disponible a escuchar clientes");
        while (this.getState()){
            Response response = null;
            try {
                Iterator<SelectionKey> selectionKeys = this.connection.getConnections();
                while (selectionKeys.hasNext()) {

                    SelectionKey selectionKey = selectionKeys.next();
                    selectionKeys.remove();
                    if (selectionKey.isAcceptable()) {

                        connection.acceptClient(selectionKey);
                    }
                    if (selectionKey.isReadable()) {
                        System.out.println("se esta leyendo");
                        byte[] message = connection.readMessage(selectionKey);
                        Request request = Serializer.deserialize(Request.class,message);
                        response = this.commandsManager.eject(request);
                        }
                    if (response != null) {
                        System.out.println("SE ESTA ESCRIBIENDO");
                        connection.writeMessage(Serializer.serialize(response), selectionKey);
                        if (response.getRequestState() == RequestState.EXIT) {
                            this.connection.CloseClientConnection(selectionKey);
                            this.commandsManager.autosave();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    /**
     * Sets the state of the handler.
     * @param status The new status to be set.
     */
    public void setState(Boolean status) {
        this.status = status;
    }

    /**
     * Gets the current state of the handler.
     * @return The current status.
     */
    public boolean getState() {
        return this.status;
    }
}
