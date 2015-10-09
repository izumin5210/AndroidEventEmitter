package info.izumin.android.androideventemitter;

import android.view.View;

import info.izumin.android.eventemitter.annotation.Callback;
import info.izumin.android.eventemitter.annotation.Listener;

/**
 * Created by izumin on 10/9/15.
 */
@Listener
public interface TestListener {
    @Callback void onClickButton1(View v);
    @Callback void onClickButton2(View v);
}
