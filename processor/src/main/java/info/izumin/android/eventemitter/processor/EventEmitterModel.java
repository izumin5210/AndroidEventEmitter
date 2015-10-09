package info.izumin.android.eventemitter.processor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;

import info.izumin.android.eventemitter.annotation.Callback;
import info.izumin.android.eventemitter.processor.exception.InvalidListenerNameException;
import info.izumin.android.eventemitter.processor.exception.ListenerIsNotInterfaceException;

/**
 * Created by izumin on 10/9/15.
 */
public class EventEmitterModel {
    private final Element element;

    private final String packageName;
    private final String originalClassName;
    private final String className;
    private final List<CallbackModel> callbacks;

    public EventEmitterModel(Element element, Elements elementUtils) {
        this.element = element;
        this.packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        this.originalClassName = element.getSimpleName().toString();
        validate();
        this.className = originalClassName.replaceAll("Listener$", "EventEmitter");

        this.callbacks = new ArrayList<>();
        findAnnotations(element);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getOriginalClassName() {
        return originalClassName;
    }

    public String getClassName() {
        return className;
    }

    public List<CallbackModel> getCallbacks() {
        return callbacks;
    }

    private void validate() {
        if (!originalClassName.endsWith("Listener")) {
            throw new InvalidListenerNameException(originalClassName + " is invalid. Listener interface name should end with 'Listener'");
        }
        if (element.getKind() != ElementKind.INTERFACE) {
            throw new ListenerIsNotInterfaceException(originalClassName + " is invalid. Listener should be interface.");
        }
    }

    private void findAnnotations(Element element) {
        for (Element enclosedElement : element.getEnclosedElements()) {
            findAnnotations(enclosedElement);

            Callback callback = enclosedElement.getAnnotation(Callback.class);
            if (callback != null) {
                callbacks.add(new CallbackModel((ExecutableElement) enclosedElement));
            }
        }
    }
}
