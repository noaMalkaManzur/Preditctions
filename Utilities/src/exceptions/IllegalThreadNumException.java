package exceptions;

public class IllegalThreadNumException extends RuntimeException{
    public IllegalThreadNumException(String message) {
        super(message);
    }
}
