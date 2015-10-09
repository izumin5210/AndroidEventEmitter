package info.izumin.android.eventemitter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by izumin on 10/9/15.
 */
@Target(ElementType.TYPE)
public @interface Listener {
    Class listener();
}
