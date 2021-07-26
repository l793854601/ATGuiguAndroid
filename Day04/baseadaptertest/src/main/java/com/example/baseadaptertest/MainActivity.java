package com.example.baseadaptertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  初始化ListView
        ListView listView = findViewById(R.id.lv);
        //  创建List
        List<ShopInfo> list = ShopInfo.shops();
        //  创建Adapter
        MyAdapter adapter = new MyAdapter(this, list);
        //  设置Adapter，显示列表
        listView.setAdapter(adapter);
    }
}