package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;


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
    public Response execute(CollectionManager collectionManager) {
        try {
            boolean aut = DBManager.getInstance().authorization(this.parameter1,this.parameter2);
            if(aut){
                String privileges =  DBManager.getInstance().getPrivileges(this.parameter1,this.parameter2);
                return new Response(String.format("%s,%s,%s",this.parameter1,this.parameter2,privileges),RequestState.CONNECTED);
            }else{
                return new Response("The user or password are wrong \n",RequestState.RETURNED);
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