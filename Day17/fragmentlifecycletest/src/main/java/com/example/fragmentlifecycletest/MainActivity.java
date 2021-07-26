package com.example.fragmentlifecycletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FRAGMENT1_TAG = "FRAGMENT1_TAG";
    private static final String FRAGMENT2_TAG = "FRAGMENT2_TAG";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loadFragment1();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public void onClickReplaceFragment2(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        MyFragment2 fragment2 = new MyFragment2();
        //  替换Fragment
        transaction.replace(R.id.fl_fragment_container, fragment2, FRAGMENT2_TAG);
        transaction.commit();
    }

    public void onClickAddFragment2(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //  通过tag，查找是否存在MyFragment1
        Fragment oldFragment = fragmentManager.findFragmentByTag(FRAGMENT1_TAG);
        if (oldFragment != null) {
            //  如果存在，则移除
            transaction.remove(oldFragment);
        }
        //  防止重复添加
        oldFragment = fragmentManager.findFragmentByTag(FRAGMENT2_TAG);
        if (oldFragment == null) {
            //  MyFragment2不存在，再添加
            MyFragment2 fragment2 = new MyFragment2();
            transaction.add(R.id.fl_fragment_container, fragment2, FRAGMENT2_TAG);
        }
        transaction.commitNow();
    }

    public void onClickRemoveFragment2(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentByTag(FRAGMENT2_TAG);
        if (oldFragment != null) {
            //  如果存在，则移除
            transaction.remove(oldFragment);
        }
        transaction.commitNow();
    }

    private void loadFragment1() {
        MyFragment1 fragment1 = new MyFragment1();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment_container, fragment1, FRAGMENT1_TAG)
                .commitNow();
    }
}