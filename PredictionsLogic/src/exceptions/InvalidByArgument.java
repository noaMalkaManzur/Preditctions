package exceptions;

public class InvalidByArgument extends RuntimeException{
    public InvalidByArgument(String message) {
        super(message);
    }
}
