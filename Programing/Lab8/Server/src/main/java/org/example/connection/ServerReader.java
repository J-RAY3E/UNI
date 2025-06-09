    package org.example.connection;

    import org.example.Enums.MessageType;

    import java.io.IOException;
    import java.net.Socket;
    import java.util.concurrent.Callable;

    public class ServerReader implements Callable<byte[]> {
        private final Socket socketClient;
        private final Connection connection;
        public  ServerReader(Socket socket , Connection connection){
            this.socketClient = socket;
            this.connection = connection;
        }

        @Override
        public  byte[] call(){
            try{
                byte[] Bytebuffer = this.connection.readMessage(socketClient);

                if(Bytebuffer.length >  1 ){

                    return Bytebuffer;
                }else{
                    NotificationManager.getInstance().pushMessage(String.format("The message sent by %s its empty",this.socketClient.getLocalAddress()), MessageType.WARNING);
                }
            } catch (IOException e) {
                NotificationManager.getInstance().pushMessage(e.getMessage(),MessageType.ERROR);
            }

            return null;
        }
    }
