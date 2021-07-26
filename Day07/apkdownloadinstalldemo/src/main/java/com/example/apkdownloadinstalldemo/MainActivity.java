package com.example.apkdownloadinstalldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
    下载apk文件，显示进度
    下载成功后，安装
    apk下载地址：http://a.xzfile.com/apk2/kaolaaichev1.0_downcc.com.apk
    apk存放地址：外部存储应用的根目录即可
    需要给其他App提供访问路径，因此apk必须下载到外部存储
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //  apk下载地址
    private static final String APK_URL = "http://a.xzfile.com/apk2/kaolaaichev1.0_downcc.com.apk";

    //  apk本地名称
    private static final String APK_FILE_NAME = "app.apk";

    //  apk本地文件
    private File mApkFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        //  初始化mApkFile
        File dir = getExternalFilesDir(null);
        Log.d(TAG, "path: " + dir.getAbsolutePath());
        mApkFile = new File(dir, APK_FILE_NAME);
    }

    public void onClickDownloadAndInstall(View view) {
        if (mApkFile.exists()) {
            //  文件已存在，则直接安装
            installApk(mApkFile);
        } else {
            //  文件不存在，则先创建
            downloadApk();
        }
    }

    /*
        下载apk文件
     */
    private void downloadApk() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        //  1.显示dialog
        ProgressDialog dialog = createDownloadDialog();
        dialog.show();

        //  2.下载文件
        new Thread(() -> {
            URL url = null;
            HttpURLConnection connection = null;
            InputStream is = null;
            FileOutputStream fos = null;

            try {
                //  1.创建URL对象
                url = new URL(APK_URL);
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
                    dialog.setMax(contentLength);

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
                        dialog.incrementProgressBy(length);
                    }

                    //  11.切换至主线程，安装apk
                    runOnUiThread(() -> {
                        installApk(mApkFile);
                    });
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
                    //  关闭dialog
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        }).start();
    }

    public void onClickDelete(View view) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mApkFile.exists()) {
            mApkFile.delete();
            Toast.makeText(this, "apk已删除", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "apk不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        安装apk
     */
    private void installApk(File file) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "下载完成，准备安装", Toast.LENGTH_SHORT).show();

        /*
            Parse error when parsing manifest. Discontinuing installation.
            出现报错的原因之一：访问路径权限不够（apk下载到内部存储，就会发生这个现象）
            其他App是不允许访问此App的内部存储的
            解决方案：使用Runtime，改变访问路径权限
         */
//        try {
//            String strPath = file.getPath();
//            String cmd = "chmod 777 " + strPath;
//            Runtime.getRuntime().exec(cmd);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "权限加载失败", Toast.LENGTH_SHORT).show();
//        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //  构建Uri
        Uri contentUri = null;

        //  判断系统版本
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            //  Android 7.0以下，外部App可直接通过file://xxx的方式访问此App的数据
            contentUri = Uri.fromFile(file);
        } else {
            //  Android 7.0及以上，需要提供FileProvider给外部App访问此App内部数据
            contentUri = FileProvider.getUriForFile(this, getPackageName(), file);
            //  赋予临时权限给Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

        //  Android 8.0新特性：未知来源应用权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PackageManager packageManager = getPackageManager();
            if (packageManager.canRequestPackageInstalls()) {
                startActivity(intent);
            } else {
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent settingIntent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                startActivity(settingIntent);
            }
        } else {
            startActivity(intent);
        }

    }

    /*
        创建进度ProgressDialog
     */
    private ProgressDialog createDownloadDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return dialog;
    }
}