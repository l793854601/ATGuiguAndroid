package com.example.graphicsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    测试Bitmap：加载一张图片数据到内存中，都可以封装成一个Bitmap对象
    1：加载资源文件中的图片并显示
    2：加载存储空间中的图片资源并显示
    3：将一个Bitmap对象保存到存储空间中
 */
public class BitmapTestActivity extends AppCompatActivity {

    private ImageView mIvFromResource;
    private ImageView mIvFromStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);

        mIvFromResource = findViewById(R.id.iv_from_resource);
        mIvFromStorage = findViewById(R.id.iv_from_storage);

        loadBitmapFromResource();
        loadBitmapFromStorage();
    }

    /*
        加载资源文件中的图片并显示
     */
    private void loadBitmapFromResource() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        mIvFromResource.setImageBitmap(bitmap);
    }

    private void loadBitmapFromStorage() {
        File rootDir = getFilesDir();
        File logoFile = new File(rootDir, "logo.png");
        if (!logoFile.exists()) {
            Toast.makeText(this, "请先保存", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(logoFile.getAbsolutePath());
        mIvFromStorage.setImageBitmap(bitmap);
    }

    /*
        保存图片到存储空间中
     */
    public void onClickSaveToStorage(View view) {
        FileOutputStream os = null;
        try {
            File rootDir = getFilesDir();
            File logoFile = new File(rootDir, "logo.png");
            os = new FileOutputStream(logoFile);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}