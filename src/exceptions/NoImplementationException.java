package src.exceptions;

public class NoImplementationException extends Exception {
    public NoImplementationException() {
        super("No suitable method found for this operation");
    }

    public NoImplementationException(String message) {
        super(message);
    }

    public NoImplementationException(String message, Throwable source){
        super(message, source);
    }

    public NoImplementationException(Throwable source) {
        super(source);
    }
}
