package com.example.fragmentlifecycletest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*
    Fragment声明周期：
    1.将Fragment添加到Activity中：
        onAttach()
        onCreate()
        onCreateView()
        onViewCreated()
        onActivityCreated()
        onStart()
        onResume()

    2.点击home键，将应用切换至后台：
        onPause()
        onStop()

    3.将应用重新切换至前台：
        onStart()
        onResume()

    5.替换为其他Fragment：
        onPause()
        onStop()
        onDestroyView()
        如果未添加到回退栈（addBackStack）则会继续执行以下方法（原来的Fragment就进入销毁流程）：
        onDestroy()
        onDetach()

    6.点击back键，回到上一个Fragment：
        onViewCreated()
        onStart()
        onResume()

    7.点击back键，退出应用：
        onPause()
        onStop()
        onDestroyView()
        onDestroy()
        onDetach()

 */
public class MyFragment1 extends Fragment {

    private static final String TAG = "MyFragment1";

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    /*
        在此方法中加载布局并返回
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.layout_fragment1, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }
}
