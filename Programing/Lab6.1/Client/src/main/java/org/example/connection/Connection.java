package org.example.connection;



import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;



public class Connection {
    private final int PORT;
    private final String host;
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public  Connection(String host,int port){
        this.PORT = port;
        this.host = host;
    }

    public void establishConnection() throws IOException {
        this.socket = new Socket(host, this.PORT);
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
    }

    public void writeMessage(byte[] message) throws IOException {
        this.output.write(message);
    }

    public byte[] readMessage() throws IOException {
        byte[] lengthBytes = new byte[4];
        if (this.input.read(lengthBytes) != 4) {
            throw new IOException("Failed to read message length");
        }

        int lengthMessage = ((lengthBytes[0] & 0xFF) << 24) |
                ((lengthBytes[1] & 0xFF) << 16) |
                ((lengthBytes[2] & 0xFF) << 8) |
                (lengthBytes[3] & 0xFF);

        byte[] byteBuffer = new byte[lengthMessage];
        int bytesRead = 0;
        while (bytesRead < lengthMessage) {
            int read = this.input.read(byteBuffer, bytesRead, lengthMessage - bytesRead);
            if (read == -1) {
                throw new IOException("Unexpected end of stream");
            }
            bytesRead += read;
        }
        return byteBuffer;
    }


    public void CloseConnection() throws IOException {
        if(this.socket != null) this.socket.close();
        if(this.input != null) this.input.close();
        if(this.output != null) this.output.close();
    }
}
