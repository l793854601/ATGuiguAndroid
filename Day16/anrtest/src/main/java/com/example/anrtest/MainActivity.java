package com.example.anrtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickANR(View view) {
        Log.d(TAG, "onClickANR: ");
        /*
            模拟ANR的场景：睡眠10s
            此时再点击返回键，则会弹框提示无响应
         */
        SystemClock.sleep(10 * 1000);
    }
}