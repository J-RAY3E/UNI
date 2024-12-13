package Enviroment.Exceptions;

public class ClassCastException extends RuntimeException {
    
    public ClassCastException(String mesage){
        super(mesage);

    }
    @Override
    public String getMessage(){
        return this.getClass().getSimpleName()+ ": " +super.getMessage();
    }


}