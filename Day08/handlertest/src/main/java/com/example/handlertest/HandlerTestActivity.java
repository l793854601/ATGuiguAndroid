package com.example.handlertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HandlerTestActivity extends AppCompatActivity {

    private static final int MSG_SET_TEXT = 1;

    private static final int MSG_REQUEST_FAILED = 2;

    private static final int MSG_HIDE_PROGRESS = 3;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        /*
            重写handleMessage，处理Message
         */
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_SET_TEXT) {
                String response = (String) msg.obj;
                mTvResponse.setText(response);
                mPb.setVisibility(View.INVISIBLE);
            } else if (msg.what == MSG_REQUEST_FAILED) {
                mPb.setVisibility(View.INVISIBLE);
                Toast.makeText(HandlerTestActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == MSG_HIDE_PROGRESS) {
                if (mPb.getVisibility() == View.VISIBLE) {
                    mPb.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private ProgressBar mPb;
    private TextView mTvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);

        mPb = findViewById(R.id.pb);
        mTvResponse = findViewById(R.id.tv_response);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  移除回调，避免内存泄露
        mHandler.removeCallbacksAndMessages(null);
    }

    /*
        不使用Handler实现以下功能：
        1.主线程，显示ProgressBar
        2.子线程，请求数据并得到响应
        3.主线程，显示数据并隐藏ProgressBar
     */
    public void onClickGetSubmit1(View view) {
        //  1.主线程，显示ProgressBar
        mPb.setVisibility(View.VISIBLE);
        //  2.子线程，请求数据并得到响应
        new Thread(() -> {
            try {
                String path = "http://api.coincent.cn/portal/article/top";
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int length = -1;
                    byte[] data = new byte[10];
                    while ((length = (is.read(data))) != -1) {
                        bos.write(data, 0, length);
                    }

                    String response = bos.toString();
                    //  3.主线程，显示数据并隐藏ProgressBar
                    runOnUiThread(() -> {
                        mTvResponse.setText(response);
                        mPb.setVisibility(View.INVISIBLE);
                    });

                    is.close();
                    bos.close();
                    connection.disconnect();
                } else {
                    runOnUiThread(() -> {
                        mPb.setVisibility(View.INVISIBLE);
                        Toast.makeText(HandlerTestActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> {
                    if (mPb.getVisibility() == View.VISIBLE) {
                        mPb.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    /*
        使用Handler实现以下功能：
        1.主线程，显示ProgressBar
        2.子线程，请求数据并得到响应
        3.主线程，显示数据并隐藏ProgressBar
    */
    public void onClickGetSubmit2(View view) {
        //  1.主线程，显示ProgressBar
        mPb.setVisibility(View.VISIBLE);
        //  2.子线程，请求数据并得到响应
        new Thread(() -> {
            try {
                String path = "http://api.coincent.cn/portal/article/top";
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int length = -1;
                    byte[] data = new byte[10];
                    while ((length = (is.read(data))) != -1) {
                        bos.write(data, 0, length);
                    }

                    String response = bos.toString();
                    //  3.主线程，显示数据并隐藏ProgressBar
                    //  创建Message对象
                    Message msg = Message.obtain();
                    //  设置what
                    msg.what = MSG_SET_TEXT;
                    //  设置obj
                    msg.obj = response;
                    //  使用Handler发送Message
                    mHandler.sendMessage(msg);

                    is.close();
                    bos.close();
                    connection.disconnect();
                } else {
                    Message msg = Message.obtain();
                    msg.what = MSG_REQUEST_FAILED;
                    mHandler.sendMessage(msg);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Message msg = Message.obtain();
                msg.what = MSG_HIDE_PROGRESS;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

}