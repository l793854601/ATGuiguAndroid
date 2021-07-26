package com.example.animationtest;

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

    public void onClickViewAnimation(View view) {
        Intent intent = new Intent(this, ViewAnimationActivity.class);
        startActivity(intent);
    }

    public void onClickDrawableAnimation(View view) {
        Intent intent = new Intent(this, DrawableAnimationActivity.class);
        startActivity(intent);
    }
}