package com.example.newsdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {

    private LinearLayout mLlContainer;
    private TextView mTvTitle;
    private TextView mTvContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_new_content, container, false);
        mLlContainer = contentView.findViewById(R.id.ll_container);
        mTvTitle = contentView.findViewById(R.id.tv_title);
        mTvContent = contentView.findViewById(R.id.tv_content);
        return contentView;
    }

    public void refresh(String title, String content) {
        mLlContainer.setVisibility(View.VISIBLE);
        mTvTitle.setText(title);
        mTvContent.setText(content);
    }
}
