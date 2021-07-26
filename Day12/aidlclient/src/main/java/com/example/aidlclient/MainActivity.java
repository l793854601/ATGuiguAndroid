package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidlservice.IStudentService;
import com.example.aidlservice.Student;

public class MainActivity extends AppCompatActivity {

    private EditText mEtNo;
    private TextView mTvInfo;

    private boolean mServiceBound;

    private IStudentService mBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = IStudentService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtNo = findViewById(R.id.et_no);
        mTvInfo = findViewById(R.id.tv_info);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mServiceBound && mBinder != null) {
            unbindService(mConnection);
            mServiceBound = false;
            mBinder = null;
        }
    }

    public void onClickBindRemoteService(View view) {
        if (!mServiceBound && mBinder == null) {
            Intent intent = new Intent();
            intent.setAction("com.example.aidlservice.IStudentService");
            intent.setPackage("com.example.aidlservice");
            mServiceBound = bindService(intent, mConnection, BIND_AUTO_CREATE);
        } else {
            Toast.makeText(this, "服务已绑定", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickUnbindRemoteService(View view) {
        if (mServiceBound && mBinder != null) {
            unbindService(mConnection);
            mServiceBound = false;
            mBinder = null;
        } else {
            Toast.makeText(this, "服务未绑定", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCallRemoteServiceMethod(View view) {
        if (mBinder != null) {
            try {
                Integer id = Integer.parseInt(mEtNo.getText().toString());
                Student student = mBinder.getStudent(id);
                runOnUiThread(() -> {
                    if (student != null) {
                        String info = "id: " + student.getId() +
                                "\nname: " + student.getName() +
                                "\nprice: " + student.getPrice();
                        mTvInfo.setText(info);
                    } else {
                        mTvInfo.setText("未找到对应的学生");
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}