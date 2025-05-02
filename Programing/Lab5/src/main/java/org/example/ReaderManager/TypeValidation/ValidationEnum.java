package ReaderManager.TypeValidation;

import java.util.Arrays;

public class ValidationEnum<T extends  Enum<T>> implements  Validator<T>{
    private final boolean isNull;
    private final Class<T> enumClass;
    public ValidationEnum (Class<T> enumClass,boolean isNull) {
        this.enumClass = enumClass;
        this.isNull = isNull;
    }

    @Override
    public boolean validate(T value) {
        if(value == null) return isNull;
        return Arrays.asList(enumClass.getEnumConstants()).contains(value);
    }

    @Override
    public String getErrorMessage() {
        return "Invalid value inserted";
    }

}
