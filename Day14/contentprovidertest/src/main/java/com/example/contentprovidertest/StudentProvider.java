package com.example.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class StudentProvider extends ContentProvider {

    private static final String TAG = "StudentProvider";

    /*
        用来存放合法的Uri的容器（匹配Uri）
        code：如果没有匹配，则返回的值，一般写为UriMatcher.NO_MATCH
     */
    private static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
        content://com.example.contentprovidertest.studentprovider/student
        匹配上述Uri成功时，UriMatch返回的code
     */
    private static final int MATCH_ALL_USER_CODE = 1;

    /*
        content://com.example.contentprovidertest.studentprovider/student/3
        匹配上述Uri成功时，UriMatch返回的code
     */
    private static final int MATCH_USER_CODE_BY_ID = 2;

    //  在UriMatcher中保存一些合法的Uri
    static {

        //  content://com.example.contentprovidertest.studentprovider/student   查询整张表数据
        sMatcher.addURI("com.example.contentprovidertest.studentprovider", "student", MATCH_ALL_USER_CODE);
        //  content://com.example.contentprovidertest.studentprovider/student/3 根据id查询表中数据
        sMatcher.addURI("com.example.contentprovidertest.studentprovider", "student/#", MATCH_USER_CODE_BY_ID);  //  #表示匹配任意路径
    }

    //  数据库操作类
    private MySQLiteOpenHelper mDBHelper = null;

    /*
        构造方法，由系统调用
        ContentProvider必须在AndroidManifest.xml中声明
     */
    public StudentProvider() {
        Log.d(TAG, "StudentProvider: ");
    }

    /*
        应用启动时调用（已经在AndroidManifest.xml中声明），一般在此做初始化操作
        此方法在主线程中执行，因此不要在内部执行太冗余的逻辑
        返回true，表示ContentProvider已经成功被加载了
     */
    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ");
        //  在此初始化数据库操作类
        mDBHelper = new MySQLiteOpenHelper(getContext());
        return true;
    }

    /*
        可传入的uri
        content://com.example.contentprovidertest.studentprovider/student/3：根据id查询
        content://com.example.contentprovidertest.studentprovider/student：不根据id查询（查询所有）
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: ");

        //  query方法中SQLiteDatabase、Cursor不能关闭，否则ContentResolver就无法查询

        //  获取数据库连接对象
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        //  1.匹配uri，返回code
        int code = sMatcher.match(uri);
        //  如果code合法，则进行查询
        if (code == MATCH_ALL_USER_CODE) {
            //  不根据id查询
            Cursor cursor = database.query("student", projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        } else if (code == MATCH_USER_CODE_BY_ID) {
            //  根据id查询
            //  1.得到id
            long id = ContentUris.parseId(uri);
            if (id == -1) {
                //  如果id为-1，则抛出异常
                throw new IllegalArgumentException("URI不合法");
            }
            //  返回根据id查询的Cursor
            Cursor cursor = database.query("student", projection, "_id=?", new String[]{String.valueOf(id)}, null, null, sortOrder);
            return cursor;
        } else {
            //  如果code不合法，则抛出异常
            throw new IllegalArgumentException("URI不合法");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType: ");
        return null;
    }

    /*
        可传入的uri
        content://com.example.contentprovidertest.studentprovider/student：插入
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: ");
        SQLiteDatabase database = null;
        try {
            database = mDBHelper.getReadableDatabase();
            int code = sMatcher.match(uri);
            if (code == MATCH_ALL_USER_CODE) {
                //  插入数据，返回id
                long newId = database.insert("student", null, values);
                //  将id插入到uri
                uri = ContentUris.withAppendedId(uri, newId);
                //  返回
                return uri;
            } else {
                //  如果code不合法，则抛出异常
                throw new IllegalArgumentException("URI不合法");
            }
        } finally {
            //  关闭数据库连接
            if (database != null) {
                database.close();
            }
        }
    }

    /*
        可传入的uri
        content://com.example.contentprovidertest.studentprovider/student/3：根据id删除
        content://com.example.contentprovidertest.studentprovider/student：不根据id删除
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete: ");
        SQLiteDatabase database = null;
        int rows = -1;
        try {
            database = mDBHelper.getReadableDatabase();
            int code = sMatcher.match(uri);
            if (code == MATCH_ALL_USER_CODE) {
                rows = database.delete("student", selection, selectionArgs);
            } else if (code == MATCH_USER_CODE_BY_ID) {
                long id = ContentUris.parseId(uri);
                rows = database.delete("student", "_id=?", new String[] { String.valueOf(id) });
            }
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return rows;
    }

    /*
        可传入的uri
        content://com.example.contentprovidertest.studentprovider/student/3：根据id更新
        content://com.example.contentprovidertest.studentprovider/student：不根据id更新
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: ");

        SQLiteDatabase database = null;
        int rows = -1;
        try {
            database = mDBHelper.getReadableDatabase();
            int code = sMatcher.match(uri);
            if (code == MATCH_ALL_USER_CODE) {
                rows = database.update("student", values, selection, selectionArgs);
            } else if (code == MATCH_USER_CODE_BY_ID) {
                long id = ContentUris.parseId(uri);
                rows = database.update("student", values, "_id=?", new String[] { String.valueOf(id) });
            }
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return rows;
    }
}
