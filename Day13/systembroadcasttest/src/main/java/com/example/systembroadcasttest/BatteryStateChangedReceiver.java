package com.example.systembroadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryStateChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "BatteryStateChanged onReceive: ");
    }
}
