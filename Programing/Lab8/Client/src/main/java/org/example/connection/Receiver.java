package org.example.connection;

import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Serializer;

import java.io.IOException;

public class Receiver implements Runnable {
    private final Connection connection;
    private final ResponseHandler responseHandler;
    private volatile boolean running = true;

    public Receiver(Connection connection, ResponseHandler responseHandler) {
        this.connection = connection;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] data = connection.readMessage();
                Response response = Serializer.deserialize(Response.class, data);
                responseHandler.handle(response);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Receiver: connection closed");
                stop();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
