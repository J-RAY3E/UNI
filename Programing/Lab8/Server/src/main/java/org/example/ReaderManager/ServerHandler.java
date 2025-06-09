package org.example.ReaderManager;

import org.example.CommandsManager.Commands.Show;
import org.example.CommandsManager.CommandsManager;
import org.example.Enums.MessageType;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;
import org.example.UserUtils.UserManager;
import org.example.connection.NotificationManager;
import org.example.connection.Connection;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * Handler processes incoming client connections using blocking I/O streams.
 */
public final class ServerHandler {
    private final CommandsManager commandsManager;
    private final Connection connection;
    private final Map<Socket, UserManager> clientUserMap = new ConcurrentHashMap<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2
    );
    private final ExecutorService forkService = new ForkJoinPool(
            Runtime.getRuntime().availableProcessors()
    );

    private final boolean running = true;

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
        NotificationManager.getInstance().pushMessage("The server is ready to accept connections", MessageType.INFO);
        try {
            while(running) {
                Socket client = connection.acceptClient();
                connection.addClients(client);
                if (client != null) {
                    new Thread(() -> handleClient(client)).start();
                }
            }
        } catch (IOException e) {
            NotificationManager.getInstance().pushMessage("Error establishing connection with the client: " + e.getMessage(),MessageType.ERROR);
        }
    }

    /**
     * Handles a single client's request/response cycle.
     */
    private void handleClient(Socket client) {
        processNextRequest(client);
    }

    private void processNextRequest(Socket client) {
        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return connection.readMessage(client);
                    } catch (IOException e) {
                        throw new CompletionException(e);
                    }
                }, executorService)
                .thenApplyAsync(message -> {
                    try {
                        return Serializer.deserialize(Request.class, message);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new CompletionException(e);
                    }
                }, executorService)
                .thenApplyAsync(request -> {
                    // Guardamos el usuario que mandó esta petición
                    clientUserMap.put(client, request.getUserManager()); // este es un Map<Socket, UserManager>
                    return commandsManager.eject(request);
                }, forkService)
                .thenAccept(response -> {
                    try {
                        // Responder al cliente que envió la petición
                        RequestState updateState = null;
                        if (response.getRequestState() == RequestState.UPDATE) {
                            updateState =  response.getRequestState();
                            response.setRequestState(RequestState.DONE);
                        }
                        connection.writeMessage(Serializer.serialize(response), client);

                        // Si la operación fue UPDATE, enviamos el resultado del SHOW a todos
                        if (updateState == RequestState.UPDATE) {
                            // Recuperar el usuario autenticado de ese cliente
                            UserManager user = clientUserMap.get(client);
                            Request updateRequest = new Request("show", new String[]{}, 0, user);
                            updateRequest.setCommand(new Show(0));
                            Response updateResponse = commandsManager.eject(updateRequest);
                            updateResponse.setRequestState(RequestState.UPDATE);
                            connection.broadcastUpdate(updateResponse, client);
                        }

                        // Si la operación fue EXIT, cerramos la conexión
                        if (response.getRequestState() == RequestState.EXIT) {
                            connection.closeClientConnection(client);
                        } else {
                            processNextRequest(client);
                        }

                    } catch (IOException e) {
                        throw new CompletionException(e);
                    }
                })
                .exceptionally(ex -> {
                    NotificationManager.getInstance().pushMessage(
                            "Client disconnected: " + ex.getCause().getMessage(),
                            MessageType.WARNING
                    );
                    try {
                        connection.closeClientConnection(client);
                    } catch (IOException ignored) {}

                    return null;
                });
    }
}



