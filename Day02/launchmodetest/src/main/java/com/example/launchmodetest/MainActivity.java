package com.example.launchmodetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        Log.d("TAG", "MainActivity()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("TAG", "onNewIntent: ");
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSecond(View v) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    public void startFirst(View v) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}