package org.example.ReaderManager.Inputs;

import org.example.CommandsManager.Commands.CommnadClasses.Command;

import java.io.Serializable;

/**
 * Represents a user request containing a command and its parameters.
 */
public final class Request implements Serializable {

    private final String command;
    private final String[] parameters;
    private final int numParameters;
    private Command commandLoaded;
    /**
     * Constructs a new Request instance.
     * @param command the command name.
     * @param parameters the command parameters.
     * @param numParameters the number of parameters.
     */
    public Request(String command, String[] parameters, int numParameters) {
        this.command = command;
        this.parameters = parameters;
        this.numParameters = numParameters;
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
}
