package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String SAVE_KEY = "SAVE_KEY";
    private static final String USERNAME_KEY = "USERNAME_KEY";
    private static final String PASSWORD_KEY = "PASSWORD_KEY";

    private SharedPreferences mSp;

    private EditText mEtUsername;
    private EditText mEtPassword;
    private CheckBox mCbSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSp = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);

        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mCbSave = findViewById(R.id.cb_save);
        mCbSave.setOnCheckedChangeListener(this);

        boolean save = mSp.getBoolean(SAVE_KEY, false);
        mCbSave.setChecked(save);
        if (save) {
            //  显示用户名、密码
            String username = mSp.getString(USERNAME_KEY, "");
            String password = mSp.getString(PASSWORD_KEY, "");
            mEtUsername.setText(username);
            mEtPassword.setText(password);
        }
    }

    public void onLoginClick(View v) {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        boolean save = mCbSave.isChecked();
        if (save) {
            //  保存用户名、密码
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(USERNAME_KEY, username)
                    .putString(PASSWORD_KEY, password)
                    .apply();
        }

        Toast.makeText(this, "登录成功：用户名：" + username + "，密码：" + password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //  保存记录用户名密码状态
        mCbSave.setChecked(isChecked);
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean(SAVE_KEY, isChecked).commit();
    }
}