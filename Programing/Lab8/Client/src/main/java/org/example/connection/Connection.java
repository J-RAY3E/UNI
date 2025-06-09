package org.example.connection;

import org.example.Enums.MessageType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;

public class Connection {
    private Integer PORT;
    private String host;
    private SocketChannel socketChannel;

    public Connection() {}

    public Connection(String host, Integer port) {
        this.PORT = port;
        this.host = host;
    }

    public void establishConnection() {
        try {
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
            this.socketChannel.connect(new InetSocketAddress(host, PORT));
            if (this.socketChannel.finishConnect()) {
                NotificationManager.getInstance().pushMessage("The connection is available right now", MessageType.INFO);
            }
            Thread.sleep(500);
        }
        catch (IOException | UnresolvedAddressException e) {
            NotificationManager.getInstance().pushMessage("The port is not right: " + e.getMessage(),MessageType.ERROR);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void writeMessage(byte[] message) throws IOException {
        int length = message.length;
        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
        buffer.putInt(length);
        buffer.put(message);
        buffer.flip();
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        NotificationManager.getInstance().pushMessage("The request was sent, Waiting response...", MessageType.INFO);
    }

    public byte[] readMessage() throws IOException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            int read = socketChannel.read(lengthBuffer);
            if (read == -1) throw new IOException("End of stream while reading length");
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()) {
            int read = socketChannel.read(dataBuffer);
            if (read == -1) throw new IOException("End of stream while reading message");
        }
        NotificationManager.getInstance().pushMessage("Response Gotten from the server", MessageType.INFO);
        return dataBuffer.array();
    }

    public void closeConnection() throws IOException {
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
        NotificationManager.getInstance().pushMessage("The connection was close successfully", MessageType.INFO);
    }

    public boolean isConnected() {
        try {
            return socketChannel != null && socketChannel.isOpen() && socketChannel.finishConnect();
        } catch (IOException e) {
            return false;
        }
    }
}
