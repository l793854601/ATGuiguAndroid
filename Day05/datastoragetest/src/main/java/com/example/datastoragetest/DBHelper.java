package com.example.datastoragetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/*
    数据库操作的帮助类
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public DBHelper(@Nullable Context context, int version) {
        super(context, "atguigu.db", null, version);
    }

    /*
        什么时候调用？
            当数据库文件创建时调用（1次）
        在此方法中做什么？
            建表
            插入一些初始化数据
        什么时候创建数据库文件？
            1）数据库文件不存在
            2）连接数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");

        //  建表
        String sql = "create table person("
                + "_id integer primary key autoincrement, "
                + "name varchar, "
                + "age int)";
        db.execSQL(sql);

        //  插入一些初始化数据
        db.execSQL("insert into person (name, age) values ('Tom1', 11)");
        db.execSQL("insert into person (name, age) values ('Tom2', 12)");
        db.execSQL("insert into person (name, age) values ('Tom3', 13)");
    }

    /*
        当传入的版本号大于数据库的版本号时调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: " + "oldVersion = " + oldVersion + ", newVersion = " + newVersion);
    }
}
