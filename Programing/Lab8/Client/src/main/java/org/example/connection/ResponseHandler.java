package org.example.connection;

import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.GUI.ViewController;
import org.example.ReaderManager.Inputs.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ResponseHandler {
    private final BlockingQueue<Response> queue = new LinkedBlockingQueue<>();

    public void handle(Response response) {
        if (response.getRequestState() == RequestState.UPDATE) {
            NotificationManager.getInstance().pushMessage("Update gotten"+response.getMessage()+response.getRequestState().toString(), MessageType.INFO);
            ViewController.getInstance().loadData(response.getReturned());
            // Podrías también emitir un evento a la GUI
        } else {

            queue.offer(response);
        }
    }

    public Response waitForResponse() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
