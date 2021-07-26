package com.example.broadcastreceivertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

/*
    注册广播接收器的方式：
    1.静态注册：AndroidManifest.xml中配置
    2.动态注册：硬编码

    动态注册/解除注册：必须成对出现，避免出现内存泄露

    备注：
    动态注册的广播接收者，相比静态注册的广播接收者，优先级要高
    Android8.0后，系统会对AndroidManifest.xml中注册的隐式广播接受者加以限制，建议使用动态注册
 */
public class MainActivity extends AppCompatActivity {

    private MyReceiver2 mReceiver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //  避免出现内存泄露
        if (mReceiver2 != null) {
            unregisterReceiver(mReceiver2);
            mReceiver2 = null;
        }
    }

    /*
            动态注册广播接收器
         */
    public void onClickRegisterReceiver(View view) {
        if (mReceiver2 == null) {
            //  创建Receiver对象
            mReceiver2 = new MyReceiver2();
            //  创建IntentFilter对象
            String action = "com.example.broadcastreceivertest.MyReceiver1.action";
            IntentFilter intentFilter = new IntentFilter(action);
            //  注册Receiver
            registerReceiver(mReceiver2, intentFilter);
        }
    }

    /*
        动态解除注册广播接收器
     */
    public void onClickUnregisterReceiver(View view) {
        if (mReceiver2 != null) {
            //  解除注册Receiver
            unregisterReceiver(mReceiver2);
            //  将Receiver对象置为null
            mReceiver2 = null;
        }
    }
}