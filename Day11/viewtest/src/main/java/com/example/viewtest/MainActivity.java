package com.example.viewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            将子View设置到id为android.R.id.content的布局（FrameLayout）中

            ViewGroup contentParent = mSubDecor.findViewById(android.R.id.content);
            contentParent.removeAllViews();
            LayoutInflater.from(mContext).inflate(resId, contentParent);
         */

        //  设置contentView的方式1
//        setContentView(R.layout.activity_main);

        //  设置contentView的方式2
//        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//        setContentView(view);

        //  设置contentView的方式3
        TextView tv = new AppCompatAutoCompleteTextView(this);
        tv.setText("Hello");
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(tv, params);


        //  获取Activity的根布局
        View decorView = getWindow().getDecorView();
        Log.d(TAG, "onCreate: " + decorView);
        Log.d(TAG, "onCreate: " + decorView.getClass());
        Log.d(TAG, "onCreate: " + decorView.getClass().getSuperclass());
    }
}