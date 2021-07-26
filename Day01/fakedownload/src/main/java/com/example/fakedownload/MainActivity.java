package com.example.fakedownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
    模拟下载：点击Button，弹出Toast，Button标题改变
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  加载布局
        setContentView(R.layout.activity_main);
        //  1.根据id找到对应的Button
        Button button = findViewById(R.id.btn_download);
        //  2.给Button设置点击监听
        button.setOnClickListener(new View.OnClickListener() {
            //  当点击Button时会调用
            @Override
            public void onClick(View v) {
                //  1）弹出Toast
                Toast.makeText(MainActivity.this,
                        getResources().getString(R.string.start_downloading),
                        Toast.LENGTH_SHORT).show();
                //  2）修改Button的text
                button.setText(getResources().getString(R.string.downloading));
            }
        });
    }
}