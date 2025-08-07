package org.example.CommandsManager.Commands;


import org.example.Classes.Worker;
import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.DataBaseManager.DBLoader;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.Response;
import org.example.Storage.CollectionManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Command to remove an element by its ID.
 */
public final class RemoveById extends Command {
    private static final long serialVersionUID = 1L;
    private Integer parameter1;
    /**
     * Constructs a RemoveById command.
     * @param numArguments the expected number of arguments.
     */
    public RemoveById(Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "remove_by_id - remove an element by the given ID";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            Boolean removed = collectionManager.removebyId(this.getUsername(),this.parameter1);
            if(removed == null){
                return new Response(String.format("The element %d was not inserted by the user %s %n",this.parameter1,this.getUsername()), RequestState.ERROR);
            }else if (removed){
                return new Response(String.format("The element %d has been removed %n",this.parameter1), RequestState.UPDATE);
            }
            else{
                return new Response("No element has been removed %n" , RequestState.DONE);
            }
        } catch (NumberFormatException e) {
            return new Response("ID should be an integer value %n", RequestState.ERROR);
        } catch (SQLException e) {
            return new Response("There is no id likely registered in the data base %n", RequestState.ERROR);
        }
    }

    @Override
    public void setParameters(String... args){
        try{
            this.parameter1 = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            throw  new NumberFormatException("The string given its not possible to be parsed");
        }
    }
}
