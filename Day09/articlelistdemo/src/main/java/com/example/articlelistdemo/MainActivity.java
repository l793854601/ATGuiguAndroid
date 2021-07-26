package com.example.articlelistdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.time.Duration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<Article> mList = new ArrayList<>();
    private ArticleListAdapter mAdapter;

    private ListView mLv;
    private LinearLayout mLlLoading;
    private TextView mTvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = findViewById(R.id.lv);
        mLlLoading = findViewById(R.id.ll_loading);
        mTvEmpty = findViewById(R.id.tv_empty);

        mAdapter = new ArticleListAdapter(this, mList);
        mLv.setAdapter(mAdapter);

        loadArticleList();
    }

    private void loadArticleList() {
        showProgressBar(true);

        String url = "http://api.coincent.cn/portal/article/list/1/20";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            /*
                请求失败时回调
                此方法在子线程中回调，操作UI，需要切换至主线程
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> {
                    refreshEmpty();
                    showProgressBar(false);
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            /*
                请求成功时回调
                此方法在子线程中回调，操作UI，需要切换至主线程
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Result<PagedResult<Article>> result = new Gson().fromJson(json, new TypeToken<Result<PagedResult<Article>>>(){}.getType());
                List<Article> list = result.getData().getContents();
                mList.clear();
                mList.addAll(list);

                runOnUiThread(() -> {
                    mAdapter.notifyDataSetChanged();
                    refreshEmpty();
                    showProgressBar(false);
                });
            }
        });
    }

    private void showProgressBar(boolean show) {
        mLlLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshEmpty() {
        if (mList.size() == 0) {
            mLv.setVisibility(View.GONE);
            mTvEmpty.setVisibility(View.VISIBLE);
        } else {
            mLv.setVisibility(View.VISIBLE);
            mTvEmpty.setVisibility(View.GONE);
        }
    }
}