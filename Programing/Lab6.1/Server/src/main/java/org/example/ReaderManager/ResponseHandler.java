package org.example.ReaderManager;


import org.example.Enums.MessageType;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.NotificationManager;

/**
 * ResponseHandler processes the responses of the executed commands.
 * It prints the results to the console depending on the request state (success, error, etc.).
 */
public final class ResponseHandler {

    public void handle(Response response) {
        synchronized (System.out) {
            switch (response.getRequestState()) {
                case DONE:
                    System.out.printf("The operation %s was successfully completed.%n", response.getMessage());
                    break;

                case RETURNED:
                    System.out.printf("%s", response.getMessage());
                    break;

                case ERROR:
                    System.out.printf("%s %n", response.getMessage());
                    break;

                case EXIT:
                    NotificationManager.getInstance().pushMessage("The program has finished successfully", MessageType.INFO);
                    System.exit(0);
                    break;
            }
        }
    }
}
