package com.example.datastoragetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*
    测试SQLite数据库村塾
 */
public class DBActivity extends AppCompatActivity {

    private static final String TAG = "DBActivity";

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);
    }

    /*
        创建数据库
     */
    public void onClickCreateDB(View v) {
        mDBHelper = new DBHelper(this, 1);
        //  获取数据库连接，此时才会真正的创建数据库文件
        mDBHelper.getReadableDatabase();
        Toast.makeText(this, "创建数据库", Toast.LENGTH_SHORT).show();
    }

    /*
        更新数据库
     */
    public void onClickUpdateDB(View v) {
//        mDBHelper = new DBHelper(this, 2);
//        mDatabase = mDBHelper.getReadableDatabase();
//        Toast.makeText(this, "创建数据库", Toast.LENGTH_SHORT).show();
    }

    /*
        插入数据
     */
    public void onClickInsert(View v) {
        if (mDBHelper == null) {
            Toast.makeText(this, "请先创建数据库", Toast.LENGTH_SHORT).show();
            return;
        }

        //  1.得到（打开）数据库连接
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        //  2.执行insert into person(name, age) value('Jerry', 14)
        ContentValues values = new ContentValues();
        values.put("name", "Jerry");
        values.put("age", 14);
        //  返回值为插入数据的rowId，-1表示失败
        long id = database.insert("person", null, values);
        //  3.关闭数据库连接
        database.close();
        //  4.提示
        if (id != -1) {
            Toast.makeText(this, "插入数据库成功，id：" + id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "插入数据库失败", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        更新数据
     */
    public void onClickUpdate(View v) {
        if (mDBHelper == null) {
            Toast.makeText(this, "请先创建数据库", Toast.LENGTH_SHORT).show();
            return;
        }

        //  1.得到（打开）数据库连接
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        //  2.执行update person set name='Jack', age = 13 where _id=4
        ContentValues values = new ContentValues();
        values.put("name", "Jack");
        values.put("age", 13);
        int count = database.update("person", values, "_id=?", new String[]{"4"});
        //  3.关闭数据库连接
        database.close();
        //  4.提示
        Toast.makeText(this, "更新了" + count + "条数据", Toast.LENGTH_SHORT).show();
    }

    /*
        删除数据
     */
    public void onClickDelete(View v) {
        if (mDBHelper == null) {
            Toast.makeText(this, "请先创建数据库", Toast.LENGTH_SHORT).show();
            return;
        }

        //  1.得到（打开）数据库连接
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        //  2.执行delete from person where _id=2
        int count = database.delete("person", "_id=?", new String[]{"2"});
        //  3.关闭数据库连接
        database.close();
        //  4.提示
        Toast.makeText(this, "删除了" + count + "条数据", Toast.LENGTH_SHORT).show();
    }

    /*
        查询数据
     */
    public void onClickQuery(View v) {
        if (mDBHelper == null) {
            Toast.makeText(this, "请先创建数据库", Toast.LENGTH_SHORT).show();
            return;
        }

        //  1.得到（打开）数据库连接
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        //  2.执行select * from person
        Cursor cursor = database.query("person", null, null, null, null, null, null);
        //  得到查询结的数量
        int count = cursor.getCount();
        //  通过Cursor查询数据
        while (cursor.moveToNext()) {
            //  _id
            int idIndex = cursor.getColumnIndex("_id");
            int idValue = cursor.getInt(idIndex);
            //  name
            int nameIndex = cursor.getColumnIndex("name");
            String nameValue = cursor.getString(nameIndex);
            //  age
            int ageIndex = cursor.getColumnIndex("age");
            int ageValue = cursor.getInt(ageIndex);
            //  打印数据
            Log.d(TAG, "onClickQuery: " + "_id = " + idValue + ", name = " + nameValue + ", age = " + ageValue);
        }
        //  3.关闭游标、数据库连接
        cursor.close(); //  Cursor也需要关闭
        database.close();
        //  4.提示
        Toast.makeText(this, "查询到" + count + "条数据", Toast.LENGTH_SHORT).show();
    }

    /*
        事务
        执行条sql：
        1.将_id为1的数据，age改为15 update person set age=15 where _id=1
        2.将_id为3的数据，age改为17 update person set age=17 where _id=3
        要保证两条sql都执行成功（要么1、2执行都生效，要么1、2执行都不生效）

        事务处理的步骤：
        try-catch-finally
        1.开启事务（在获取数据库连接后）
        2.标记事务成功（在全部正常执行完后）
        3.结束事务（finally中）
     */
    public void onClickTransaction(View v) {
        if (mDBHelper == null) {
            Toast.makeText(this, "请先创建数据库", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase database = null;
        try {
            //  得到数据库连接
            database = mDBHelper.getReadableDatabase();
            //  开启事务（在获取数据库连接后）
            database.beginTransaction();

            //  update person set age=15 where _id=1
            ContentValues values1 = new ContentValues();
            values1.put("age", 15);
            int count1 = database.update("person", values1, "_id=?", new String[]{"1"});
            Log.d(TAG, "updateCount1 = " + count1);

            //  模拟出现了异常
            Boolean flag = true;
            if (flag) {
                throw new RuntimeException("出异常了！！！");
            }

            //  update person set age=17 where _id=3
            ContentValues values2 = new ContentValues();
            values2.put("age", 17);
            int count2 = database.update("person", values2, "_id=?", new String[]{"3"});
            Log.d(TAG, "updateCount2 = " + count2);

            //  标记事务成功（在全部正常执行完后）
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "异常！！！", Toast.LENGTH_SHORT).show();
        } finally {
            if (database != null && database.isOpen()) {
                //  结束事务（finally中）
                database.endTransaction();
                //  关闭数据库连接
                database.close();
            }
        }
    }
}