package com.example.eventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import org.jetbrains.annotations.NotNull;

public class MyImageView extends AppCompatImageView {

    private static final String TAG = "MotionEventTest";

    public MyImageView(@NonNull @NotNull Context context) {
        super(context);
        Log.d(TAG, "MyImageView1: ");
    }

    public MyImageView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MyImageView2: ");
    }

    public MyImageView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "MyImageView3: ");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyImageView dispatchTouchEvent: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyImageView onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
