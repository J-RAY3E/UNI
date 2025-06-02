package org.example.ReaderManager.Inputs;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.UserUtils.UserManager;

import java.io.Serializable;

/**
 * Represents a user request containing a command and its parameters.
 */
public final class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String command;
    private final String[] parameters;
    private final int numParameters;
    private Command commandLoaded;
    private UserManager userManager;
    /**
     * Constructs a new Request instance.
     * @param command the command name.
     * @param parameters the command parameters.
     * @param numParameters the number of parameters.
     */
    public Request(String command, String[] parameters, int numParameters,UserManager userManager) {
        this.command = command;
        this.parameters = parameters;
        this.numParameters = numParameters;
        this.userManager = userManager;
    }

    /**
     * Gets the command name.
     * @return the command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the parameters.
     * @return an array of parameters.
     */
    public String[] getParameters() {
        return parameters;
    }

    /**
     * Gets the number of parameters.
     * @return the number of parameters.
     */
    public int getNumParameters() {
        return numParameters;
    }

    public void setCommand(Command command){
        this.commandLoaded = command;
    };

    public Command getCommandLoaded(){
        return commandLoaded;
    };

    public UserManager getUserManager() {
        return this.userManager;
    }
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

}
