package org.example.ReaderManager.TypeValidation;

public class ValidationNumber implements Validator<Number>{
    final Double max;
    final Double min;
    final boolean isNull;
    public ValidationNumber(Double min, Double max, Boolean isNull) {
        this.min = min;
        this.max = max;
        this.isNull = isNull;
    }


    @Override
    public boolean validate(Number value) {
        if(value == null) return isNull;
        Double  x =  value.doubleValue();
        return min <= x && x <= max;
    }

    @Override
    public String getErrorMessage() {
        return "El número debe estar entre " + min + " y " + max;
    }


}
