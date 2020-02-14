package src.exceptions;

/**
 * Unchecked exceptions are thrown in place of RuntimeExceptions
 * They should be unreachable and indicate a logical error when decoding bytes
 * Unlike RuntimeExceptions, they will be recorded but not halt execution
 */

public class UncheckedException extends Exception {
    public UncheckedException() {
        System.out.println("An unhandled error occurred.");
    }

    public UncheckedException(String message) {
        super(message);
    }

    public UncheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedException(Throwable cause) {
        super(cause);
    }
}
