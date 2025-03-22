package org.example.ReaderManager.Validators;


/**
 * Validator for numerical values.
 * Ensures that the number is within the specified range.
 */
public final class ValidationNumber implements Validator<Number> {

    private final double max;
    private final double min;
    private final boolean isNull;

    /**
     * Constructor specifying the valid range and nullability.
     *
     * @param min    The minimum allowed value.
     * @param max    The maximum allowed value.
     * @param isNull Determines if null values are allowed.
     */
    public ValidationNumber(double min, double max, boolean isNull) {
        this.min = min;
        this.max = max;
        this.isNull = isNull;
    }

    @Override
    public boolean validate(Number value) {
        if (value == null) return isNull;
        double num = value.doubleValue();
        return min <= num && num <= max;
    }

    @Override
    public String getErrorMessage() {
        return "The number must be between " + min + " and " + max + ".";
    }
}