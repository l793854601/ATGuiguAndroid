package com.example.contactdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity {

    public static final String CONTACT_KEY = "CONTACT_KEY";
    public static final String INDEX_KEY = "INDEX_KEY";

    private Contact mContact;
    private int mIndex = -1;

    private EditText mEtName;
    private EditText mEtNumber;
    private Button mBtnEdit;

    public static Intent newInstance(Context context, Contact contact, int index) {
        Intent intent = new Intent(context, UpdateContactActivity.class);
        intent.putExtra(CONTACT_KEY, contact);
        intent.putExtra(INDEX_KEY, index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        mContact = (Contact) getIntent().getSerializableExtra(CONTACT_KEY);
        mIndex = getIntent().getIntExtra(INDEX_KEY, -1);

        mEtName = findViewById(R.id.et_name);
        mEtNumber = findViewById(R.id.et_number);
        mBtnEdit = findViewById(R.id.btn_edit);

        if (isUpdate()) {
            mEtName.setText(mContact.getName());
            mEtNumber.setText(mContact.getNumber());
            mBtnEdit.setText("编辑");
        } else {
            mBtnEdit.setText("添加");
        }
    }

    private boolean isUpdate() {
        return mContact != null && mIndex != -1;
    }

    private void updateContact(int id, String name, String number) {
        Contact contact = ContactDAO.getInstance(this).updateContact(id, name, number);
        if (contact != null) {
            Intent data = new Intent();
            data.putExtra(CONTACT_KEY, contact);
            data.putExtra(INDEX_KEY, mIndex);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void insertContact(String name, String number) {
        Contact contact = ContactDAO.getInstance(this).insertContact(name, number);
        if (contact != null) {
            Intent data = new Intent();
            data.putExtra(CONTACT_KEY, contact);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    public void onClickEdit(View v) {
        String name = mEtName.getText().toString();
        String number = mEtNumber.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "请输入号码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isUpdate()) {
            int id = mContact.getId();
            updateContact(id, name, number);
        } else {
            insertContact(name, number);
        }
    }


}