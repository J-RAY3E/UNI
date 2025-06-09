package org.example.ReaderManager.Validators;


import org.example.Classes.Worker;
import org.example.Enums.Position;
import org.example.Enums.Status;
import org.example.ReaderManager.TypeValidation.ValidationEnum;
import org.example.ReaderManager.TypeValidation.ValidationNumber;
import org.example.ReaderManager.TypeValidation.ValidationString;
import org.example.ReaderManager.TypeValidation.Validator;

/**
 * Validator for Worker objects.
 * Ensures that the worker's fields meet validation criteria.
 */
public final class ValidationWorker implements Validator<Worker> {

    private final Validator<String> nameValidator = new ValidationString(Integer.MAX_VALUE, false);
    private final ValidationNumber salaryValidator = new ValidationNumber(0.0, (double) Integer.MAX_VALUE, false);
    private final ValidationEnum<Position> positionValidator = new ValidationEnum<>(Position.class, true);
    private final ValidationEnum<Status> statusValidator = new ValidationEnum<>(Status.class, true);
    private final ValidationNumber coordXvalidation = new ValidationNumber(0.0, (double) 395, false);
    private final ValidationNumber coordYvalidation = new ValidationNumber(0.0, (double) Float.MAX_VALUE, false);

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
        Boolean org = true;
        if (worker.getOrganization() != null){

        }
        return nameValidator.validate(worker.getName()) &&
                salaryValidator.validate(worker.getSalary()) &&
                positionValidator.validate(worker.getPosition()) &&
                statusValidator.validate(worker.getStatus()) &&
                coordYvalidation.validate(worker.getCoordinates().getY()) &&
                coordXvalidation.validate(worker.getCoordinates().getX());

    }

    @Override
    public String getErrorMessage() {
        return "The worker object is invalid.";
    }
}
