package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
    界面二
 */
public class SecondActivity extends AppCompatActivity {

    public static final String MESSAGE_KEY = "MESSAGE_KEY";

    private EditText mEtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mEtMessage = findViewById(R.id.et_message);

        //  获取Intent对象
        Intent intent = getIntent();
        //  通过Intent读取额外数据
        String message = intent.getStringExtra(MESSAGE_KEY);
        //  设置数据
        mEtMessage.setText(message);
    }

    public void backNormal(View view) {
        //  关闭Activity
        finish();
    }

    public void backCallback(View view) {
        //  创建Intent对象
        Intent intent = new Intent();
        //  设置传递参数
        intent.putExtra(MESSAGE_KEY, mEtMessage.getText().toString());
        //  设置回调结果
        setResult(RESULT_OK, intent);
        //  关闭Activity
        finish();
    }
}