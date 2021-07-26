package com.example.graphicsdemo;

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

    /*
        测试图片处理
     */
    public void onClickGraphicsTest(View view) {
        Intent intent = new Intent(this, GraphicsTestActivity.class);
        startActivity(intent);
    }

    /*
        测试绘制处理
     */
    public void onClickDrawTest(View view) {
        Intent intent = new Intent(this, DrawTestActivity.class);
        startActivity(intent);
    }
}