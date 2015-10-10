package info.izumin.android.eventemitter.processor;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import info.izumin.android.eventemitter.processor.exception.InvalidCallbackMethodNameException;

/**
 * Created by izumin on 10/9/15.
 */
public class CallbackModel {
    private final ExecutableElement element;

    private final String originalMethodName;
    private final String methodName;
    private final String methodForOnUiThreadName;
    private final List<? extends VariableElement> paramerters;

    public CallbackModel(ExecutableElement element) {
        this.element = element;
        this.originalMethodName = element.getSimpleName().toString();
        validate();
        this.methodName = "emit" + originalMethodName.substring(0, 1).toUpperCase() + originalMethodName.substring(1);
        this.methodForOnUiThreadName = methodName + "OnUiThread";
        this.paramerters = element.getParameters();
    }

    public String getOriginalMethodName() {
        return originalMethodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodForOnUiThreadName() {
        return methodForOnUiThreadName;
    }

    public List<? extends VariableElement> getParamerters() {
        return paramerters;
    }

    private void validate() {
        if (!element.getSimpleName().toString().startsWith("on")) {
            throw new InvalidCallbackMethodNameException(originalMethodName + " is invalid. Callback method name should start with 'on'");
        }
    }
}
