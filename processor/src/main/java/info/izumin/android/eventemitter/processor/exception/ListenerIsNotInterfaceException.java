package info.izumin.android.eventemitter.processor.exception;

/**
 * Created by izumin on 10/9/15.
 */
public class ListenerIsNotInterfaceException extends RuntimeException {
    public ListenerIsNotInterfaceException() {
    }

    public ListenerIsNotInterfaceException(String message) {
        super(message);
    }

    public ListenerIsNotInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListenerIsNotInterfaceException(Throwable cause) {
        super(cause);
    }
}
