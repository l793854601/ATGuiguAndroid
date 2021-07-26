package com.example.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrderedReceiver1 extends BroadcastReceiver {
    private static final String TAG = OrderedReceiver1.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
    }
}
