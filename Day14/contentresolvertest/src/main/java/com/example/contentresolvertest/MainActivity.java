package com.example.contentresolvertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        插入
     */
    public void onClickInsert(View view) {
        //  得到ContentResolver
        ContentResolver contentResolver = getContentResolver();
        //  创建Uri
        Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student");
        //  插入数据
        ContentValues values = new ContentValues();
        values.put("name", "Jessica");
        contentResolver.insert(uri, values);
    }

    /*
        修改
     */
    public void onClickUpdate(View view) {
        ContentResolver contentResolver = getContentResolver();

        //  根据id修改
        {
            Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student/2");
            ContentValues values = new ContentValues();
            values.put("name", "Jerry");
            contentResolver.update(uri, values, null, null);
        }

        //  不根据id修改，根据条件修改
        {
            Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student");
            ContentValues values = new ContentValues();
            values.put("name", "Linda");
            contentResolver.update(uri, values, "name=?", new String[] { "Marry" });
        }
    }

    /*
        删除
     */
    public void onClickDelete(View view) {
        ContentResolver contentResolver = getContentResolver();

        //  根据id删除
        {
            Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student/5");
            contentResolver.delete(uri, null, null);
        }

        //  不根据id删除，根据条件删除
        {
            Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student");
            contentResolver.delete(uri, "name=?", new String[] { "Jessica" });
        }
    }

    /*
        查询
     */
    public void onClickQuery(View view) {
        //  得到ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        //  创建Uri
        //  根据id查询
//        Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student/1");
        //  查询整张表
        Uri uri = Uri.parse("content://com.example.contentprovidertest.studentprovider/student");

        //  查询
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //  Cursor可能为空，需要做判空处理，避免空指针异常
        if (cursor == null) {
            Log.d(TAG, "onClickQuery: 查询结果为空");
            return;
        }
        //  遍历查询结果
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");

            int idValue = cursor.getInt(idIndex);
            String nameValue = cursor.getString(nameIndex);

            Log.d(TAG, "onClickQuery: id = " + idValue + ", name = " + nameValue);
        }
        //  关闭cursor
        cursor.close();
    }
}