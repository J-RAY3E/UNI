package ReaderManager.TypeValidation;

public class ValidationBoolean implements   Validator<Boolean> {
    Boolean isNull;
    public ValidationBoolean(Boolean isNull) {this.isNull = isNull;}

    @Override
    public boolean validate(Boolean value) {
        if(value == null) {return isNull;};
        return true;
    }

}


