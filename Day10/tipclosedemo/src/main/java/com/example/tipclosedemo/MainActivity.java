package com.example.tipclosedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;

/*
    点击back键，弹出提示框确认是否退出？点击确定，再退出
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("是否确定退出？")
                    .setNegativeButton("取消 ", null)
                    .setPositiveButton("确定", (dialog, which) -> {
                        finish();
                    })
                    .show();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}