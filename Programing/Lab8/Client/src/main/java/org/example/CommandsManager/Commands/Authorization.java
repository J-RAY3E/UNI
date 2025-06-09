package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.UserUtils.PasswordEncryptation;
import org.example.ReaderManager.Inputs.InputManagerRegistry;

/**
 * Command to add a new element to the collection.
 */
public final class Authorization extends Command {
    private static final long serialVersionUID = 1L;
    public String parameter1;
    public String parameter2;
    /**
     * Constructs an Add command.
     * @param numArguments the expected number of arguments.
     */
    public Authorization( Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "authorization {element}";
    }

    @Override
    public  void setParameters(String ...args){
        this.parameter1 = args[0];
        this.parameter2 = PasswordEncryptation.encryptationPassword(args[1]);
    }
}