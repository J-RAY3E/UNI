package org.example.ReaderManager;


import org.example.CommandsManager.CommandsManager;
import org.example.ReaderManager.Inputs.InputManager;



public class RuntimeManager {

    CommandsManager commandsManager;
    InputManager inputManager;

    public RuntimeManager(InputManager inputManager, CommandsManager commandsManager){
        this.inputManager = inputManager;
        this.commandsManager = commandsManager;

    }
    public void Reader(){
        commandsManager.loadCommands();
        while (true) {
            System.out.print(">> ");
            String command = this.inputManager.nextLine();
            try{
                commandsManager.ejectCommand(command);
            }
            catch(Exception e){
                System.out.printf("the command %s does not exist", command);
                break;
            }
        }
    }
}
