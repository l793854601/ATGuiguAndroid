package com.example.dynamicloadfragmenttest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment1 extends Fragment {

    private static final String TAG = "MyFragment1";

    /*
        在此方法中加载布局并返回
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View contentView = inflater.inflate(R.layout.layout_fragment1, container, false);
        return contentView;
    }
}
