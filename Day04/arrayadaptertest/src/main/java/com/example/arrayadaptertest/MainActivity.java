package com.example.arrayadaptertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  初始化ListView
        ListView listView = findViewById(R.id.lv);
        //  创建List
        List<String> list = Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10");
        //  创建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        //  给ListView设置adapter，显示列表
        listView.setAdapter(adapter);
        //  给List设置行点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了：" + position + "行", Toast.LENGTH_SHORT).show();
            }
        });
    }
}