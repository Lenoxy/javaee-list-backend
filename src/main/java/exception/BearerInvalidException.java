package exception;

public class BearerInvalidException extends RuntimeException{
    public BearerInvalidException(String s){
        super(s);
    }
}
