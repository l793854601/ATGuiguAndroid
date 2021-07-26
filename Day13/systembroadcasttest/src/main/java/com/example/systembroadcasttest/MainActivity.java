package com.example.systembroadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NetworkStateChangedReceiver mNetworkStateChangedReceiver;
    private BatteryStateChangedReceiver mBatteryStateChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceivers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    private void registerReceivers() {
        if (mNetworkStateChangedReceiver == null) {
            mNetworkStateChangedReceiver = new NetworkStateChangedReceiver();
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mNetworkStateChangedReceiver, intentFilter);
        }

        if (mBatteryStateChangedReceiver == null) {
            mBatteryStateChangedReceiver = new BatteryStateChangedReceiver();
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(mBatteryStateChangedReceiver, intentFilter);
        }
    };

    private void unregisterReceivers() {

        if (mNetworkStateChangedReceiver != null) {
            unregisterReceiver(mNetworkStateChangedReceiver);
            mNetworkStateChangedReceiver = null;
        }

        if (mBatteryStateChangedReceiver != null) {
            unregisterReceiver(mBatteryStateChangedReceiver);
            mBatteryStateChangedReceiver = null;
        }
    }
}