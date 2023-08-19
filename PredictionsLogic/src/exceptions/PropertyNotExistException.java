package exceptions;

public class PropertyNotExistException extends RuntimeException{
    public PropertyNotExistException(String message) {
        super(message);
    }
}
