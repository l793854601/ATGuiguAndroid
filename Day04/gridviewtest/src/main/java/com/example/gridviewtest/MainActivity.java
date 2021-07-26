package com.example.gridviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<Function> mFunctions;
    private FunctionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.gv);
        mFunctions = Function.getFunctions(this);
        mAdapter = new FunctionAdapter(this, mFunctions);
        mGridView.setAdapter(mAdapter);
    }
}