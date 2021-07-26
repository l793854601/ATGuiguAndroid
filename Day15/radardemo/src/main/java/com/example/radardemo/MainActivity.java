package com.example.radardemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MESSAGE_UPDATE_PROGRESS = 100;

    private static final int MESSAGE_SCAN_FINISH = 200;

    private ImageView mIvPointer;
    private TextView mTvScanInfo;
    private ProgressBar mPb;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MESSAGE_UPDATE_PROGRESS:
                    //  更新进度
                    mPb.setProgress(mProgress);
                    //  更新信息
                    mTvScanInfo.setText("扫描中...");
                    break;
                case MESSAGE_SCAN_FINISH:
                    //  取消动画
                    mIvPointer.clearAnimation();
                    //  隐藏进度条
                    mPb.setVisibility(View.GONE);
                    //  更新信息
                    mTvScanInfo.setText("扫描完成");
                    break;
            }
        }
    };

    private int mProgress = 0;

    private Animation mScanAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        startScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);
        mPb.clearAnimation();
    }

    private void initViews() {
        mIvPointer = findViewById(R.id.iv_pointer);
        mTvScanInfo = findViewById(R.id.tv_scan_info);
        mPb = findViewById(R.id.pb);
        mPb.setProgress(mProgress);
    }

    private void startScan() {
        //  1.显示扫描动画
        showScanAnimation();
        //  2.扫描，并显示扫描结果
        handleScanProgress();
    }

    private void showScanAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scan);
        mIvPointer.startAnimation(animation);
    }

    private void handleScanProgress() {
        new Thread(() -> {
            while (true) {
                mProgress++;
                if (mProgress >= 100) {
                    //  切换至主线程，停止扫描
                    Message message = Message.obtain();
                    message.what = MESSAGE_SCAN_FINISH;
                    mHandler.sendMessage(message);

                    break;
                } else {
                    //  切换至主线程，更新进度
                    Message message = Message.obtain();
                    message.what = MESSAGE_UPDATE_PROGRESS;
                    mHandler.sendMessage(message);
                }

                SystemClock.sleep(500);
            }

        }).start();
    }
}