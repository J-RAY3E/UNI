package org.example.CommandsManager.Commands;


import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.TypeValidation.ValidationDate;
import org.example.Storage.CollectionManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


/**
 * Command to count elements with a specified end date.
 */
public final class CountByEndDate extends Command {
    private static final long serialVersionUID = 1L;
    private LocalDate parameter1;
    public CountByEndDate (Integer numArguments) {
        super(numArguments);
    }

    @Override
    public String description() {
        return "count_by_end_date - count all the elements of the list with the same end_date field";
    };

    @Override
    public Response execute(CollectionManager collectionManager) {
        try {
            long counted = collectionManager.countByEndDate(this.parameter1);
            return  new Response(String.format("Total elements in storage with EndDate %s: %d .%n ",(this.parameter1 == null) ? "null":this.parameter1.toString(), counted), RequestState.RETURNED);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };

    @Override
    public void setParameters(String... args){
        parameter1 = new InputValidator(InputManagerRegistry.getInstance().getCurrentInput()).execute(new ValidationDate(false), LocalDate::parse, args[0]);
    }
}