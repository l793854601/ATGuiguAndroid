package com.example.moveimagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/*
    功能描述：
    通过手指一定来拖动图片
    控制图片不能超出屏幕显示区域

    技术点：MotionEvent处理
    对View进行动态定位：layout()方法
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "MainActivity";

    private ImageView mIvIcon;
    private RelativeLayout mRvParent;

    private float mStartRawX = -1;
    private float mStartRawY = -1;

    private int mIvIconTop = -1;
    private int mIvIconLeft = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvIcon = findViewById(R.id.iv_icon);
        mRvParent = findViewById(R.id.rv_root);

        int right = mRvParent.getRight();
        int bottom = mRvParent.getBottom();
        //  此时right、bottom为0，因为View的top、left、bottom、right需要经过测量才能确定
        Log.d(TAG, "right: " + right + ", bottom: " + bottom);

        mIvIcon.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //  获取action
        int action = event.getAction();
        //  判断action类型
        if (action == MotionEvent.ACTION_DOWN) {
            //  手指按下时
            
            //  记录当前位置的x、y
            mStartRawX = event.getRawX();
            mStartRawY = event.getRawY();

            //  记录控件的l、t
            mIvIconTop = mIvIcon.getTop();
            mIvIconLeft = mIvIcon.getLeft();
        } else if (action == MotionEvent.ACTION_MOVE) {
            //  手指移动时

            //  计算x、y偏移量
            int offsetX = (int) (event.getRawX() - mStartRawX);
            int offsetY = (int) (event.getRawY() - mStartRawY);

            //  得到父视图的宽、高
            int parentWidth = mRvParent.getWidth();
            int parentHeight = mRvParent.getHeight();

            //  得到视图的宽、高
            int width = mIvIcon.getWidth();
            int height = mIvIcon.getHeight();

            //  根据offsetX、offsetY，计算视图的t、l、r、b
            int top = mIvIconTop + offsetY;
            int left = mIvIconLeft + offsetX;
            int bottom = top + height;
            int right = left + width;

            //  控制视图t不超过屏幕上边
            if (top < 0) {
                top = 0;
                bottom = height;
            }

            //  控制视图l不超过屏幕左边
            if (left < 0) {
                left = 0;
                right = width;
            }

            //  控制视图r不超过屏幕右边
            if (right > parentWidth) {
                right = parentWidth;
                left = parentWidth - width;
            }

            //  控制视图b不超过屏幕下边
            if (bottom > parentHeight) {
                bottom = parentHeight;
                top = parentHeight - height;
            }

            //  重新布局视图
            mIvIcon.layout(left, top, right, bottom);
        }

        return true;
    }
}