package dagger_async.recipes.frogermcs.com.asyncinjection;

import android.app.Application;
import android.content.Context;


/**
 * Created by froger_mcs on 14/09/16.
 */

public class MyApplication extends Application {

    public static long launchTimeMillis = 0;

    private AppComponent appComponent;

    public static AppComponent getAppComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        launchTimeMillis = System.currentTimeMillis();
        super.onCreate();
        setupAppComponent();
    }

    private void setupAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
