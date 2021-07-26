package com.example.bindservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/*
    调用bindService()方法，首次绑定Service：
    构造方法->onCreate()->onStartCommand()->onBind()->onServiceConnected()

    调用unBindService()方法，解绑Service：
    onUnbind()（当所有绑定Service的绑定者与Service解绑时才会调用）->onDestroy()
 */
public class FirstService extends Service {

    private static final String TAG = "FirstService";

    public class InnerBinder extends Binder {
        public void callServiceMethod() {
            sayHello();
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
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
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

    private void sayHello() {
        Toast.makeText(this, "Hello world!", Toast.LENGTH_SHORT).show();
    }
}