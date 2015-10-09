package info.izumin.android.eventemitter.processor.exception;

/**
 * Created by izumin on 10/9/15.
 */
public class InvalidCallbackReturnTypeException extends RuntimeException {
    public InvalidCallbackReturnTypeException() {
    }

    public InvalidCallbackReturnTypeException(String message) {
        super(message);
    }

    public InvalidCallbackReturnTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCallbackReturnTypeException(Throwable cause) {
        super(cause);
    }
}
