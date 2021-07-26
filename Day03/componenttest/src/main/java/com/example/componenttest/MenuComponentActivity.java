package com.example.componenttest;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/*
    菜单Component

    OptionMenu：
    1.如何触发Menu的显示？
        早期Android手机有menu实体按键，点击此按键会弹出OptionMenu
        后期实现onCreateOptionsMenu(Menu menu)方法，点击Activity右上角菜单会弹出OptionMenu
    2.如何向Menu中添加MenuItem？
        1）menu.add()方法，代码添加
        2）定义menu.xml，加载menu文件添加
    3.选择某个MenuItem时如何响应？
        重写onOptionsItemSelected(MenuItem item)方法，在方法中判断点击的是哪个MenuItem

    ContextMenu：
    1.如何触发Menu的显示？
        视图调用setOnCreateContextMenuListener，设置长按监听
        长按某个视图会触发
    2.如何向Menu中添加MenuItem？
        实现onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)方法
    3.选择某个MenuItem时如何响应？
        onContextItemSelected(MenuItem item)，在方法中判断点击的是哪个MenuItem
 */
public class MenuComponentActivity extends AppCompatActivity {

    private Button mBtnShowMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_component);

        mBtnShowMenu = findViewById(R.id.btn_show_menu);
        mBtnShowMenu.setOnCreateContextMenuListener(this);
    }

    /**
     * 创建OptionMenu的回调方法，在此方法中向Menu添加MenuItem
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  编码方式添加
//        menu.add(Menu.NONE, 10, Menu.NONE, "添加");
//        menu.add(Menu.NONE, 20, Menu.NONE, "删除");
        //  加载menu文件方式添加
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 点击OptionMenu某一项的回调方法
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String string = item.getTitle().toString();
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 创建ContextMenu的回调方法，在此方法中向Menu添加MenuItem
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //  代码添加
//        menu.add(Menu.NONE, 10, Menu.NONE, "添加");
//        menu.add(Menu.NONE, 20, Menu.NONE, "删除");
        //  加载menu文件方式添加
        getMenuInflater().inflate(R.menu.option_menu, menu);
    }

    /**
     * 点击ContextMenu某一项的回调方法
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String string = item.getTitle().toString();
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
}
