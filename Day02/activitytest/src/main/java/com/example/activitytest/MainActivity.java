package com.example.activitytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
    实现一般启动：
        1）定义界面
            布局
            定义Activity类
            在AndroidManifest.xml中配置
            重写onCreate()方法，并加载布局
        2）启动界面
            创建Intent对象（显示）
            通过Intent设置要传递的额外数据
            调用startActivity()方法，启动Activity
            获取Intent对象
            通过Intent读取额外数据

     实现一般返回：
        1）在显示SecondActivity时，MainActivity界面还在，只是被盖住了
        2）关闭当前界面，即可回到上一个Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 100;

    private EditText mEtMessage;
    private Button mBtnStartNormal;
    private Button mBtnStartCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  通过id初始化控件
        mEtMessage = findViewById(R.id.et_message);
        mBtnStartNormal = findViewById(R.id.btn_start_normal);
        mBtnStartCallback = findViewById(R.id.btn_start_call_back);

        //  设置点击监听
        mBtnStartNormal.setOnClickListener(this);
        mBtnStartCallback.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //  从Intent中取出数据
            String message = data.getStringExtra(SecondActivity.MESSAGE_KEY);
            //  设置数据
            mEtMessage.setText(message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_normal: {
                //  创建Intent对象（显示）
                Intent intent = new Intent(this, SecondActivity.class);
                //  通过Intent设置要传递的额外数据（内部是由Map存储的key-value键值对）
                intent.putExtra(SecondActivity.MESSAGE_KEY, mEtMessage.getText().toString());
                //  调用startActivity()方法，启动Activity
                startActivity(intent);
            }
                break;
            case R.id.btn_start_call_back: {
                //  创建Intent对象（显示）
                Intent intent = new Intent(this, SecondActivity.class);
                //  通过Intent设置要传递的额外数据（内部是由Map存储的key-value键值对）
                intent.putExtra(SecondActivity.MESSAGE_KEY, mEtMessage.getText().toString());
                //  startActivityForResult()方法，设置requestCode，并启动Activity
                startActivityForResult(intent, REQUEST_CODE);
            }
            break;
        }
    }
}