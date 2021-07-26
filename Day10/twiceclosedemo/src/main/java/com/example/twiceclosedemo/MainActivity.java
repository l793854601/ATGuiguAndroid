package com.example.twiceclosedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/*
    2s之内，连续点击两次back键退出Activity
    实现方式：
    方式1：记算两次点击的时间，计算时间差
    方式2：使用Handler延迟发送消息，重置点击次数
 */
public class MainActivity extends AppCompatActivity {

    private static long TWICE_PRESS_INTERVAL = 2000;

    private long mFirstBackUpTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //  监听back键抬起事件
            if (mFirstBackUpTime == 0) {
                //  第一次点击back，则记录事件
                mFirstBackUpTime = System.currentTimeMillis();
                //  弹出Toast提示
                Toast.makeText(this, "再按一次返回键，退出程序", Toast.LENGTH_SHORT).show();
            } else {
                //  获取当前时间
                long now = System.currentTimeMillis();
                long offset = now - mFirstBackUpTime;
                //  判断两次点击back的时间间隔
                if (offset <= TWICE_PRESS_INTERVAL) {
                    finish();
                } else {
                    mFirstBackUpTime = 0;
                    onKeyUp(keyCode, event);
                }
            }
            //  返回true，表示此事件不会再被传递了
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}