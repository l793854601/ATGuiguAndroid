package com.example.datastoragetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
    SharedPreferences存储测试
 */
public class SPActivity extends AppCompatActivity {

    private EditText mEtKey;
    private EditText mEtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spactivity);

        mEtKey = findViewById(R.id.et_key);
        mEtValue = findViewById(R.id.et_value);
    }

    /*
        SharedPreferences保存数据
     */
    public void onClickSave(View v) {
        //  获取SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("sp_test", Context.MODE_PRIVATE);
        //  得到Editor对象
        SharedPreferences.Editor editor = sp.edit();
        //  存储数据
        String key = mEtKey.getText().toString();
        String value = mEtValue.getText().toString();
        editor.putString("key", key);
        editor.putString("value", value);
        //  提交（不调用则不会真正存储数据）
        boolean ret = editor.commit();    //  同步提交，会返回保存结果
//        editor.apply();   //  异步提交
        //  Toast提示
        Toast.makeText(this, ret ? "保存成功" : "保存失败", Toast.LENGTH_SHORT).show();
    }

    /*
        SharedPreferences读取数据
     */
    public void onClickRead(View v) {
        //  获取SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("sp_test", Context.MODE_PRIVATE);
        //  根据key读取对应的value
        String key = sp.getString("key", "null_key");
        String value = sp.getString("value", "null_value");
        //  显示
        mEtKey.setText(key);
        mEtValue.setText(value);
    }
}