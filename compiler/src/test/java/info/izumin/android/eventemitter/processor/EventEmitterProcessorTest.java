package info.izumin.android.eventemitter.processor;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * Created by izumin on 10/9/15.
 */
public class EventEmitterProcessorTest {
    @Test
    public void testProcessor() throws Exception {
        assert_().about(javaSource())
                .that(JavaFileObjects.forSourceString("TestListener",
                        "package com.example.android.eventemitter;\n" +
                                "\n" +
                                "import info.izumin.android.eventemitter.annotation.Listener;\n" +
                                "import info.izumin.android.eventemitter.annotation.Callback;\n" +
                                "\n" +
                                "@Listener\n" +
                                "interface TestListener {\n" +
                                "    @Callback\n" +
                                "    void onNoArgs();\n" +
                                "    @Callback\n" +
                                "    void onOneArg(int arg0);\n" +
                                "    @Callback\n" +
                                "    void onTwoArgs(String arg0, float arg1);\n" +
                                "}\n"
                ))
                .processedWith(new EventEmitterProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forSourceString("TestEventEmitter",
                        "package com.example.android.eventemitter;\n" +
                                "\n" +
                                "import android.content.Context;\n" +
                                "\n" +
                                "import info.izumin.android.eventemitter.EventEmitter;\n" +
                                "\n" +
                                "import java.lang.String;\n" +
                                "\n" +
                                "public class TestEventEmitter extends EventEmitter<TestListener> {\n" +
                                "    public TestEventEmitter(Context context) {\n" +
                                "        super(context);\n" +
                                "    }\n" +
                                "    public void emitOnNoArgs() {\n" +
                                "        for (TestListener listener : getListeners()) {\n" +
                                "            listener.onNoArgs();\n" +
                                "        }\n" +
                                "    }\n" +
                                "    public void emitOnNoArgsOnUiThread() {\n" +
                                "        getHandler().post(new Runnable() {\n" +
                                "            @Override\n" +
                                "            public void run() {\n" +
                                "                emitOnNoArgs();\n" +
                                "            }\n" +
                                "        });\n" +
                                "    }\n" +
                                "    public void emitOnOneArg(final int arg0) {\n" +
                                "        for (TestListener listener : getListeners()) {\n" +
                                "            listener.onOneArg(arg0);\n" +
                                "        }\n" +
                                "    }\n" +
                                "    public void emitOnOneArgOnUiThread(final int arg0) {\n" +
                                "        getHandler().post(new Runnable() {\n" +
                                "            @Override\n" +
                                "            public void run() {\n" +
                                "                emitOnOneArg(arg0);\n" +
                                "            }\n" +
                                "        });\n" +
                                "    }\n" +
                                "    public void emitOnTwoArgs(final String arg0, final float arg1) {\n" +
                                "        for (TestListener listener : getListeners()) {\n" +
                                "            listener.onTwoArgs(arg0, arg1);\n" +
                                "        }\n" +
                                "    }\n" +
                                "    public void emitOnTwoArgsOnUiThread(final String arg0, final float arg1) {\n" +
                                "        getHandler().post(new Runnable() {\n" +
                                "            @Override\n" +
                                "            public void run() {\n" +
                                "                emitOnTwoArgs(arg0, arg1);\n" +
                                "            }\n" +
                                "        });\n" +
                                "    }\n" +
                                "}\n"
                ));
    }
}
