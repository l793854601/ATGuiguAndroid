package com.example.animationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewAnimationActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        mIv = findViewById(R.id.iv);
    }

    /*
        代码实现ScaleAnimation
     */
    public void onClickCodeScale(View view) {
        //  1.创建动画对象
        //  宽度：0.5倍到1.5倍f，高度：0倍到1倍，缩放的中心点为顶部中心点
        ScaleAnimation animation = new ScaleAnimation(
                0.5f, 1.5f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0);

        //  2.设置动画属性
        //  延迟1s开始
        animation.setStartOffset(1000);
        //  动画时长2s
        animation.setDuration(2000);
        //  动画结束后恢复至动画开始前的状态
        animation.setFillBefore(true);

        //  3.启动动画
        mIv.startAnimation(animation);
    }

    /*
        Xml实现ScaleAnimation
        1.定义动画文件（res->anim文件夹下，根节点为scale）
        2.加载动画文件
        3.启动动画
     */
    public void onClickXmlScale(View view) {
        //  加载动画文件
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_scale);
        //  启动动画
        mIv.startAnimation(animation);
    }

    /*
        代码实现RotateAnimation
        角度：顺时针为正，逆时针为负
     */
    public void onClickCodeRotation(View view) {
        RotateAnimation animation = new RotateAnimation(
                -90, 90,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(5000);
        mIv.startAnimation(animation);
    }

    /*
        Xml实现RotateAnimation
     */
    public void onClickXmlRotation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_rotate);
        mIv.startAnimation(animation);
    }

    /*
        代码实现AlphaAnimation
        完全透明：0
        完全不透明：1
     */
    public void onClickCodeAlpha(View view) {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        mIv.startAnimation(animation);
    }

    /*
        Xml实现AlphaAnimation
     */
    public void onClickXmlAlpha(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha);
        mIv.startAnimation(animation);
    }

    /*
        代码实现TranslateAnimation
        向右移动一个自己的宽度，向下移动一个自己的高度
        持续2s
     */
    public void onClickCodeTranslate(View view) {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1);
        animation.setDuration(2000);
        mIv.startAnimation(animation);
    }

    /*
        Xml实现TranslateAnimation
     */
    public void onClickXmlTranslate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_translate);
        mIv.startAnimation(animation);
    }

    /*
        代码实现AnimationSet
        两个动画是顺序执行：
        1.从透明到不透明，持续2s
        2.旋转360度，持续1s
     */
    public void onClickCodeAnimationSet(View view) {
        //  1.定义AnimationSet
        //  shareInterpolator：set中的动画，是否共享内差值（动画速率变化相关）
        AnimationSet animationSet = new AnimationSet(true);
        //  2.定义AlphaAnimation
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        //  3.定义RotateAnimation
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setStartOffset(alphaAnimation.getDuration());
        rotateAnimation.setDuration(1000);
        //  4.将子动画添加到AnimationSet
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        //  5.启动动画
        mIv.startAnimation(animationSet);
    }

    /*
        Xml实现AnimationSet
     */
    public void onClickXmlAnimationSet(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_set);
        mIv.startAnimation(animation);
    }

    public void onClickAnimationListener(View view) {
        //  创建Animation
        Animation animation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        //  设置动画时间
        animation.setDuration(1000);
        //  设置重复次数，动画执行次数=重复次数+1（本身执行的次数）
        animation.setRepeatCount(Animation.INFINITE);   //  INFINITE：-1，无限循环
        //  设置重复模式
        animation.setRepeatMode(Animation.RESTART);
        //  设置Interpolator
        //  LinearInterpolator：线性变化
        Interpolator interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        //  设
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, "onAnimationRepeat: ");
            }
        });
        //  启动动画
        mIv.startAnimation(animation);
    }
}