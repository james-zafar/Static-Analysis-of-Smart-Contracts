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
