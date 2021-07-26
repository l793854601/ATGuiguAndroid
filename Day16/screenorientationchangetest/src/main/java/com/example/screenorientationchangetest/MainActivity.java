package com.example.screenorientationchangetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String SAVED_EDIT_TEXT_VALUE = "SAVED_EDIT_TEXT_VALUE";

    private EditText mEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");

        setContentView(R.layout.activity_main);

        //  设置Activity支持的屏幕方向
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //  获取Activity当前的屏幕方向
//        int orientation = getResources().getConfiguration().orientation;

        mEt = findViewById(R.id.et);

        //  savedInstanceState可能为空，因此需要判空
        if (savedInstanceState != null) {
            //  将保存的String重新赋值给EditText
            mEt.setText(savedInstanceState.getString(SAVED_EDIT_TEXT_VALUE));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
    }

    /*
        在此保存一些必要信息，便于Activity重新启动时，恢复信息
        某些情况下不会调用
        比如：AndroidManifest.xml中配置了android:configChanges="orientation|keyboardHidden|screenSize
        当屏幕方向发生改变时，此方法就不会调用了
     */
    @Override
    protected void onSaveInstanceState(@NonNull @org.jetbrains.annotations.NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState: ");

        //  保存数据
        String string = mEt.getText().toString();
        outState.putString(SAVED_EDIT_TEXT_VALUE, string);
    }

    /*
        配置改变时回调
        需要在AndroidManifest.xml中配置android:configChanges，才会生效
     */
    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "onConfigurationChanged: ");

        int orientation = newConfig.orientation;
        String info = null;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //  横屏，landscape：风景
            info = "横屏";
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //  竖屏，portrait：肖像
            info = "竖屏";
        }
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}