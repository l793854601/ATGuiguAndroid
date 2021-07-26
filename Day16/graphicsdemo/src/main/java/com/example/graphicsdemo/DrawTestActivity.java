package com.example.graphicsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DrawTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyView view = new MyView(this);
        setContentView(view);
    }
}