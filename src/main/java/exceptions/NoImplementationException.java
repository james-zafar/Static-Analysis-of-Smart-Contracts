package exceptions;

/**
 * Exception classed used to deal with issues caused by opcode functions not being implemented
 */

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
