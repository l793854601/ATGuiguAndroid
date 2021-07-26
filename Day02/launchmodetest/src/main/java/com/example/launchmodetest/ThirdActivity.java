package com.example.launchmodetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ThirdActivity extends AppCompatActivity {

    public ThirdActivity() {
        Log.d("TAG", "ThirdActivity()");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }
}