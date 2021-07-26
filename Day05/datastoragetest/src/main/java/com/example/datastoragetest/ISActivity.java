package com.example.datastoragetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
    手机内部存储测试
 */
public class ISActivity extends AppCompatActivity {

    private ImageView mIvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isactivity);

        mIvIcon = findViewById(R.id.iv_icon);
    }

    /*
        将文件保存至手机内部存储
     */
    public void onClickSave(View v) {
        //  InputStream用于读取文件
        InputStream inputStream = null;
        //  OutputStream用于写入文件
        OutputStream outputStream = null;
        try {
            //  获取AssetManager对象
            AssetManager assetManager = getAssets();
            //  读取assets下的文件
            inputStream = assetManager.open("GTR.jpg");
            //  得到外部存储对应的OutputStream（/data/data/包名/files/GTR.jpg）
            outputStream = openFileOutput("GTR.jpg", Context.MODE_PRIVATE);
            //  读写的过程
            byte[] buffer = new byte[20];
            int length = -1;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //  InputStream、OutputStream使用完毕记得关闭
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
        从手机内部存储读取文件
     */
    public void onClickRead(View v) {
        //  获取内部存储文件夹：/data/data/包名/files
        File filesDir = getFilesDir();
        //  获取内部存储文件夹下的文件
        File imageFile = new File(filesDir, "GTR.jpg");
        //  判断文件是否存在
        if (!imageFile.exists()) {
            Toast.makeText(this, "文件不存在，请先保存", Toast.LENGTH_SHORT).show();
            return;
        }
        //  获取文件路径
        String imagePath = imageFile.getPath();
        //  使用BitmapFactory，生产Bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        //  显示图片
        mIvIcon.setImageBitmap(bitmap);
    }
}