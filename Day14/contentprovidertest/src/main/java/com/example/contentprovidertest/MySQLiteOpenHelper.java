package com.example.contentprovidertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "student";

    private static final int DB_VERSION = 1;

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME + ".db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  建表
        String sql = "create table " + DB_NAME + "(_id integer primary key autoincrement, name varchar)";
        db.execSQL(sql);

        //  插入一些数据，给ContentResolver使用
        db.execSQL("insert into " + DB_NAME + "(name) values ('Tom')");
        db.execSQL("insert into " + DB_NAME + "(name) values ('Jerry')");
        db.execSQL("insert into " + DB_NAME + "(name) values ('Marry')");
        db.execSQL("insert into " + DB_NAME + "(name) values ('Jenny')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertData(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        //  SQLiteDatabase如果已经打开，再次调用getReadableDatabase()会造成数据库递归调用
        //  java.lang.IllegalStateException: getDatabase called recursively
//        getReadableDatabase().insert(DB_NAME, null, values);
        db.insert(DB_NAME, null, null);
    }
}
