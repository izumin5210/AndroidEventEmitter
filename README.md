# EventEmitter for Android
[![wercker status](https://app.wercker.com/status/21e3fb7f70933503c9a9faadba53eeff/s/master "wercker status")](https://app.wercker.com/project/bykey/21e3fb7f70933503c9a9faadba53eeff)
[![Download](https://api.bintray.com/packages/izumin5210/maven/eventemitter/images/download.svg)](https://bintray.com/izumin5210/maven/eventemitter/_latestVersion)
[![Download](https://api.bintray.com/packages/izumin5210/maven/eventemitter-compiler/images/download.svg)](https://bintray.com/izumin5210/maven/eventemitter-compiler/_latestVersion)

## Download

```groovy
dependencies {
    apt 'info.izumin.android:eventemitter-compiler:0.2.0'
    compile 'info.izumin.android:eventemitter:0.2.0'
}
```

## Usage
### Listener Interface
Create a listener interface. Its name should end with `Listener`.

```java
@Listener
public interface ConnectionListener {
    @Callback void onConnect();
    @Callback void onDisconnect();
    @Callback void onError(Exception e);
}
```

Add `Listener` annotation to listener interface, and also add `Callback` annotation to callback methods.

### Instantiate EventEmitter instance

Annotation processor generates `*EventEmitter` class and `emit*` methods.

```java
ConnectionEventEmitter emitter = new ConnectionEventEmitter();

emitter.on(connectionListenerImpl);

emitter.emitOnConnect(); // Call onCreate() for all registered listeners

new Thread(new Runnable() {
    @Override
    public void run() {
        emitter.emitOnConnectOnUiThread() // Call onConnect on UI thread
    }
});
```

## Licenses
licensed under [MIT-license](https://izumin.mit-license.org/2015).