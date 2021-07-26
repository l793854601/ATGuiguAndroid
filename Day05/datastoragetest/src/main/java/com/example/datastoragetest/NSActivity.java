package com.example.datastoragetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NSActivity extends AppCompatActivity {

    private static final String TAG = "NSActivity";

    private TextView mTvResponse;

    //  Volley的请求队列
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsactivity);

        mTvResponse = findViewById(R.id.tv_response);
    }

    /*
        使用HttpUrlConnection提交get请求
        1.显示ProgressDialog
        2.启动子线程
        3.在子线程，发送请求，得到响应数据
            1）得到path
            2）创建URL对象
            3）打开连接，得到HttpUrlConnection对象
            4）设置请求方式、连接超时、读取数据超时
            5）连接服务器
            6）发送请求，得到响应数据
               得到响应码，必须是200才能继续往下进行
               得到InputStream，并读取为String
            7）关闭连接
        4.切换到主线程，显示得到的结果，移除dialog
     */
    public void onClickUrlConnectionGet(View view) {
        //  1.显示ProgressDialog
        ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...");
        //   2.启动子线程
        new Thread(() -> {
            //  3.在子线程，发送请求，得到响应数据
            //  1）得到path
            String path = "http://api.coincent.cn/portal/article/top";
            HttpURLConnection connection = null;
            InputStream is = null;
            ByteArrayOutputStream bos = null;
            try {
                //  2）创建URL对象
                URL url = new URL(path);
                //  3）打开连接，得到HttpUrlConnection对象
                connection = (HttpURLConnection) url.openConnection();
                //  4）设置请求方式、连接超时、读取数据超时
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                //  5）连接服务器
                //  此方法会阻塞当前线程，且可能会抛出异常：SocketTimeoutException
                connection.connect();
                //  6）发送请求，得到响应数据
                //  得到响应码，必须是200才能继续往下进行
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //  得到InputStream，并读取为String
                    is = connection.getInputStream();
                    bos = new ByteArrayOutputStream();
                    byte[] data = new byte[10];
                    int length = -1;
                    while ((length = is.read(data)) != -1) {
                        bos.write(data, 0, length);
                    }
                    String response = bos.toString();
                    //  切换到主线程，显示得到的结果，移除dialog
                    runOnUiThread(() -> {
                        mTvResponse.setText(response);
                    });
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //  关闭流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //  7）关闭连接
                if (connection != null) {
                    connection.disconnect();
                }
                //  关闭dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }).start();

    }

    /*
        使用HttpUrlConnection提交post请求
        1.显示ProgressDialog
        2.启动子线程
        3.在子线程，发送请求，得到响应数据
            1）得到path
            2）创建URL对象
            3）打开连接，得到HttpUrlConnection对象
            4）设置请求方式、连接超时、读取数据超时
            5）得到输出流，设置请求体
            6）连接服务器
            7）发送请求，得到响应数据
               得到响应码，必须是200才能继续往下进行
               得到InputStream，并读取为String
            8）关闭连接
        4.切换到主线程，显示得到的结果，移除dialog
    */
    public void onClickUrlConnectionPost(View view) {
        //  1.显示ProgressDialog
        ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...");
        //  2.启动子线程
        new Thread(() -> {
            //  3.在子线程，发送请求，得到响应数据
            //  1）得到path
            String path = "http://api.coincent.cn/portal/comment";
            URL url = null;
            HttpURLConnection connection = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream bos = null;
            try {
                //  2）创建URL对象
                url = new URL(path);
                //  3）打开连接，得到HttpUrlConnection对象
                connection = (HttpURLConnection) url.openConnection();
                //  设置请求头参数
                connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
                connection.setRequestProperty("Accept","application/json, text/plain, */*");
                //  4）设置请求方式、连接超时、读取数据超时
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                //  构建请求参数
                Map<String, String> map = new HashMap<>();
                map.put("articleId", "234123");
                map.put("commentContent", "文章不错，向大佬学习！");
                JSONObject jsonObject = new JSONObject(map);
                String body = jsonObject.toString();
                //  5）得到输出流，设置请求体
                os = connection.getOutputStream();
                os.write(body.getBytes("utf-8"));
                //  6）连接服务器
                connection.connect();
                //  7）发送请求，得到响应数据
                int responseCode = connection.getResponseCode();
                //  得到响应码，必须是200才能继续往下进行
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //  得到InputStream，并读取为String
                    is = connection.getInputStream();
                    bos = new ByteArrayOutputStream();
                    byte[] data = new byte[10];
                    int length = -1;
                    while ((length = is.read(data)) != -1) {
                        bos.write(data, 0, length);
                    }
                    String response = bos.toString();
                    //  切换到主线程，显示得到的结果，移除dialog
                    runOnUiThread(() -> {
                        mTvResponse.setText(response);
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //  关闭流
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //  8）关闭连接
                if (connection != null) {
                    connection.disconnect();
                }
                //  移除dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }).start();
    }

    /*
        使用HttpClient提交get请求
     */
    public void onClickClientGet(View view) {
        ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...");
        new Thread(() -> {
            String path = "http://api.coincent.cn/portal/article/top";

            //  创建HttpClient对象
            HttpClient client = new DefaultHttpClient();
            //  设置超时
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);
            //  创建请求对象
            HttpGet request = new HttpGet(path);
            HttpResponse response = null;
            try {
                //  执行请求对象，得到响应对象
                response = client.execute(request);
                //  得到响应行
                StatusLine statusLine = response.getStatusLine();
                //  得到状态码
                int statusCode = statusLine.getStatusCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    //  得到响应体
                    HttpEntity entity = response.getEntity();
                    String responseString = EntityUtils.toString(entity);
                    runOnUiThread(() -> {
                        //  切换至主线程，显示请求结果
                        mTvResponse.setText(responseString);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //  断开连接
                client.getConnectionManager().shutdown();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }).start();
    }

    /*
        使用HttpClient提交post请求
     */
    public void onClickClientPost(View view) {
        ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...");
        new Thread(() -> {
            String path = "http://api.coincent.cn/portal/comment";
            //  创建HttpClient对象
            HttpClient client = new DefaultHttpClient();
            //  设置超时
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);

            try {
                //  构建请求参数
                Map<String, Object> map = new HashMap<>();
                map.put("articleId", "234123");
                map.put("commentContent", "文章不错，向大佬学习！");
                JSONObject jsonObject = new JSONObject(map);
                String body = jsonObject.toString();
                //  创建请求头对象
                HttpEntity requestEntity = new StringEntity(body);
                //  创建请求对象
                HttpPost request = new HttpPost(path);
                //  设置请求头信息
                request.setHeader("Content-Type","application/json;charset=UTF-8");
                request.setHeader("Accept-Language","zh-CN,zh;q=0.9");
                request.setHeader("Accept","application/json, text/plain, */*");
                //  设置请求体
                request.setEntity(requestEntity);
                //  执行请求对象，得到响应对象
                HttpResponse response = client.execute(request);
                //  得到响应行
                StatusLine statusLine = response.getStatusLine();
                //  得到状态码
                int statusCode = statusLine.getStatusCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    //  得到响应体
                    HttpEntity responseEntity = response.getEntity();
                    String responseString = EntityUtils.toString(responseEntity);
                    runOnUiThread(() -> {
                        //  切换至主线程，显示请求结果
                        mTvResponse.setText(responseString);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //  断开连接
                client.getConnectionManager().shutdown();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }).start();
    }

    /*
        使用Volley提交post请求
    */
    public void onClickVolleyGet(View view) {
        ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...");
        //  1.创建Request请求对象
        String url = "http://api.coincent.cn/portal/article/top";
        Request request = new StringRequest(url, new Response.Listener<String>() {
            /*
                请求成功时回调
                在主线程回调
             */
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + Thread.currentThread());

                dialog.dismiss();
                mTvResponse.setText(response);
            }
        }, new Response.ErrorListener() {
            /*
                请求失败时回调
                在主线程回调
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + Thread.currentThread());

                dialog.dismiss();
            }
        });
        //  2.获取请求队列
        RequestQueue requestQueue = getRequestQueue();
        //  3.将请求对象添加到请求队列中
        requestQueue.add(request);
    }

    /*
        使用Volley提交post请求
    */
    public void onClickVolleyPost(View view) {
        ProgressDialog dialog = ProgressDialog.show(this, null, "请稍后...");
        //  1.获取请求地址
        String url = "http://api.coincent.cn/portal/comment";
        //  2.构建请求参数
        Map<String, String> map = new HashMap<>();
        map.put("articleId", "234123");
        map.put("commentContent", "文章不错，向大佬学习！");
        JSONObject jsonRequest = new JSONObject(map);
        //  3.使用JsonObjectRequest，创建Request对象
        Request request = new JsonObjectRequest(url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                mTvResponse.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }) {
            /*
                重写该方法，设置请求头
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Accept-Language", "zh-CN,zh;q=0.9");
                headers.put("Accept", "application/json, text/plain, */*");
                return headers;
            }
        };
        //  4.获取请求队列
        RequestQueue requestQueue = getRequestQueue();
        //  5.将Request添加到请求队列中
        requestQueue.add(request);

    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;
    }
}