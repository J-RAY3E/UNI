package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


/**
 * Command to add a new element to the collection.
 */
public final class Registration extends Command {
    private static final long serialVersionUID = 1L;
    public String parameter1;
    public String parameter2;
    /**
     * Constructs an Add command.
     * @param numArguments the expected number of arguments.
     */
    public Registration( Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "registration {element}";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        if (parameter1 == null || parameter1.trim().isEmpty()) {
            return new Response("Username cannot be empty or null.", RequestState.ERROR);
        }

        if (parameter2 == null || parameter2.trim().isEmpty()) {
            return new Response("Password cannot be empty or null.", RequestState.ERROR);
        }
        try {
            boolean aut = DBManager.getInstance().registration(this.parameter1,this.parameter2,"client");
            if(aut){
                return new Response("t",RequestState.RETURNED);
            }else{
                return new Response("f",RequestState.RETURNED);
            }
        } catch (Exception e) {
            return new Response("Unexpected "+e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
    @Override
    public  void setParameters(String ...args){
        this.parameter1 = args[0];
        this.parameter2 = args[1];


    }
}