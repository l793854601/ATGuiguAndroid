package com.example.viewlifecycletest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyTextView extends AppCompatTextView {

    private static final String TAG = "MyTextViewTest";

    /*
        使用代码创建时，会调用此构造方法
     */
    public MyTextView(@NonNull @org.jetbrains.annotations.NotNull Context context) {
        super(context);
        Log.d(TAG, "MyTextView(context)");
    }

    /*
        使用布局文件动态创建时，会调用此构造方法
        AttributeSet：在values中定义的自定义属性，可以在布局中使用
     */
    public MyTextView(@NonNull @org.jetbrains.annotations.NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MyTextView(context, attrs)");
    }

    /*
        View的布局加载完毕时回调
        只有通过XML创建布局时，才会回调此方法
        如果是ViewGroup，在此方法之前，子View已经全被添加了，因此可在此方法中获取子View对象
     */
    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFinishInflate: ");
        super.onFinishInflate();
    }

    /*
        View关联到Window时调用
     */
    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow: ");
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure: measuredWidth = " + measuredWidth + ", measuredHeight = " + measuredHeight);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "layout: ");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout: changed = " + changed);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw: ");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
    }
}

