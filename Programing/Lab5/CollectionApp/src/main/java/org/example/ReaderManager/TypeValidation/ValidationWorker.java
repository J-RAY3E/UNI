package org.example.ReaderManager.TypeValidation;

import org.example.Classes.Worker;

public class ValidationWorker implements Validator<Worker>{

    final boolean isNull;
    public ValidationWorker(Double min, Double max, Boolean isNull) {
        this.isNull = isNull;
    }


    @Override
    public boolean validate(Worker value) {
        if(value ==  null && this.isNull){return false;};
        if(!(value.getName() == null)) {return true;};
        return false;
    }

    @Override
    public String getErrorMessage() {
        return "The element is not able to be added to the collection ";
    }


}

