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
