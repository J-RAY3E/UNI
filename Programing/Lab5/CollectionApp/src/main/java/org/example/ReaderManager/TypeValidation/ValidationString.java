package org.example.ReaderManager.TypeValidation;

public class ValidationString implements Validator<String> {
    int maxlength;
    boolean isNull;
    public ValidationString(int length) {
        this.maxlength = length;
    }
    public ValidationString() {}
    @Override
    public boolean validate(String value) {
        if (value == null){return isNull;};
        return value.length() <= this.maxlength;

    }
}
