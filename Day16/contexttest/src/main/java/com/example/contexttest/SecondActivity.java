package com.example.contexttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        MyApplication application = (MyApplication) getApplicationContext();
        Log.d(TAG, "onCreate: " + application.getData());

        //  Toast可以正常弹出
        Toast.makeText(application, "Make a toast", Toast.LENGTH_SHORT).show();
    }

    public void onClickShowDialog(View view) {
        Context context = getApplicationContext();

        /*
             Unable to add window -- token null is not valid; is your activity running?
             必须使用Activity作为Context，来显示Dialog
         */
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确定要退出应用吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null)
                .show();
    }
}