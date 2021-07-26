package com.example.contactdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/*
    数据库DAO类
    提供给外部操作数据库的类
 */
public class ContactDAO {

    private static ContactDAO mInstance;

    private ContactDBHelper mDBHelper;

    private ContactDAO(Context context) {
        mDBHelper = new ContactDBHelper(context);
    }

    public static ContactDAO getInstance(Context context) {
        //  使用双重检查构建单例
        if (mInstance == null) {
            synchronized (ContactDAO.class) {
                if (mInstance == null) {
                    mInstance = new ContactDAO(context);
                }
            }
        }
        return mInstance;
    }

    /*
        增加一个联系人
     */
    public Contact insertContact(String name, String number) {
        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactDBHelper.COLUMN_NAME, name);
        values.put(ContactDBHelper.COLUMN_NUMBER, number);

        long id = database.insert(ContactDBHelper.DB_NAME, null, values);
        database.close();

        if (id != -1) {
            Contact contact = new Contact((int) id, name, number);
            return contact;
        }
        return null;
    }

    /*
        根据id，删除联系人
     */
    public int deleteContact(int id) {
        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        int count = database.delete(ContactDBHelper.DB_NAME, ContactDBHelper.COLUMN_ID + "=?", new String[]{ String.valueOf(id) });
        database.close();

        return count;
    }

    /*
        根据id，修改联系人
     */
    public Contact updateContact(int id, String name, String number) {
        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactDBHelper.COLUMN_NAME, name);
        values.put(ContactDBHelper.COLUMN_NUMBER, number);

        int count = database.update(ContactDBHelper.DB_NAME, values, ContactDBHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        database.close();

        if (count > 0) {
            Contact contact = new Contact((int) id, name, number);
            return contact;
        }
        return null;
    }

    /*
        查询所有联系人
     */
    public List<Contact> queryContacts() {
        List<Contact> contacts = new ArrayList<>();

        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        //  orderBy：控制查询顺序
        //  order by _id DESC：按照_id，倒序查询
        Cursor cursor = database.query(ContactDBHelper.DB_NAME, null, null, null, null, null, ContactDBHelper.COLUMN_ID + " DESC");
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(ContactDBHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(ContactDBHelper.COLUMN_NAME);
            int numberIndex = cursor.getColumnIndex(ContactDBHelper.COLUMN_NUMBER);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String number = cursor.getString(numberIndex);

            Contact contact = new Contact(id, name, number);
            contacts.add(contact);
        }

        cursor.close();
        database.close();

        return contacts;
    }
}
