package com.example.startservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/*
    调用startService()方法，首次启动Service：
    构造方法->onCreate()->onStart()（已废弃）->onStartCommand()

    调用startService()方法，再次启动已经启动的Service：
    onStart()（已废弃）->onStartCommand()

    调用stopService()方法，停止已启动的Service：
    onDestroy()
 */
public class FirstService extends Service {

    private static final String TAG = "FirstService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//        Log.d(TAG, "onStart: ");
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sayHello() {
        Toast.makeText(this, "Hello world!", Toast.LENGTH_SHORT).show();
    }
}

