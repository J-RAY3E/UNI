package Enviroment.Exceptions;

public class ListIndexException extends RuntimeException {
    
    public ListIndexException(String mesage){
        super(mesage);

    }
    @Override
    public String getMessage(){
        return this.getClass().getSimpleName()+ ": " +super.getMessage();
    }


}