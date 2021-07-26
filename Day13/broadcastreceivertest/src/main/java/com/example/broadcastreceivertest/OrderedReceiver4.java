package com.example.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrderedReceiver4 extends BroadcastReceiver {
    private static final String TAG = OrderedReceiver4.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        //  如果是有序广播，才可以阻断广播
        if (isOrderedBroadcast()) {
            //  阻断有序广播继续向下（向优先级较低的Receiver）传播
            abortBroadcast();
        }
    }
}
