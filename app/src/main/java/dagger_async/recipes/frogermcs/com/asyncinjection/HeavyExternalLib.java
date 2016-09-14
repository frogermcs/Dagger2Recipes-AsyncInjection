package dagger_async.recipes.frogermcs.com.asyncinjection;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by froger_mcs on 14/09/16.
 */

public class HeavyExternalLib {

    private HeavyExternalLib() {
    }

    public static HeavyExternalLib initialize() {
        HeavyExternalLib heavyExternalLib = new HeavyExternalLib();

        //Do some blocking operations
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return heavyExternalLib;
    }

    public void callMethod(Context context) {
        Toast.makeText(context, "Heavy lib method called", Toast.LENGTH_LONG).show();
    }
}
