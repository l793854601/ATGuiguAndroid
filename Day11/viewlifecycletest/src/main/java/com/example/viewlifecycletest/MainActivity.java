package com.example.viewlifecycletest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTextViewTest";

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MyTextView tv = new MyTextView(this);
//        tv.setText("使用代码创建的MyTextView");
//        setContentView(tv);

//        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
//        setContentView(view);

        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    public void onClickRequestLayout(View view) {
        mTv.requestLayout();
    }

    public void onClickInvalidate(View view) {
        mTv.invalidate();
    }

    public void onClickPostInvalidate(View view) {
        Runnable runnable = () -> {
            mTv.postInvalidate();
        };
        Executors.newCachedThreadPool().execute(runnable);
    }

    public void onClickRemove(View view) {
        ViewGroup parent = (ViewGroup) mTv.getParent();
        parent.removeView(mTv);
    }
}