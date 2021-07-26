package com.example.componenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

/*
    进度条Component
 */
public class ProgressComponentActivity extends AppCompatActivity {

    private static final String TAG = "PROGRESS_TAG";
    
    private LinearLayout mLlLoading;
    private ProgressBar mPb;
    private SeekBar mSb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_component);

        mLlLoading = findViewById(R.id.ll_loading);
        mPb = findViewById(R.id.pb);
        mSb = findViewById(R.id.sb);

        mSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*
                进度改变
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: " + progress);
            }

            /*
                按下滑杆
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            /*
                从滑杆离开
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: ");

                //  1.得到seekBar的进度
                int progress = seekBar.getProgress();
                //  2.设置progressBar的进度
                mPb.setProgress(progress);

                //  1.获取visibility
                //  View.VISIBLE：view可见
                //  View.GONE：view不可见，且不占用原有的空间
                //  View.INVISIBLE：view不可见，但占用原有的空间
                int visibility = progress == seekBar.getMax() ? View.INVISIBLE : View.VISIBLE;
                //  2.设置linearLayout的显示/隐藏
                mLlLoading.setVisibility(visibility);
            }
        });
    }
}