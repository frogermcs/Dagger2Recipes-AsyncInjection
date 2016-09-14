package dagger_async.recipes.frogermcs.com.asyncinjection;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by froger_mcs on 14/09/16.
 */

@ActivityScope
@Subcomponent(
        modules = MainActivityComponent.MainActivityModule.class
)
public interface MainActivityComponent {
    MainActivity inject(MainActivity activity);

    @Module
    class MainActivityModule {
        private MainActivity mainActivity;

        MainActivityModule(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }
    }
}
