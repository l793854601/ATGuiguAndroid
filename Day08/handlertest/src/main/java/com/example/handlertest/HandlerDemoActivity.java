package com.example.handlertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
    手动增加/减少
    自动增加/减少
    限制数字的范围为[1, 20]
    限制Button的可操作性

    技术点：
    使用sendEmptyMessage(int what)方法：发送空Message
    使用sendEmptyMessageDelayed(int what, long delayMillis)方法，发送延时Message
    使用removeMessages(int what)：移除未处理的Message
    handleMessage(Message msg)方法中，循环发送Message
 */
public class HandlerDemoActivity extends AppCompatActivity {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 20;

    private static final int MSG_INCREMENT = 1;
    private static final int MSG_DECREMENT = 2;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_INCREMENT) {
                //  处理mNumber的递增操作
                if (++mNumber > MAX_NUMBER) {
                    mNumber = MAX_NUMBER;
                }
                //  刷新UI
                refreshUI();
                //  循环发送MSG_INCREMENT的延时消息
                if (mNumber < MAX_NUMBER) {
                    mHandler.sendEmptyMessageDelayed(MSG_INCREMENT, 1000);
                } else {
                    Toast.makeText(HandlerDemoActivity.this, "已达到最大值", Toast.LENGTH_SHORT).show();
                    mHandler.removeMessages(MSG_INCREMENT);
                }
            } else if (msg.what == MSG_DECREMENT) {
                if (--mNumber < MIN_NUMBER) {
                    mNumber = MIN_NUMBER;
                }

                refreshUI();

                if (mNumber > MIN_NUMBER) {
                    mHandler.sendEmptyMessageDelayed(MSG_DECREMENT, 1000);
                } else {
                    mHandler.removeMessages(MSG_DECREMENT);
                    Toast.makeText(HandlerDemoActivity.this, "已达到最小值", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private int mNumber = MIN_NUMBER;

    private TextView mTvNumber;
    private Button mBtnIncrease;
    private Button mBtnDecrease;
    private Button mBtnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);

        mTvNumber = findViewById(R.id.tv_number);
        mBtnIncrease = findViewById(R.id.btn_increase);
        mBtnDecrease = findViewById(R.id.btn_decrease);
        mBtnPause = findViewById(R.id.btn_pause);

        mBtnIncrease.setOnClickListener(v -> {
            //  移除未处理的Message，就不会回调handleMessage了
            mHandler.removeMessages(MSG_INCREMENT);
            mHandler.removeMessages(MSG_DECREMENT);
            //  发送MSG_INCREMENT的Message
            mHandler.sendEmptyMessage(MSG_INCREMENT);
        });

        mBtnDecrease.setOnClickListener(v -> {
            mHandler.removeMessages(MSG_INCREMENT);
            mHandler.removeMessages(MSG_DECREMENT);
            mHandler.sendEmptyMessage(MSG_DECREMENT);
        });

        mBtnPause.setOnClickListener(v -> {
            mHandler.removeMessages(MSG_INCREMENT);
            mHandler.removeMessages(MSG_DECREMENT);
        });

        refreshUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  移除回调，避免内存泄露
        mHandler.removeCallbacksAndMessages(null);
    }

    private void refreshUI() {
        mTvNumber.setText(String.valueOf(mNumber));

        if (mNumber == MIN_NUMBER) {
            mBtnIncrease.setEnabled(true);
            mBtnDecrease.setEnabled(false);
            mBtnPause.setEnabled(false);
        } else if (mNumber == MAX_NUMBER) {
            mBtnIncrease.setEnabled(false);
            mBtnDecrease.setEnabled(true);
            mBtnPause.setEnabled(false);
        } else {
            mBtnIncrease.setEnabled(true);
            mBtnDecrease.setEnabled(true);
            mBtnPause.setEnabled(true);
        }
    }
}