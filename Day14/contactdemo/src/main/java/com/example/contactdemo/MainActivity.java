package com.example.contactdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ListView mLv;

    private List<Contact> mList = new ArrayList<>();

    private ContactListAdapter mAdapter;

    private ContentObserver mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Log.d(TAG, "onChange: ");
            prepareToLoadContacts();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  初始化ListView
        mLv = findViewById(R.id.lv);
        mAdapter = new ContactListAdapter(this, mList);
        mLv.setAdapter(mAdapter);

        //  添加联系人变化监听
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        getContentResolver().registerContentObserver(uri, true, mObserver);

        //  准备读取联系人（先要获取权限）
        prepareToLoadContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  解除注册联系人变化监听
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void prepareToLoadContacts() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            loadContacts();
                        } else {
                            Toast.makeText(MainActivity.this, "权限不足", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
            return;
        }

        mList.clear();

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String number = cursor.getString(numberIndex);
            Contact contact = new Contact(id, name, number);
            mList.add(contact);
        }

        cursor.close();
        mAdapter.notifyDataSetChanged();
    }
}