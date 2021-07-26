package com.example.graphicsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/*
    测试Matrix
    通过设置Matrix的scale、rotate、translate等，修改ImageView的Matrix（本质是修改图片，而不是控件本身）
    备注：ImageView的scaleType需要设置为matrix，才会起效果
 */
public class MatrixTestActivity extends AppCompatActivity {

    private EditText mEtScale;
    private EditText mEtRotate;
    private EditText mEtTranslateX;
    private EditText mEtTranslateY;
    private ImageView mIv;

    private Matrix mMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_test);

        mEtScale = findViewById(R.id.et_scale);
        mEtRotate = findViewById(R.id.et_rotate);
        mEtTranslateX = findViewById(R.id.et_translate_x);
        mEtTranslateY = findViewById(R.id.et_translate_y);
        mIv = findViewById(R.id.iv);

        mMatrix = mIv.getImageMatrix();
    }

    /*
        缩放
     */
    public void onClickScale(View view) {
        if (TextUtils.isEmpty(mEtScale.getText())) {
            return;
        }

        float scaleXY = Float.parseFloat(mEtScale.getText().toString());
        //  修改Matrix的scale
        mMatrix.postScale(scaleXY, scaleXY);
        //  设置ImageView的Matrix
        mIv.setImageMatrix(mMatrix);
    }

    /*
        旋转
     */
    public void onClickRotate(View view) {
        if (TextUtils.isEmpty(mEtRotate.getText())) {
            return;
        }

        float degrees = Float.parseFloat(mEtRotate.getText().toString());
        mMatrix.postRotate(degrees);
        mIv.setImageMatrix(mMatrix);
    }

    /*
        平移
     */
    public void onClickTranslate(View view) {
        if (TextUtils.isEmpty(mEtTranslateX.getText()) ||
            TextUtils.isEmpty(mEtTranslateY.getText())) {
            return;
        }

        float dx = Float.parseFloat(mEtTranslateX.getText().toString());
        float dy = Float.parseFloat(mEtTranslateY.getText().toString());
        mMatrix.postTranslate(dx, dy);
        mIv.setImageMatrix(mMatrix);
    }

    /*
        复位
     */
    public void onClickReset(View view) {
        mMatrix.reset();
        mIv.setImageMatrix(mMatrix);
    }
}