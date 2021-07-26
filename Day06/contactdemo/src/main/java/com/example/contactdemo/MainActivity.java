package com.example.contactdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD = 10;
    private static final int REQUEST_EDIT = 11;

    private int mSelectedIndex = -1;
    private List<Contact> mList;
    private ContactListAdapter mAdapter;

    private ListView mLv;
    private TextView mtvTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = findViewById(R.id.lv);
        mtvTip = findViewById(R.id.tv_tip);

        mList = new ArrayList<>();
        mAdapter = new ContactListAdapter(this, mList);

        mLv.setAdapter(mAdapter);
        mLv.setOnCreateContextMenuListener(this);

        loadContacts();
        refreshListTip();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_long_pressed, menu);

        //  在此可取到长按的是哪一行
        if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            mSelectedIndex = info.position;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                updateContact();
                break;
            case R.id.delete:
                deleteContact();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            //  添加联系人成功
            Contact contact = (Contact) data.getSerializableExtra(UpdateContactActivity.CONTACT_KEY);
            mList.add(0, contact);
            mAdapter.notifyDataSetChanged();
            refreshListTip();
            Toast.makeText(this, "添加联系人成功", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            //  修改联系人成功
            Contact contact = (Contact) data.getSerializableExtra(UpdateContactActivity.CONTACT_KEY);
            int index = data.getIntExtra(UpdateContactActivity.INDEX_KEY, -1);
            if (contact != null && index != -1) {
                mList.set(index, contact);
                mAdapter.notifyDataSetChanged();
                refreshListTip();
                Toast.makeText(this, "修改联系人成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadContacts() {
        List<Contact> contacts = ContactDAO.getInstance(this).queryContacts();
        mList.clear();
        mList.addAll(contacts);
        mAdapter.notifyDataSetChanged();
    }

    private void updateContact() {
        Contact contact = mList.get(mSelectedIndex);
        Intent intent = UpdateContactActivity.newInstance(this, contact, mSelectedIndex);
        startActivityForResult(intent, REQUEST_EDIT);
        mSelectedIndex = -1;
    }

    private void deleteContact() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除联系人吗")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    Contact contact = mList.get(mSelectedIndex);
                    int id = contact.getId();
                    int count = ContactDAO.getInstance(MainActivity.this).deleteContact(id);
                    if (count > 0) {
                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        mList.remove(contact);
                        mAdapter.notifyDataSetChanged();
                        refreshListTip();
                        mSelectedIndex = -1;
                    }
                })
                .show();
    }

    private void refreshListTip() {
        if (mList.size() > 0) {
            mLv.setVisibility(View.VISIBLE);
            mtvTip.setVisibility(View.GONE);
        } else {
            mLv.setVisibility(View.GONE);
            mtvTip.setVisibility(View.VISIBLE);
        }
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, UpdateContactActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
    }
}