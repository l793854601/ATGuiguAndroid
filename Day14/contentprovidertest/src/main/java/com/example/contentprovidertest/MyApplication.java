package com.example.contentprovidertest;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }
}
