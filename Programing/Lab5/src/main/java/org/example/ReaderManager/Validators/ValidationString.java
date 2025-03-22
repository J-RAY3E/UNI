package org.example.readerManager.validators;


/**
 * Validator for Strings.
 * Ensures that the string is not null (if required) and does not exceed the maximum length.
 */
public final class ValidationString implements Validator<String> {

    private final int maxLength;
    private final boolean isNull;

    /**
     * Constructor specifying max length and nullability.
     *
     * @param maxLength The maximum allowed length of the string.
     * @param isNull    Determines if null values are allowed.
     */
    public ValidationString(int maxLength, boolean isNull) {
        this.maxLength = maxLength;
        this.isNull = isNull;
    }

    /**
     * Default constructor with no constraints.
     */
    public ValidationString() {
        this.maxLength = Integer.MAX_VALUE;
        this.isNull = true;
    }

    @Override
    public boolean validate(String value) {
        if (value == null) return isNull;
        return value.length() <= maxLength;
    }

    @Override
    public String getErrorMessage() {
        return "String length exceeds " + maxLength + " characters.";
    }
}