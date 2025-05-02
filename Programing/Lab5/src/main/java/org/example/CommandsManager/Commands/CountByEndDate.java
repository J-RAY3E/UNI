package CommandsManager.Commands;

import org.example.commandsManager.commands.CommnadClasses.Command;
import org.example.enums.RequestState;
import org.example.readerManager.inputs.InputValidator;
import org.example.readerManager.inputs.InputManagerRegistry;
import org.example.readerManager.inputs.Response;
import org.example.readerManager.validators.ValidationDate;
import org.example.storage.CollectionManager;


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