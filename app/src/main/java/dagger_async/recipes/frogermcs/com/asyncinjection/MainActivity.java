package dagger_async.recipes.frogermcs.com.asyncinjection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_LOAD_BLOCKING = "key_load_blocking";

    @Inject
    SharedPreferences sharedPreferences;

    //Those are injected in `setupExternalLibrary()` method
    Observable<HeavyExternalLib> heavyExternalLibObservable;
    HeavyExternalLib heavyExternalLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLayout();
    }

    private void setupLayout() {
        long activityLaunchTimeMillis = System.currentTimeMillis() - MyApplication.launchTimeMillis;
        TextView tvHello = (TextView) findViewById(R.id.tvHello);
        tvHello.setText("Activity launched in: " + activityLaunchTimeMillis + "ms");

        Switch swInject = (Switch) findViewById(R.id.swInject);
        swInject.setChecked(sharedPreferences.getBoolean(KEY_LOAD_BLOCKING, false));
        swInject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean(KEY_LOAD_BLOCKING, isChecked).apply();
            }
        });
    }

    private void setupExternalLibrary() {
        if (sharedPreferences.getBoolean(KEY_LOAD_BLOCKING, false)) {
            injectHeabyExternalLibraryBlocking();
            callHeavyExternalLibMethod();
        } else {
            injectHeabyExternalLibraryObservable();
            heavyExternalLibObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<HeavyExternalLib>() {
                        @Override
                        public void call(HeavyExternalLib heavyExternalLib) {
                            MainActivity.this.heavyExternalLib = heavyExternalLib;
                            callHeavyExternalLibMethod();
                        }
                    });
        }
    }

    private void injectDependencies() {
        MyApplication.getAppComponent(this)
                .plus(new MainActivityComponent.MainActivityModule(this))
                .inject(this);

        setupExternalLibrary();
    }

    private void injectHeabyExternalLibraryBlocking() {
        heavyExternalLib = MyApplication.getAppComponent(this).heavyExternalLib();
    }

    private void injectHeabyExternalLibraryObservable() {
        heavyExternalLibObservable = MyApplication.getAppComponent(this).heavyExternalLibObservable();
    }

    private void callHeavyExternalLibMethod() {
        heavyExternalLib.callMethod(this);
    }

    public void onRestartAppClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        Runtime.getRuntime().exit(0);
    }
}
