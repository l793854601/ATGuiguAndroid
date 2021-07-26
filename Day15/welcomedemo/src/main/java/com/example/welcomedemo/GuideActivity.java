package com.example.welcomedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
    前/后页的切换：
    下一页：
        当前界面：(0, 0) -> (-100%, 0)
        要显示的界面：(100%, 0) -> (0, 0)
    上一页：
        当前界面：(0, 0) -> (100%，0)
        要显示的界面：(-100%, 0) -> (0, 0)
 */
public class GuideActivity extends AppCompatActivity {

    private static final String PAGE_KEY = "PAGE_KEY";

    public static final int FIRST_PAGE = 0;
    public static final int LAST_PAGE = 3;

    //  当前页
    private int mPage = -1;

    private TextView mTv;
    private Button mBtnPrevious;
    private Button mBtnNext;

    /**
     * Intent构造方法
     * @param context
     * @param page 从0开始
     * @return
     */
    public static Intent newInstance(Context context, int page) {
        Intent intent = new Intent(context, GuideActivity.class);
        intent.putExtra(PAGE_KEY, page);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //  初始化当前页
        mPage = getIntent().getIntExtra(PAGE_KEY, FIRST_PAGE);

        //  初始化View
        mTv = findViewById(R.id.tv);
        mBtnPrevious = findViewById(R.id.btn_previous);
        mBtnNext = findViewById(R.id.btn_next);

        //  控件赋值
        mTv.setText(tvTitle());
        mBtnPrevious.setVisibility(btnPreviousVisibility());
        mBtnNext.setText(btnNextTitle());
    }

    private String tvTitle() {
        return String.format("第%d页", mPage + 1);
    }

    private int btnPreviousVisibility() {
        return previousPage() == -1 ? View.GONE : View.VISIBLE;
    }

    private String btnNextTitle() {
        return nextPage() == -1 ? "完成" : "下一页";
    }

    private int previousPage() {
        int previousPage = mPage - 1;
        if (mPage < FIRST_PAGE) {
            mPage = -1;
        }
        return previousPage;
    }

    private int nextPage() {
        int nextPage = mPage + 1;
        if (nextPage > LAST_PAGE) {
            nextPage = -1;
        }
        return nextPage;
    }

    public void onClickPrevious(View view) {
        finish();

        //  设置Activity进入/退出的动画
        overridePendingTransition(
                R.anim.animation_activity_left_in,
                R.anim.animation_activity_right_out);
    }

    public void onClickNext(View view) {
        int nextPage = nextPage();
        if (nextPage == -1) {
            //  跳转MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            //  设置Activity进入/退出的动画
            overridePendingTransition(R.anim.animation_activity_top_in, R.anim.animation_activity_hide);
        } else {
            //  跳转下一个引导页
            Intent intent = GuideActivity.newInstance(this, nextPage);
            startActivity(intent);

            //  设置Activity进入/退出的动画
            overridePendingTransition(
                    R.anim.animation_activity_right_in,
                    R.anim.animation_activity_left_out);
        }
    }
}