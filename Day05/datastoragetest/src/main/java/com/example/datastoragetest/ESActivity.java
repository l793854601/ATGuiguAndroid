package com.example.datastoragetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
    手机外部存储测试
 */
public class ESActivity extends AppCompatActivity {

    EditText mEtName;
    EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esactivity);

        mEtName = findViewById(R.id.et_name);
        mEtContent = findViewById(R.id.et_content);
    }

    public void onClickSave(View v) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = mEtName.getText().toString();
        String fileContent = mEtContent.getText().toString();
        if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(fileContent)) {
            return;
        }

        //  获取外部存储文件夹
        File externalStorageFileDir = getExternalFilesDir(null);
        File file = new File(externalStorageFileDir, fileName + ".txt");
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            byte[] data = fileContent.getBytes();
            fos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onClickRead(View v) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = mEtName.getText().toString();
        File externalStorageFileDir = getExternalFilesDir(null);
        File file = new File(externalStorageFileDir, fileName + ".txt");

        FileInputStream fis = null;
        InputStreamReader reader = null;

        try {
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis);

            int length = -1;
            char[] data = new char[10];
            StringBuffer sb = new StringBuffer();

            while ((length = reader.read(data)) != -1) {
                sb.append(data, 0, length);
            }

            String content = sb.toString();
            mEtContent.setText(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}