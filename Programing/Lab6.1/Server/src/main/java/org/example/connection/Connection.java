package org.example.connection;




import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.ConsoleHandler;


public class Connection {
    private final int PORT;
    private final String host;
    private Selector  selector;
    private final Checker serverCheker;

    public  Connection(String host,int port){
        this.PORT = port;
        this.host = host;
        this.serverCheker = Checker.getInstance(host);
    }

    public void establishConnection(){
        try{
            ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
            this.selector = Selector.open();
            serverSocketChannel.bind(new InetSocketAddress(this.PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverCheker.getLogger().info(String.format("\u001B[32m" +"The server has been started in port:  %s"+ "\u001B[0m",this.PORT));
        }catch (IOException e){
            serverCheker.getLogger().info(String.format("Its not possible to start the server in the port: %s",this.PORT));
        }
    }

    public Iterator<SelectionKey> getConnections() throws IOException {

        selector.select();
        return this.selector.selectedKeys().iterator() ;
    }


    public void writeMessage(byte[] message,SelectionKey  selectionKey) throws IOException {
        SocketChannel client = (SocketChannel)  selectionKey.channel();
        int length = message.length;
        client.write(ByteBuffer.wrap(new byte[]{
                    (byte) (length >> 24),
                    (byte) (length >> 16),
                    (byte) (length >> 8),
                    (byte) (length)
        }));
        client.write(ByteBuffer.wrap(message));
        client.register(this.selector, SelectionKey.OP_READ);
    }

    public void acceptClient(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(this.selector,SelectionKey.OP_READ);

    }
    public byte[] readMessage(SelectionKey selectionKey) throws IOException {
        SocketChannel  client  =  (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int bytesRead  = client.read(buffer);

        if(bytesRead <= -1){
            throw new IOException("The message its empty");
        }
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        return data;
    }

    public void CloseConnection() throws IOException {
        if (this.selector != null && this.selector.isOpen()) {
            for (SelectionKey key : this.selector.keys()) {
                if (key.channel() instanceof ServerSocketChannel) {
                    ((ServerSocketChannel) key.channel()).close();
                } else if (key.channel() instanceof SocketChannel) {
                    ((SocketChannel) key.channel()).close();
                }
            }
            this.selector.close();
        }
    }

    public void CloseClientConnection(SelectionKey selectionKey) {
        try ( SocketChannel clientChannel = (SocketChannel) selectionKey.channel()){
            clientChannel.close();
        }
        catch (IOException e) {

        }
        finally {
            selectionKey.cancel();
        }

    }
}
