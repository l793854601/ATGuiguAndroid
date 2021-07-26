package com.example.componenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);
        mBtn4 = findViewById(R.id.btn4);

        mBtn1.setOnClickListener(v -> {
            //  常用简单的Component
            Intent intent = new Intent(MainActivity.this, SimpleComponentActivity.class);
            startActivity(intent);
        });

        mBtn2.setOnClickListener(v -> {
            //  菜单Component
            Intent intent = new Intent(MainActivity.this, MenuComponentActivity.class);
            startActivity(intent);
        });

        mBtn3.setOnClickListener(v -> {
            //  进度条Component
            Intent intent = new Intent(MainActivity.this, ProgressComponentActivity.class);
            startActivity(intent);
        });

        mBtn4.setOnClickListener(v -> {
            //  对话框Component
            Intent intent = new Intent(MainActivity.this, DialogComponentActivity.class);
            startActivity(intent);
        });
    }
}