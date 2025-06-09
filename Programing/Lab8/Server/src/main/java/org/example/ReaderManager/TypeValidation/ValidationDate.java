package org.example.ReaderManager.TypeValidation;

import java.time.LocalDate;

public class ValidationDate  implements  Validator<LocalDate> {
    private final boolean isNull;

    public ValidationDate(boolean isNull) {
        this.isNull = isNull;
    }
    @Override
    public boolean validate(LocalDate value) {
        if(value == null){return isNull;}
        return true;
    }
    @Override
    public String getErrorMessage() {
        return "the Date must have format yy-MM-dd";
    }
}
