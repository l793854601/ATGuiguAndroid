package com.example.staticloadfragmenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
    测试静态加载Fragment
    1.定义Fragment的子类，并加载一个布局文件
    2.在布局文件中，通过<fragment>标签指定自定义的Fragment
    3.<fragment>内部必须定义android:id/android:tag（必须指定唯一标识符）
    4.承接Fragment的Activity，必须直接或间接继承自FragmentActivity

    Fragment的视图层次结构会成为宿主的视图层次结构的一部分，或附加到宿主的视图层次结构。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}