package dagger_async.recipes.frogermcs.com.asyncinjection;

import javax.inject.Singleton;

import dagger.Component;
import rx.Observable;

/**
 * Created by froger_mcs on 14/09/16.
 */

@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {
    MainActivityComponent plus(MainActivityComponent.MainActivityModule module);

    public HeavyExternalLib heavyExternalLib();

    public Observable<HeavyExternalLib> heavyExternalLibObservable();
}