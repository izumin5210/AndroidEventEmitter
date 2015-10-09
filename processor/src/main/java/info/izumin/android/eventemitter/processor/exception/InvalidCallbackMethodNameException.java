package info.izumin.android.eventemitter.processor.exception;

/**
 * Created by izumin on 10/9/15.
 */
public class InvalidCallbackMethodNameException extends RuntimeException {
    public InvalidCallbackMethodNameException() {
    }

    public InvalidCallbackMethodNameException(String message) {
        super(message);
    }

    public InvalidCallbackMethodNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCallbackMethodNameException(Throwable cause) {
        super(cause);
    }
}
