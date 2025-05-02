package ReaderManager.TypeValidation;

import org.example.Classes.Worker;
import org.example.Enums.Position;
import org.example.Enums.Status;

public class ValidationWorker implements Validator<Worker>{

    private final Validator<String> nameValidator = new ValidationString(Integer.MAX_VALUE,false);
    private final ValidationNumber salaryValidator = new ValidationNumber(0.d, (double) Integer.MAX_VALUE, false);
    private final ValidationEnum<Position> positionValidator = new ValidationEnum<>(Position.class, true);
    private final ValidationEnum<Status> statusValidator = new ValidationEnum<>(Status.class, true);

    private final boolean isNull;

    public ValidationWorker(boolean isNull) {
        this.isNull = isNull;
    }


    @Override
    public boolean validate(Worker worker) {
        if(worker ==  null && this.isNull){return false;};

        return nameValidator.validate(worker.getName()) && salaryValidator.validate(worker.getSalary())
                && positionValidator.validate(worker.getPosition()) && statusValidator.validate(worker.getStatus());
    }

    @Override
    public String getErrorMessage() {
        return "The element is not able to be added to the collection ";
    }


}

