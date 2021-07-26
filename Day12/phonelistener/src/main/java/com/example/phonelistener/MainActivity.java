package com.example.phonelistener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telecom.InCallService;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //  建议将listener设置为全局变量
    private PhoneStateListener mListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            Log.d(TAG, "onCallStateChanged: state = " + state + ", phoneNumber = " + phoneNumber);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}