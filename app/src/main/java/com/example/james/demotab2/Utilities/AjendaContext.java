package com.example.james.demotab2.Utilities;

import android.app.Application;
import android.content.Context;

/**
 * Created by James on 10/7/2016.
 */



public class AjendaContext extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}