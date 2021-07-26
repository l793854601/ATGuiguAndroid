package com.example.eventtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/*
    测试MotionEvent
 */
public class MotionEventTestActivity extends AppCompatActivity {

    private static final String TAG = "MotionEventTest";

    private ImageView mIvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event_test);

        mIvIcon = findViewById(R.id.iv_icon);

        mIvIcon.setOnTouchListener((v, event) -> {
            Log.d(TAG, "MyImageView onTouch: " + event.getAction());
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MotionEventTestActivity dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MotionEventTestActivity onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }
}