package src.exceptions;

/**
 * Exception class to manage any unexpected issues when creating the final program flow
 * Usually indicates a bad stack value or data improperly formatted in Decoder.java
 */

public class ProgramFlowException extends Exception {
    public ProgramFlowException() {
        super("An error occurred when creating the program flow");
    }

    public ProgramFlowException(String msg) {
        super(msg);
    }

    public ProgramFlowException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ProgramFlowException(Throwable cause) {
        super(cause);
    }
}
