package exceptions;

/**
 * Exception class to handle math errors when parsing invalid parameters
 * Usually used to deal with arithmetic exceptions
 */

public class IllegalOperationException extends Exception {

    public IllegalOperationException() {
        //Exception is thrown by StopAndArithmetic.java
        super("This operation is not possible with the specified parameters");
    }

    public IllegalOperationException(String cause) {
        super(cause);
    }

    public IllegalOperationException(String cause, Throwable source){
        super(cause, source);
    }

    public IllegalOperationException(Throwable source) {
        super(source);
    }
}

