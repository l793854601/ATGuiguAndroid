package com.example.simpleadaptertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  初始化ListView
        ListView listView = findViewById(R.id.lv);
        //  创建List
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icon", android.R.drawable.btn_dialog);
            map.put("name", "name---" + i);
            map.put("content", "content---" + i);
            list.add(map);
        }
        //  map对象key的数组，用于得到对应的value
        String[] from = { "icon", "name", "content" };
        //  item布局文件中的子View的id数组
        int[] to = { R.id.iv_icon, R.id.tv_name, R.id.tv_content };
        //  创建SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item_list, from, to);
        //  设置adapter，显示列表
        listView.setAdapter(adapter);
    }
}