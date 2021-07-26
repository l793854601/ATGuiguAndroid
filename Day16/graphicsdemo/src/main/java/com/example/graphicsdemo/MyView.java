package com.example.graphicsdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyView extends View {

    //  椭圆
    private ShapeDrawable mShapeDrawable;

    //  用于绘制文本的Paint
    private Paint mPaint;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //  初始化ShapeDrawable
        //  OvalShape：椭圆，如果不指定Shape，默认绘制矩形
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        //  指定颜色
        mShapeDrawable.getPaint().setColor(Color.RED);
        //  指定区域
        mShapeDrawable.setBounds(30, 30, 500, 300);

        //  初始化Paint
        mPaint = new Paint();
        //  设置Paint颜色
        mPaint.setColor(Color.RED);
        //  设置字体大小
        mPaint.setTextSize(60);
        //  设置字体为粗体
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //  设置抗锯齿（使图形更平滑）
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //  画颜色背景
        canvas.drawColor(Color.GREEN);
        //  画椭圆
        mShapeDrawable.draw(canvas);
        //  画文本
        //  x、y：左下角的坐标，具体来说，y是文本基线的纵坐标
        canvas.drawText("Hello world", 30, 400, mPaint);
    }
}
