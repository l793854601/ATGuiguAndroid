package com.example.contexttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  com.example.contexttest.MyApplication@678981b
        MyApplication application = (MyApplication) getApplication();
        //  com.example.contexttest.MyApplication@678981b
        Context context = getApplicationContext();
        Log.d(TAG, "onCreate: application = " + application +
                ", context = " + context +
                ", equals = " + (context == application));

        application.setData("hello world");

        findViewById(R.id.tv).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }
}