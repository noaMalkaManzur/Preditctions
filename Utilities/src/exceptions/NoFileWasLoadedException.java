package exceptions;

public class NoFileWasLoadedException extends RuntimeException{
    public NoFileWasLoadedException(String message) {
        super(message);
    }
}
