package Enviroment.Exceptions;

public class NullStateEntityException extends RuntimeException {
    
    public NullStateEntityException(String mesage){
        super(mesage);

    }
    @Override
    public String getMessage(){
        return this.getClass().getSimpleName()+ ": " +super.getMessage();
    }


}