package org.example.ReaderManager;

/**
 * RuntimeManager is responsible for running the command reading and execution process.
 * It coordinates the handler to process commands and continue the flow.
 */
public final class RuntimeManager {

    private final Handler handler;

    /**
     * Constructor for the RuntimeManager.
     * @param handler The handler responsible for processing commands.
     */
    public RuntimeManager(Handler handler) {
        this.handler = handler;
    }

    /**
     * Starts the reader loop, continuously processing user input until the application state is false.
     */
    public void Reader() {
        while (this.handler.getState()) {
            System.out.print(">> ");
            this.handler.createRequest();
            this.handler.pullRequest();
        }
    }
}
