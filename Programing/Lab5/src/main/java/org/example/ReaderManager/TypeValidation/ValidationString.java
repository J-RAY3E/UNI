package ReaderManager.TypeValidation;

public class ValidationString implements Validator<String> {
    int maxlength;
    boolean isNull;
    public ValidationString(int length,Boolean isNull) {
        this.maxlength = length;
        this.isNull = isNull;
    }
    public ValidationString() {}
    @Override
    public boolean validate(String value) {
        if (value == null){return isNull;};
        if(!(this.maxlength == 0)){return value.length() <= this.maxlength;}
        return true;

    }
}
