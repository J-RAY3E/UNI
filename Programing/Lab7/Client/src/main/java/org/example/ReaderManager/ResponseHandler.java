package org.example.ReaderManager;


import org.example.Enums.MessageType;
import org.example.ReaderManager.Inputs.Response;
import org.example.connection.NotificationManager;

/**
 * ResponseHandler processes the responses of the executed commands.
 * It prints the results to the console depending on the request state (success, error, etc.).
 */
public final class ResponseHandler {

    private  Handler consoleHandler;
    public void handle(Response response) {
        synchronized (System.out) {
            switch (response.getRequestState()) {
                case DONE:
                    System.out.printf("The operation %s was successfully completed.%n", response.getMessage());
                    break;

                case RETURNED:
                    System.out.printf("%s", response.getMessage());
                    break;
                case CONNECTED:
                    NotificationManager.getInstance().pushMessage("Login successfully, Welcome!", MessageType.INFO);
                    this.consoleHandler.setConnection(response.getMessage().split(",",3)[0],response.getMessage().split(",",3)[1],response.getMessage().split(",",3)[2],true);
                    break;
                case ERROR:
                    System.out.printf("%s %n", response.getMessage());
                    break;
                case EXIT:
                    NotificationManager.getInstance().pushMessage("The program has finished successfully", MessageType.INFO);
                    this.consoleHandler.finish();
                    System.out.println(this.consoleHandler.getState());
                    break;
                case DISCONNECT:
                    NotificationManager.getInstance().pushMessage("The user has been disconnected successfully", MessageType.INFO);
                    this.consoleHandler.disconnect();
                    break;
            }
        }
    }

    public void setHandler(Handler consoleHandler){
        this.consoleHandler = consoleHandler;
    }
}
