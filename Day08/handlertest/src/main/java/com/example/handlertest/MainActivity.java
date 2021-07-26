package com.example.handlertest;

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

    public void onClickHandlerTest(View view) {
        Intent intent = new Intent(this, HandlerTestActivity.class);
        startActivity(intent);
    }

    public void onClickHandlerDemo(View view) {
        Intent intent = new Intent(this, HandlerDemoActivity.class);
        startActivity(intent);
    }

    public void onClickAsyncTaskTest(View view) {
        Intent intent = new Intent(this, AsyncTaskTestActivity.class);
        startActivity(intent);
    }
}