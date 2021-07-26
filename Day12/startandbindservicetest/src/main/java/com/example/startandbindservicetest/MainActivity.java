package com.example.startandbindservicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            if (service instanceof ICommunication) {
                mBinder = (ICommunication) service;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            mBinder = null;
        }
    };

    private boolean mServiceBinded;

    private ICommunication mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBinder != null && mServiceBinded) {
            unbindService(mConnection);
            mBinder = null;
            mServiceBinded = false;
        }
    }

    public void onClickStartService(View view) {
        Intent intent = new Intent(this, FirstService.class);
        startService(intent);
    }

    public void onClickStopService(View view) {
        Intent intent = new Intent(this, FirstService.class);
        stopService(intent);
    }

    public void onClickBindService(View view) {
        if (mBinder == null && !mServiceBinded) {
            Intent intent = new Intent(this, FirstService.class);
            mServiceBinded = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void onClickUnbindService(View view) {
        if (mBinder != null && mServiceBinded) {
            unbindService(mConnection);
            mBinder = null;
            mServiceBinded = false;
        }
    }

    public void onClickCallServiceMethod(View view) {
        if (mBinder != null && mServiceBinded) {
            mBinder.callServiceMethod();
        }
    }
}