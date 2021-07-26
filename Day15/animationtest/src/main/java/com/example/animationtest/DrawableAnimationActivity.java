package com.example.animationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DrawableAnimationActivity extends AppCompatActivity {

    private ImageView mIv;
    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);

        mIv = findViewById(R.id.iv);
        mAnimationDrawable = (AnimationDrawable) mIv.getBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }

    public void onClickStartAnimation(View view) {
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    public void onClickStopAnimation(View view) {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}