package com.example.layouttest;

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

    public void showLinearLayout(View v) {
        Intent intent = new Intent(this, LineaLayoutActivity.class);
        startActivity(intent);
    }

    public void showRelativeLayout(View v) {
        Intent intent = new Intent(this, RelativeLayoutActivity.class);
        startActivity(intent);
    }

    public void showFrameLayout(View v) {
        Intent intent = new Intent(this, FrameLayoutActivity.class);
        startActivity(intent);
    }
}