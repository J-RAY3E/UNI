package org.example.readerManager.inputs;

/**
 * Represents a user request containing a command and its parameters.
 */
public final class Request {

    private final String command;
    private final String[] parameters;
    private final int numParameters;

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
}
