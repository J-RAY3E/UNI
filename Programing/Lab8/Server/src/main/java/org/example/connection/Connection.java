package org.example.connection;

import org.example.Enums.MessageType;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Serializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Connection encapsulates server-side blocking I/O operations.
 */
public class Connection {
    private final int port;
    private ServerSocket serverSocket;
    private final List<Socket> clients = Collections.synchronizedList(new ArrayList<>());

    public Connection(int port) {
        this.port = port;
    }

    /**
     * Initializes the ServerSocket on the configured port.
     */
    public void establishConnection()  {

        try{
            this.serverSocket = new ServerSocket(port);
            this.serverSocket.setReuseAddress(true);
            this.serverSocket.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            NotificationManager.getInstance().pushMessage("The server is running in port " + port, MessageType.INFO);

        }catch(IOException e){
            NotificationManager.getInstance().pushMessage("The port " + port + " is already being used", MessageType.ERROR);
        }

    }

    /**
     * Blocks until a client connects, then returns the Socket.
     */
    public Socket acceptClient() throws IOException {
        Socket clientsocket = serverSocket.accept();
        if(clientsocket != null){
            NotificationManager.getInstance().pushMessage("A connection has been accepted", MessageType.INFO);
        }
        return clientsocket;
    }

    /**
     * Reads a length-prefixed message from the client.
     */
    public byte[] readMessage(Socket client) throws IOException {
        DataInputStream in = new DataInputStream(client.getInputStream());
        int length = in.readInt();
        if (length <= 0) {
            throw new IOException("Invalid message length: " + length);
        }
        byte[] data = new byte[length];
        in.readFully(data);
        NotificationManager.getInstance().pushMessage("The message was read", MessageType.INFO);
        return data;
    }

    /**
     * Writes a length-prefixed message back to the client.
     */
    public void writeMessage(byte[] message, Socket client) throws IOException {
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeInt(message.length);
        out.write(message);
        out.flush();
        NotificationManager.getInstance().pushMessage("A response has been sent", MessageType.INFO);
    }

    /**
     * Closes an individual client connection.
     */
    public void closeClientConnection(Socket client) throws IOException {
        client.close();
        NotificationManager.getInstance().pushMessage("The client has closed the session", MessageType.INFO);
    }

    /**
     * Closes the server socket and stops accepting clients.
     */
    public void closeConnection() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    public void broadcastUpdate(Response response, Socket excludeClient) {
        byte[] updateBytes;
        try {
            updateBytes = Serializer.serialize(response);
        } catch (IOException e) {
            NotificationManager.getInstance().pushMessage("Error serializando UPDATE: " + e.getMessage(), MessageType.ERROR);
            return;
        }

        synchronized (clients) {
            for (Socket client : clients) {
                if (!client.isClosed()) {
                    try {
                        writeMessage(updateBytes, client);
                    } catch (IOException e) {
                        NotificationManager.getInstance().pushMessage("Error enviando UPDATE a cliente: " + e.getMessage(), MessageType.WARNING);
                    }
                }
            }
        }
    }

    public void addClients(Socket client) {
        this.clients.add(client);
    }
}