package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/*
    主界面（点击应用图标启动的界面），由AndroidManifest.xml确定
 */
public class MainActivity extends AppCompatActivity {

    /*
        在当前Activity对象创建时自动调用（Activity生命周期）
        回调方法：非手动调用，由系统（在一定条件下）调用，基本都以on开头：onXxx
                 这些方法我们不需要调用，一般都是重写此方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  调用父类的onCreate方法，做一些默认的初始化工作
        super.onCreate(savedInstanceState);
        //  设置窗口要显示的内容视图（界面/布局）
        //  指定布局文件在R所对应的变量，加载布局文件，最终显示到窗口中
        setContentView(R.layout.activity_main);

        //  使用Log打印日志
        Log.i("TAG", "使用i()的信息");
        Log.e("MainActivity", "使用e()的信息");
    }
}