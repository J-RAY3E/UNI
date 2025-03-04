package org.example.ReaderManager;

import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Inputs.ConsoleInputManager;
import org.example.ReaderManager.Inputs.InputManager;
import org.example.ReaderManager.Inputs.Request;
import org.example.Storage.CollectionManager;


import java.util.Arrays;

public class Handler {

    InputManager inputManager;
    CommandsManager commandsManager;
    StringBuffer currentQuest = new StringBuffer();
    Boolean Status = true;

    public Handler() {
        this.inputManager = new ConsoleInputManager();
        this.commandsManager = new CommandsManager(this.inputManager);
    }

    public Handler(InputManager inputManager, CollectionManager collectionManager) {
        this.inputManager = inputManager;
        this.commandsManager = new CommandsManager(this.inputManager,collectionManager);
    }


    public void createRequest () {
        this.currentQuest.setLength(0);
        System.out.print(">> ");
        this.currentQuest.append(inputManager.nextLine());

    }
    public void pullRequest() {
        if(!this.currentQuest.isEmpty()) {
            String[] parameters = this.currentQuest.toString().split(" ");
            this.commandsManager.eject(new Request(parameters[0],Arrays.copyOfRange(parameters,1,parameters.length),parameters.length-1));
        }
    }

    public void setStatus(Boolean status) {
        this.Status = status;
    }
    public boolean getState() {
        return this.Status;
    }

}
