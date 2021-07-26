package com.example.graphicsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GraphicsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_test);
    }

    /*
        测试Bitmap
     */
    public void onClickBitmapTest(View view) {
        Intent intent = new Intent(this, BitmapTestActivity.class);
        startActivity(intent);
    }

    /*
        测试Matrix
     */
    public void onClickMatrixTest(View view) {
        Intent intent = new Intent(this, MatrixTestActivity.class);
        startActivity(intent);
    }
}