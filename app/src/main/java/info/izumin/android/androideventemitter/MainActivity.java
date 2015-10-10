package info.izumin.android.androideventemitter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TestListener, View.OnClickListener {

    private TestEventEmitter mEmitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mEmitter = new TestEventEmitter(this);

        mEmitter.addListener(this);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClickButton1(View v) {
        Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickButton2(View v) {
        Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mEmitter.emitOnClickButton1(v);
                break;
            case R.id.btn2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            mEmitter.emitOnClickButton2OnUiThread(v);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}
