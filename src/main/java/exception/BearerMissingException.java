package exception;

public class BearerMissingException extends RuntimeException{
    public BearerMissingException(String s){
        super(s);
    }
}
