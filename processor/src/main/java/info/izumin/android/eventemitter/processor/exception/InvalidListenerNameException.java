package info.izumin.android.eventemitter.processor.exception;

/**
 * Created by izumin on 10/9/15.
 */
public class InvalidListenerNameException extends RuntimeException {
    public InvalidListenerNameException() {
    }

    public InvalidListenerNameException(String message) {
        super(message);
    }

    public InvalidListenerNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidListenerNameException(Throwable cause) {
        super(cause);
    }
}
