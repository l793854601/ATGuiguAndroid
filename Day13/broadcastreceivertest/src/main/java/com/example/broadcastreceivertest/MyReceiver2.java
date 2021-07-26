package com.example.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver2 extends BroadcastReceiver {

    private static final String TAG = "MyReceiver2";

    public MyReceiver2() {
        Log.d(TAG, "MyReceiver2: ");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action = " + intent.getAction()
                + ", name = " + intent.getStringExtra("name")
                + ", age = " + intent.getIntExtra("age", 0));
    }


}
