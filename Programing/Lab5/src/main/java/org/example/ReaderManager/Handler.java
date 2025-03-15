package org.example.ReaderManager;

import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Inputs.ConsoleInputManager;
import org.example.ReaderManager.Inputs.Request;
import org.example.ReaderManager.Inputs.Response;


import java.util.Arrays;

public class Handler {

    private final CommandsManager commandsManager;
    private StringBuffer currentRequest;
    public Boolean Status = true;

    public Handler() {
        this.commandsManager = new CommandsManager();
        StackInputs.initialize();
        StackInputs.addReader(new ConsoleInputManager());
    }


    public void createRequest () {
            this.currentRequest = new StringBuffer();
            System.out.print(">> ");
            if (StackInputs.getCurrentInput() == null) {
                System.out.println("Error: No input source available.");
                setState(false);
                return;
            }
            if(!StackInputs.getCurrentInput().hasNextLine()) {StackInputs.removeReader();}
            this.currentRequest.append(StackInputs.getCurrentInput().nextLine());
    }
    public void pullRequest() {
        if(!this.currentRequest.isEmpty()) {
            String[] parameters = this.currentRequest.toString().split(" ");
            Response response = this.commandsManager.eject(new Request(parameters[0],Arrays.copyOfRange(parameters,1,parameters.length),parameters.length-1));
            printResponse(response);
        }
    }

    public void setState(Boolean status) {
        this.Status = status;
    }
    public boolean getState() {
        return this.Status;
    }


    private void printResponse(Response response){
        switch(response.getRequestState()){
            case DONE:
                System.out.printf("The operation %s was successfully complete %n",response.getMessage());
                break;
            case RETURNED:
                System.out.printf("%s %n",response.getMessage());
                break;
            case ERROR:
                System.out.printf("Error: %s %n", response.getMessage());
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }
}
