package org.example.ReaderManager.Validators;

import java.time.LocalDate;

/**
 * Validator for LocalDate.
 * Ensures that the date is valid and optionally allows null values.
 */
public final class ValidationDate implements Validator<LocalDate> {

    private final boolean isNull;

    /**
     * Constructor specifying whether null values are allowed.
     *
     * @param isNull Determines if null values are valid.
     */
    public ValidationDate(boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public boolean validate(LocalDate value) {
        return value != null || isNull;
    }

    @Override
    public String getErrorMessage() {
        return "The date must have the format yyyy-MM-dd.";
    }
}
