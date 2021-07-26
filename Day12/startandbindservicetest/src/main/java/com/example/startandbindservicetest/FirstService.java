package com.example.startandbindservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class FirstService extends Service {

    private static final String TAG = "FirstService";

    private class InnerBinder extends Binder implements ICommunication {

        @Override
        public void callServiceMethod() {
            satHello();
        }
    }

    public FirstService() {
        Log.d(TAG, "FirstService() ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return new InnerBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void satHello() {
        Toast.makeText(this, "Hello world!", Toast.LENGTH_SHORT).show();
    }
}