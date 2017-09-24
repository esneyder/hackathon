package com.gleamsoft.avamigables;

/**
 * Created by Developer on 4/09/2017.
 */

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
@Override
public void onCreate() {
    super.onCreate();
    Parse.enableLocalDatastore(this);
    Parse.initialize(this);
    
}
}