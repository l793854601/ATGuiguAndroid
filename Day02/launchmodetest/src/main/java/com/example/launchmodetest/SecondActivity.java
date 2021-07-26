package com.example.launchmodetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    public SecondActivity() {
        Log.d("TAG", "SecondActivity()");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onDestroy() {
        Log.d("TAG", "onDestroy: ");
        super.onDestroy();
    }

    public void startThird(View v) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void startFirst(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}