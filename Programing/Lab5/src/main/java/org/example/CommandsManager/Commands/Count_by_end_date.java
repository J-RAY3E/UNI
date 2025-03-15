package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.InputValidator;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.StackInputs;
import org.example.ReaderManager.TypeValidation.ValidationDate;
import org.example.Storage.CollectionManager;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class Count_by_end_date extends Command {

    public Count_by_end_date (CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
    }
    
    @Override
    public String description() {
        return "count_by_end_date - count all the elements of the list with the same end_date field";
    };

    @Override
    public Response execute(String... args) {
        try {
            LocalDate date = new InputValidator(StackInputs.getCurrentInput()).execute(new ValidationDate(false), LocalDate::parse, args[0]);
            long count = this.collectionManager.getCollection().stream().filter(Worker -> Worker.getEndDateStr().equals(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()).toString())).count();
            return  new Response(String.format("Total elements in storage: %d .%n ", count), RequestState.RETURNED);
        } catch (Exception e){
            return new Response(e.getMessage() + " in command"  + this.getClass(), RequestState.ERROR);
        }
    };
}
