package src.exceptions;

/**
 * Exception class used to indicate when an argument has been parsed which is not a valid opcode
 */

public class UnknownOpcodeException extends Exception {
    public UnknownOpcodeException() {
        super("An unknown error occurred");
    }

    public UnknownOpcodeException(String errorMessage) {
        super(errorMessage);
    }

    public UnknownOpcodeException(String errorMessage, Throwable cause){
        super(errorMessage, cause);
    }

    public UnknownOpcodeException(Throwable cause) {
        super(cause);
    }
}