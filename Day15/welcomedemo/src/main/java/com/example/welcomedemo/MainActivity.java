package com.example.welcomedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEt = findViewById(R.id.et);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(
                R.anim.animation_show,
                R.anim.animation_bottom_out);
    }

    public void onClickLogin(View view) {
        String username = mEt.getText().toString();
        if (TextUtils.isEmpty(username)) {
            //  EditText执行左右抖动动画
            shakeEditText();
            return;
        }

        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    /*
        EditText执行左右抖动动画
     */
    private void shakeEditText() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_view_shake);
        mEt.startAnimation(animation);
    }
}