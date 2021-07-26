package com.example.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        发送一般广播
     */
    public void onClickSendNormalBroadcast(View view) {
        //  创建Intent
        Intent intent = new Intent();
        //  设置action
        intent.setAction("com.example.broadcastreceivertest.MyReceiver1.action");
        //  设置携带数据
        intent.putExtra("name", "TKM");
        intent.putExtra("age", 20);
        //  发送广播
        sendBroadcast(intent);
    }

    /*
        发送有序广播
     */
    public void onClickSendOrderedBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.example.broadcastreceivertest.OrderedReceiver.action");
        intent.putExtra("name", "Jerry");
        intent.putExtra("age", 12);
        sendOrderedBroadcast(intent, null);
    }
}