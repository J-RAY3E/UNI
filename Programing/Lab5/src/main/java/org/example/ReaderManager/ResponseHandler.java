package org.example.ReaderManager;

import org.example.ReaderManager.Inputs.Response;

/**
 * ResponseHandler processes the responses of the executed commands.
 * It prints the results to the console depending on the request state (success, error, etc.).
 */
public final class ResponseHandler {

    /**
     * Handles and displays the response based on the request state.
     * @param response The response to be handled.
     */
    public void handle(Response response) {
        switch (response.getRequestState()) {
            case DONE:
                System.out.printf("The operation %s was successfully completed.%n", response.getMessage());
                break;

            case RETURNED:
                System.out.printf("%s", response.getMessage());
                break;

            case ERROR:
                System.out.printf("%s %n",response.getMessage());
                break;

            case EXIT:
                System.exit(0);
                break;
        }
    }
}
