package org.example.CommandsManager.Commands;

import org.example.CommandsManager.Commands.CommnadClasses.Command;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.InputValidator;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.Validators.ValidationDate;
import org.example.Storage.CollectionManager;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


/**
 * Command to count elements with a specified end date.
 */
public final class CountByEndDate extends Command {

    /**
     * Constructs a CountByEndDate command.
     * @param storageManager the collection manager instance.
     * @param numArguments the expected number of arguments.
     */
    public CountByEndDate(CollectionManager storageManager, Integer numArguments) {
        super(storageManager, numArguments);
    }

    @Override
    public String description() {
        return "count_by_end_date date - count elements with the same end_date field";
    }

    @Override
    public Response execute(String... args) {
        try {
            LocalDate date = new InputValidator(InputManagerRegistry.getInstance().getCurrentInput())
                    .execute(new ValidationDate(true), LocalDate::parse, args[0]);

            long count = this.collectionManager.getCollection().stream()
                    .filter(worker ->  {
                        Date endDate = worker.getEndDate();
                        return (date == null && endDate == null) ||
                                (endDate != null && endDate.toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                        .equals(date));})
                    .count();

            return new Response(String.format("Total elements in storage: %d.%n", count), RequestState.RETURNED);
        } catch (Exception e) {
            return new Response("Unexpected "+ e.getMessage() + " in command " + this.getClass().getSimpleName(), RequestState.ERROR);
        }
    }
}