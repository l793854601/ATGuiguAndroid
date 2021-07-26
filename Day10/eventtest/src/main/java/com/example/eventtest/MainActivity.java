package com.example.eventtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  设置点击监听
        findViewById(R.id.btn_motion_event_test).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MotionEventTestActivity.class);
            startActivity(intent);
        });

        //  设置长按监听
        findViewById(R.id.btn_key_event_test).setOnLongClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KeyEventTestActivity.class);
            startActivity(intent);
            return true;
        });
    }
}