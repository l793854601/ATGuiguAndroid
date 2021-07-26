package com.example.activitylifecycletest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

/*
    1）界面从“死亡” -> “运行”
        创建对象
        onCreate()
        onStart()
        onResume()
    2）界面从“运行” -> “死亡”
        onPause()
        onStop()
        onDestroy()
    3）界面从“运行” -> “停止”
        onPause()
        onStop()
    4）界面从“停止” -> “运行”
        onRestart()
        onStart()
        onResume()
    5）界面从“运行” -> “暂停”
        onPause()
    6）界面从“暂停” -> “运行”
        onResume()
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    
    public MainActivity() {
        Log.d(TAG, "MainActivity()");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLaunch = findViewById(R.id.btn_launch);
        btnLaunch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}