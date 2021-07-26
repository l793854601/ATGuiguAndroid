package com.example.systembroadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "BootCompleted onReceive: ");
        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
    }
}
