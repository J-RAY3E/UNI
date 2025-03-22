package org.example.ReaderManager.Validators;

import java.util.Arrays;

/**
 * Validator for Enum types.
 * Ensures that the given value belongs to a specific Enum class.
 *
 * @param <T> Type of Enum being validated
 */
public final class ValidationEnum<T extends Enum<T>> implements Validator<T> {

    private final boolean isNull;
    private final Class<T> enumClass;

    /**
     * Constructor for ValidationEnum.
     *
     * @param enumClass The Enum class to validate against.
     * @param isNull    Determines if null values are allowed.
     */
    public ValidationEnum(Class<T> enumClass, boolean isNull) {
        this.enumClass = enumClass;
        this.isNull = isNull;
    }

    @Override
    public boolean validate(T value) {
        if (value == null) return isNull;
        return Arrays.asList(enumClass.getEnumConstants()).contains(value);
    }

    @Override
    public String getErrorMessage() {
        return "Invalid value inserted.";
    }
}
