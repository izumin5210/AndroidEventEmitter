package info.izumin.android.eventemitter;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public abstract class EventEmitter<L> {
    private final Handler mHandler;
    private final List<L> mListeners;

    public EventEmitter(Context context) {
        mHandler = new Handler(context.getMainLooper());
        mListeners = new ArrayList<>();
    }

    public void addListener(L listener) {
        mListeners.add(listener);
    }

    public void on(L listener) {
        addListener(listener);
    }

    public void removeListener(L listener) {
        mListeners.remove(listener);
    }

    public void removeAllListeners() {
        mListeners.clear();
    }

    public List<L> getListeners() {
        return new ArrayList<>(mListeners);
    }

    protected Handler getHandler() {
        return mHandler;
    }
}
