package com.example.datastoragetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        测试SharedPreferences存储
     */
    public void onClickSharedPreferences(View v) {
        Intent intent = new Intent(this, SPActivity.class);
        startActivity(intent);
    }

    /*
        测试内部存储
     */
    public void onClickInternalStorage(View v) {
        Intent intent = new Intent(this, ISActivity.class);
        startActivity(intent);
    }

    /*
        测试外部存储
     */
    public void onClickExternalStorage(View v) {
        Intent intent = new Intent(this, ESActivity.class);
        startActivity(intent);
    }

    /*
        测试Sqlite存储
     */
    public void onClickSQLiteStorage(View v) {
        Intent intent = new Intent(this, DBActivity.class);
        startActivity(intent);
    }

    /*
        测试网络存储
     */
    public void onClickNetworkStorage(View v) {
        Intent intent = new Intent(this, NSActivity.class);
        startActivity(intent);
    }
}