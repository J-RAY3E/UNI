package ReaderManager.Validators;

public  interface Validator <T> {

    public boolean validate(T value);
    default String getErrorMessage(){
        return "the field was not correct check the syntaxis";
    }
}
