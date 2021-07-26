package com.example.startservicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        //  如果Activity关闭，且不需要Service运行了，则关闭Service
//        Intent intent = new Intent(this, FirstService.class);
//        stopService(intent);
    }

    public void onClickStartService(View view) {
        Intent intent = new Intent(this, FirstService.class);
        startService(intent);
    }

    public void onClickStopService(View view) {
        Intent intent = new Intent(this, FirstService.class);
        stopService(intent);
    }

    public void onClickCallServiceMethod(View view) {
        /*
         Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.content.Context.getPackageName()' on a null object reference
            at android.content.ContextWrapper.getPackageName(ContextWrapper.java:149)
            外部不可以直接调用Service的方法
            直接以new的方式创建Service，Service内部的Context为空
            想要调用Service中的方法，必须bindService()方法，通过Binder调用
         */
        FirstService service = new FirstService();
        service.sayHello();
    }
}