package org.example.connection;

import org.example.Enums.MessageType;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Serializer;

import java.io.IOException;
import java.net.Socket;

public class ServerWriter implements  Runnable{
    private final Connection connection;
    private final Socket socketClient;
    private final Response response;
    public  ServerWriter(Connection connection,Socket socketClient,Response response){
        this.socketClient = socketClient;
        this.connection = connection;
        this.response = response;
    }
     public  void run(){
            try {
                byte[] message = Serializer.serialize(this.response);
                this.connection.writeMessage(message, this.socketClient);
            }catch (IOException e){
                NotificationManager.getInstance().pushMessage("Message sent alright", MessageType.INFO);
            }
     }
}
