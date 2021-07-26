package com.example.contactdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
    数据库Helper类
    不建议外部直接调用，建议包装到DAO层调用
 */
public class ContactDBHelper extends SQLiteOpenHelper {

    //  数据库名称
    public static final String DB_NAME = "contact";
    //  数据库版本号
    public static final int DB_VERSION = 1;

    //  _id对应的列
    public static final String COLUMN_ID = "_id";
    //  name对应的列
    public static final String COLUMN_NAME = "name";
    //  number对应的列
    public static final String COLUMN_NUMBER = "number";

    //  建表sql
    private static final String CREATE_TABLE = "create table " + DB_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME +" varchar, "
            + COLUMN_NUMBER +" varchar)";

    public ContactDBHelper(@Nullable Context context) {
        super(context, DB_NAME + ".db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  建表
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
