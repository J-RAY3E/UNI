package ReaderManager.Validators;

import org.example.classes.Worker;
import org.example.enums.Position;
import org.example.enums.Status;


/**
 * Validator for Worker objects.
 * Ensures that the worker's fields meet validation criteria.
 */
public final class ValidationWorker implements Validator<Worker> {

    private final Validator<String> nameValidator = new ValidationString(Integer.MAX_VALUE, false);
    private final ValidationNumber salaryValidator = new ValidationNumber(0.0, (double) Integer.MAX_VALUE, false);
    private final ValidationEnum<Position> positionValidator = new ValidationEnum<>(Position.class, true);
    private final ValidationEnum<Status> statusValidator = new ValidationEnum<>(Status.class, true);
    private final boolean isNull;

    /**
     * Constructor specifying if null values are allowed.
     *
     * @param isNull Determines if null Worker objects are allowed.
     */
    public ValidationWorker(boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public boolean validate(Worker worker) {
        if (worker == null) return isNull;

        return nameValidator.validate(worker.getName()) &&
                salaryValidator.validate(worker.getSalary()) &&
                positionValidator.validate(worker.getPosition()) &&
                statusValidator.validate(worker.getStatus());
    }

    @Override
    public String getErrorMessage() {
        return "The worker object is invalid.";
    }
}
