package com.example.applisttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
    1.分析界面结构，编写布局文件
        1）整体布局：
            LinearLayout
                TextView
                ListView
        2）item布局：
            LinearLayout
                ImageView TextView
    2.使用ListView+BaseAdapter显示所有永盈信息
        1）得到所有应用信息数据对象的集合List<AppInfo>
        2）定义BaseAdapter的实现类：AppInfoAdapter
    3）设置ListView的item点击监听
    4）设置ListView的长按监听
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView mListView;
    private List<AppInfo> mList;
    private AppInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.lv);
        mList = AppInfo.getAppInfos(this);
        mAdapter = new AppInfoAdapter(this, mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppInfo appInfo = mList.get(position);
        Toast.makeText(this, "点击了：" + appInfo.getPackageName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定隐藏此应用吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    mList.remove(position);
                    mAdapter.notifyDataSetChanged();
                })
                .show();
        return true;
    }
}