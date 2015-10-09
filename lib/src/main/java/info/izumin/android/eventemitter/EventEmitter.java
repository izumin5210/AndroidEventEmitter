package info.izumin.android.eventemitter;

import java.util.ArrayList;
import java.util.List;

public abstract class EventEmitter<L> {
    private List<L> mListeners;

    public EventEmitter() {
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
}
