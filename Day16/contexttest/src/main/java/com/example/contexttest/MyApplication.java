package com.example.contexttest;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    private String data;

    private static final String TAG = "MyApplication";

    /*
        在应用启动时调用（无论是冷启动还是热启动）
        在任何Activity、Service、BroadcastReceiver（不包括ContentProvider）创建之前调用
        如果重写此方法，需要调用super.onCreate();
        此方法中不应做太多耗时操作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        //  com.example.contexttest.MyApplication@678981b
        Log.d(TAG, "onCreate: " + this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String getTAG() {
        return TAG;
    }
}
