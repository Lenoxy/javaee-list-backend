package exception;

public class UserClaimMissingException extends RuntimeException{
    public UserClaimMissingException(String s){
        super(s);
    }
}
