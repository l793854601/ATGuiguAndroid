package com.example.bindservicetest;

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

    private boolean mIsServiceBinded;

    private FirstService.InnerBinder mInnerBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            if (service instanceof FirstService.InnerBinder) {
                mInnerBinder = (FirstService.InnerBinder) service;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            mInnerBinder = null;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mIsServiceBinded && mInnerBinder != null) {
            unbindService(mConnection);
            mIsServiceBinded = false;
            mInnerBinder = null;
        }
    }

    /*
        绑定服务
     */
    public void onClickBindService(View view) {
        if (!mIsServiceBinded && mInnerBinder == null) {
            Intent intent = new Intent(this, FirstService.class);
            mIsServiceBinded = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    /*
        解绑服务
     */
    public void onClickUnbindService(View view) {
        if (mIsServiceBinded && mInnerBinder != null) {
            unbindService(mConnection);
            mIsServiceBinded = false;
            mInnerBinder = null;
        }
    }

    /*
        调用服务中的方法
     */
    public void onClickCallServiceMethod(View view) {
        if (mInnerBinder != null) {
            mInnerBinder.callServiceMethod();
        }
    }
}