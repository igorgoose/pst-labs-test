package by.pst.schepov.test.service.exception;

public class UnexpectedError extends RuntimeException {
    public UnexpectedError() {
    }

    public UnexpectedError(String message) {
        super(message);
    }

    public UnexpectedError(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedError(Throwable cause) {
        super(cause);
    }
}
