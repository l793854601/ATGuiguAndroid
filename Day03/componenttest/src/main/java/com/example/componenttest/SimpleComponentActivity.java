package com.example.componenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
    常用简单的Component
 */
public class SimpleComponentActivity extends AppCompatActivity {

    private TextView mTvSimpleMessage;

    private EditText mEtSimpleNumber;

    private Button mBtnSimpleSubmit;

    private ImageView mIvSimplePlay;

    private CheckBox mCbBasketball;
    private CheckBox mCbFootball;
    private CheckBox mCbPingPang;

    private RadioGroup mRgSimpleGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_component);

        //  1.TextView
        mTvSimpleMessage = findViewById(R.id.tv_simple_message);
        mTvSimpleMessage.setOnClickListener(v -> {
            mTvSimpleMessage.setText("尚硅谷0712NB");
        });

        //  2.EditText
        mEtSimpleNumber = findViewById(R.id.et_simple_number);

        //  3.Button
        mBtnSimpleSubmit = findViewById(R.id.btn_simple_submit);
        mBtnSimpleSubmit.setOnClickListener(v -> {
            String number = mEtSimpleNumber.getText().toString();
            Toast.makeText(SimpleComponentActivity.this, number, Toast.LENGTH_SHORT).show();
        });

        //  4.ImageView
        mIvSimplePlay = findViewById(R.id.iv_simple_play);
        mIvSimplePlay.setOnClickListener(V -> {
            //  设置背景图片
            mIvSimplePlay.setBackgroundResource(android.R.drawable.alert_light_frame);
            //  设置前景图片
            mIvSimplePlay.setImageResource(android.R.drawable.ic_media_pause);
        });

        //  5.CheckBox
        mCbBasketball = findViewById(R.id.cb_simple_basketball);
        mCbBasketball.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SimpleComponentActivity.this, "选中了篮球", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SimpleComponentActivity.this, "取消选中了篮球", Toast.LENGTH_SHORT).show();
            }
        });

        mCbFootball = findViewById(R.id.cb_simple_football);
        mCbFootball.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SimpleComponentActivity.this, "选中了足球", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SimpleComponentActivity.this, "取消选中了足球", Toast.LENGTH_SHORT).show();
            }
        });

        mCbPingPang = findViewById(R.id.cb_simple_ping_pang);
        mCbPingPang.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SimpleComponentActivity.this, "选中了乒乓球", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SimpleComponentActivity.this, "取消选中了乒乓球", Toast.LENGTH_SHORT).show();
            }
        });

        //  6.RadioGroup/RadioButton
        mRgSimpleGender = findViewById(R.id.rg_simple_gender);
        mRgSimpleGender.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            //  找到选中的RadioButton
            RadioButton rb = findViewById(checkedId);
            //  得到text
            String s = rb.getText().toString();
            //  显示Toast
            Toast.makeText(SimpleComponentActivity.this, s, Toast.LENGTH_SHORT).show();
        });
    }

    public void onConfirmClick(View v) {
        List<String> balls = new ArrayList<>();
        if (mCbBasketball.isChecked()) balls.add("篮球");
        if (mCbFootball.isChecked()) balls.add("足球");
        if (mCbPingPang.isChecked()) balls.add("乒乓球");

        if (balls.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("选择了：");
            for (int i = 0; i < balls.size(); i++) {
                sb.append(balls.get(i));
                if (i < balls.size() - 1) {
                    sb.append(", ");
                }
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未选中任何球类", Toast.LENGTH_SHORT).show();
        }
    }
}