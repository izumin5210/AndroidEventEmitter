package info.izumin.android.eventemitter.processor;

import android.content.Context;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import info.izumin.android.eventemitter.EventEmitter;

/**
 * Created by izumin on 10/9/15.
 */
public class EventEmitterWriter {

    private ListenerModel model;

    public EventEmitterWriter(ListenerModel model) {
        this.model = model;
    }

    public void write(Filer filer) throws IOException {
        TypeSpec typeSpec = createTypeSpec();
        JavaFile.builder(model.getPackageName(), typeSpec)
                .build()
                .writeTo(filer);
    }

    private TypeSpec createTypeSpec() {
        return TypeSpec.classBuilder(model.getClassName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(createSuperclass().box())
                .addMethod(createConstructor())
                .addMethods(createMethodSpecs())
                .build();
    }

    private ParameterizedTypeName createSuperclass() {
        return ParameterizedTypeName.get(
                ClassName.get(EventEmitter.class),
                ClassName.get(model.getPackageName(), model.getOriginalClassName()).box()
        );
    }

    private MethodSpec createConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(Context.class), "context")
                .addCode("super(context);")
                .build();
    }

    private List<MethodSpec> createMethodSpecs() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (CallbackModel callbackModel : model.getCallbacks()) {
            methodSpecs.add(createMethodSpec(callbackModel));
            methodSpecs.add(createMethodForOnUiThreadSpec(callbackModel));
        }
        return methodSpecs;
    }

    private MethodSpec createMethodSpec(CallbackModel callbackModel) {
        return MethodSpec.methodBuilder(callbackModel.getMethodName())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameters(createParameters(callbackModel))
                .addCode(createCode(callbackModel))
                .build();
    }

    private MethodSpec createMethodForOnUiThreadSpec(CallbackModel callbackModel) {
        return MethodSpec.methodBuilder(callbackModel.getMethodForOnUiThreadName())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameters(createParameters(callbackModel))
                .addCode(createCodeForOnUiThread(callbackModel))
                .build();
    }

    private List<ParameterSpec> createParameters(CallbackModel callbackModel) {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();
        for (VariableElement variableElement : callbackModel.getParamerters()) {
            parameterSpecs.add(createParameter(variableElement));
        }
        return parameterSpecs;
    }

    private ParameterSpec createParameter(VariableElement variableElement) {
        return ParameterSpec.builder(
                TypeName.get(variableElement.asType()),
                variableElement.getSimpleName().toString(),
                Modifier.FINAL
        ).build();
    }

    private CodeBlock createCode(CallbackModel callbackModel) {
        return CodeBlock.builder()
                .add("for (" + model.getOriginalClassName() + " listener : getListeners()) {\n")
                .add("listener." + callbackModel.getOriginalMethodName() + "(" + getArgsString(callbackModel) + ");\n")
                .add("}\n")
                .build();
    }

    private CodeBlock createCodeForOnUiThread(CallbackModel callbackModel) {
        return CodeBlock.builder()
                .add("getHandler().post(new Runnable() {\n")
                .add("@Override\n")
                .add("public void run() {\n")
                .add(callbackModel.getMethodName() + "(" + getArgsString(callbackModel) + ");\n")
                .add("}\n")
                .add("});\n")
                .build();
    }

    private String getArgsString(CallbackModel callbackModel) {
        StringBuilder argsBuilder = new StringBuilder();
        for (VariableElement variableElement : callbackModel.getParamerters()) {
            argsBuilder.append(variableElement.getSimpleName()).append(",");
        }
        return (argsBuilder.length() == 0) ? "" : argsBuilder.toString().substring(0, argsBuilder.length() - 1);
    }
}
