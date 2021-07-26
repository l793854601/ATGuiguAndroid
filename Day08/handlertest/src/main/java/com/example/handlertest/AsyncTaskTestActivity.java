package com.example.handlertest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskTestActivity extends AppCompatActivity {

    private static final String TAG = "AsyncTaskTest";

    private ProgressDialog mDialog;
    private File mApkFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_test);
    }

    public void onClickDownload(View view) {
        //  创建异步任务
        //  TODO: API 30下，回调onProgressUpdate时，操作UI会引起卡顿，原因不明
        AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {

            /*
                1.主线程，显示提示视图
             */
            @Override
            protected void onPreExecute() {
                //  创建ProgressDialog并显示
                mDialog = new ProgressDialog(AsyncTaskTestActivity.this);
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.show();

                //  初始化mApkFile
                File dir = getFilesDir();
                mApkFile = new File(dir, "app.apk");
                if (mApkFile.exists()) {
                    mApkFile.delete();
                }
            }

            /*
                2.子线程，执行异步任务
             */
            @Override
            protected String doInBackground(String... strings) {
                URL url = null;
                HttpURLConnection connection = null;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    //  1.创建URL对象
                    url = new URL(strings[0]);
                    //  2.获取HttpURLConnection对象
                    connection = (HttpURLConnection) url.openConnection();
                    //  3.设置请求方式、超时时间
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    //  4.连接
                    connection.connect();
                    //  5.获取状态码，200才能继续进行
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //  6.设置dialog最大值（文件大小）
                        int contentLength = connection.getContentLength();
                        runOnUiThread(() -> {
                            mDialog.setMax(contentLength);
                        });

                        //  7.得到InputStream，用于读取下载的内容
                        is = connection.getInputStream();
                        //  8.创建FileOutputStream，用于写入文件
                        fos = new FileOutputStream(mApkFile);
                        //  9.写入文件
                        byte[] data = new byte[10];
                        int length = -1;
                        while ((length = is.read(data)) != -1) {
                            fos.write(data, 0, length);
                            //  10.更新下载进度
                           publishProgress(length);
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //  关闭流
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        //  关闭连接
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
                return mApkFile.getAbsolutePath();
            }

            /*
                3.主线程，更新进度
             */
            @Override
            protected void onProgressUpdate(Integer... values) {
                mDialog.incrementProgressBy(values[0]);
            }

            /*
                4.任务执行完毕时回调
             */
            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                Toast.makeText(AsyncTaskTestActivity.this, "APK下载成功，路径为：" + s, Toast.LENGTH_SHORT).show();
            }

            /*
                任务取消时回调
             */
            @Override
            protected void onCancelled() {
                mDialog.dismiss();
                Toast.makeText(AsyncTaskTestActivity.this, "下载取消", Toast.LENGTH_SHORT).show();
            }
        };

        //  启动异步任务
        String url = "http://a.xzfile.com/apk2/kaolaaichev1.0_downcc.com.apk";
        asyncTask.executeOnExecutor(Executors.newCachedThreadPool(), url);
    }
}