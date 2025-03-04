package org.example.ReaderManager.Inputs;

public class Request {
    String command;
    String[] parameters;
    Integer numParameters;

    public Request(String command, String[] parameters, Integer numParameters) {
        this.command = command;
        this.parameters = parameters;
        this.numParameters = numParameters;
    }

    public String getCommand() {return command;}
    public String[] getParameters() {return parameters;}
    public Integer getNumParameters() {return numParameters;}
}
