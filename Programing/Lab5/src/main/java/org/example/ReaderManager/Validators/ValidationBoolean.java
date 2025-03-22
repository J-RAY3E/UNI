package org.example.readerManager.validators;

/**
 * Validator for Boolean values.
 * Ensures that the value is not null unless explicitly allowed.
 */
public final class ValidationBoolean implements Validator<Boolean> {

    private final boolean isNull;

    /**
     * Constructor specifying whether null values are allowed.
     *
     * @param isNull Determines if null values are valid.
     */
    public ValidationBoolean(boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public boolean validate(Boolean value) {
        return value != null || isNull;
    }

    @Override
    public String getErrorMessage() {
        return "Invalid boolean value.";
    }
}
