package com.example.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
    静态注册的广播接收器
 */
public class MyReceiver1 extends BroadcastReceiver {
    private static final String TAG = "MyReceiver1";

    public MyReceiver1() {
        Log.d(TAG, "MyReceiver1() ");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action = " + intent.getAction()
                + ", name = " + intent.getStringExtra("name")
                + ", age = " + intent.getIntExtra("age", 0));
    }
}
