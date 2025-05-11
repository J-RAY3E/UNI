package org.example.ReaderManager;


/**
 * RuntimeManager is responsible for running the command reading and execution process.
 * It coordinates the handler to process commands and continue the flow.
 */
public final class RuntimeManager {

    private final ServerHandler clientHandler;
    private final ConsoleHandler consoleHandler;

    /**
     * Constructor for the RuntimeManager.
     * @param clientHandler The handler responsible for processing commands.
     * @param consoleHandler The handler responsible for processing commands.
     */
    public RuntimeManager(ServerHandler clientHandler, ConsoleHandler consoleHandler) {
        this.clientHandler = clientHandler;
        this.consoleHandler = consoleHandler;
    }

    /**
     * Starts the reader loop, continuously processing user input until the application state is false.
     */
    public void Reader() {

        if(this.clientHandler != null) {
            Runnable clientRunnable = this.clientHandler::run;
            Thread clientPart = new Thread(clientRunnable);
            clientPart.start();
        }

        Runnable consoleRunnable = () -> {
            while (this.consoleHandler.getState()){
                System.out.print(">> ");
                this.consoleHandler.createRequest();
                this.consoleHandler.pullRequest();
            }
        };
        Thread consolePart = new Thread(consoleRunnable);
        consolePart.start();


    }
}
