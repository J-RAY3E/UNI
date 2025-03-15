package org.example.ReaderManager;


import org.example.ReaderManager.Inputs.InputManager;
import org.example.ReaderManager.TypeValidation.Validator;
import org.example.ReaderManager.Parse.ValueParse;


public class InputValidator {

    private final InputManager inputManager;
    public InputValidator(final InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public  <T> T execute(String message,  Validator<T> validator,ValueParse<T> parser ){
        T value = null;
        do{
            System.out.print(message);
            String input = this.inputManager.nextLine();
            if(input.isEmpty() && validator.validate(null)){
                return null;
            }
            try{
                value = parser.parse(input);
                if(!validator.validate(value)){
                    System.out.println("Invalid Format: " + validator.getErrorMessage());
                    value = null;
                }
            }
            catch (Exception e){
                System.out.println("Invalid Type");
            }
        }while(value == null);
        return value;
    }

    public  <T> T execute(  Validator<T> validator,ValueParse<T> parser, String input){
        T value = null;
            try{
                value = parser.parse(input);
                if(!validator.validate(value)){
                    System.out.println("Invalid Format: " + validator.getErrorMessage());
                    value = null;
                }
            }
            catch (Exception e){
                System.out.println("Wrong Syntax");
            }
        return value;
    }
}
