package dagger_async.recipes.frogermcs.com.asyncinjection;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.functions.Func0;

/**
 * Created by froger_mcs on 14/09/16.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    HeavyExternalLib provideHeavyExternalLib() {
        return HeavyExternalLib.initialize();
    }

    @Provides
    @Singleton
    Observable<HeavyExternalLib> heavyExternalLibObservable(final Lazy<HeavyExternalLib> heavyExternalLibLazy) {
        return Observable.defer(new Func0<Observable<HeavyExternalLib>>() {
            @Override
            public Observable<HeavyExternalLib> call() {
                return Observable.just(heavyExternalLibLazy.get());
            }
        });
    }
}