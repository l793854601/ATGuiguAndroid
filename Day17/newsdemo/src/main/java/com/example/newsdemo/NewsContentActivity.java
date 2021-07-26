package com.example.newsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NewsContentActivity extends AppCompatActivity {

    private static final String NEWS_TITLE_KEY = "NEWS_TITLE_KEY";
    private static final String NEWS_CONTENT_KEY = "NEWS_CONTENT_KEY";

    public static void startActivity(Context context, News news) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra(NEWS_TITLE_KEY, news.getTitle());
        intent.putExtra(NEWS_CONTENT_KEY, news.getContent());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        String title = getIntent().getStringExtra(NEWS_TITLE_KEY);
        String content = getIntent().getStringExtra(NEWS_CONTENT_KEY);
        NewsContentFragment fragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_news_content);
        fragment.refresh(title, content);
    }
}