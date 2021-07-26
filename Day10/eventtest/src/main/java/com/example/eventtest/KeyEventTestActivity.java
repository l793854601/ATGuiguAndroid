package com.example.eventtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/*
    测试keyEvent
 */
public class KeyEventTestActivity extends AppCompatActivity {

    private static final String TAG = "KeyEventTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_event_test);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent: keyCode = " + event.getKeyCode() + ", action = " + event.getAction());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: keyCode = " + keyCode + ", action = " + event.getAction());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp: keyCode = " + keyCode + ", action = " + event.getAction());
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyLongPress: keyCode = " + keyCode + ", action = " + event.getAction());
        return super.onKeyLongPress(keyCode, event);
    }
}