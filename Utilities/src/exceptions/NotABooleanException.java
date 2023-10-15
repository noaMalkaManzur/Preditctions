package exceptions;

public class NotABooleanException extends RuntimeException{
    public NotABooleanException(String message) {
        super(message);
    }
}
