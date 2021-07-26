package com.example.welcomedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class WelcomeActivity extends AppCompatActivity {

    private RelativeLayout mRlWelcome;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //  初始化View
        mRlWelcome = findViewById(R.id.rl_welcome);

        //  显示动画
        showAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /*
        显示动画
        旋转动画：0~360，视图中心点，2s
        透明度动画：0~1
        缩放动画：0~1
     */
    private void showAnimation() {
        //  加载Animation（AnimationSet）
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_welcome_set);
        //  设置AnimationListener
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //  动画结束，就准备启动MainActivity
                handleAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //  启动动画
        mRlWelcome.setAnimation(animation);
    }

    private void handleAnimationEnd() {
        //  延迟2s，启动GuideActivity
        mHandler.postDelayed(() -> {
            showGuideActivity();
        }, 2000);
    }

    private void showGuideActivity() {
        Intent intent = GuideActivity.newInstance(this, GuideActivity.FIRST_PAGE);
        startActivity(intent);

        //  设置Activity进入/退出的动画
        overridePendingTransition(
                R.anim.animation_activity_right_in,
                R.anim.animation_activity_left_out);

        //  将自己关闭
        finish();
    }
}