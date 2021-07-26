package com.example.phonemessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;

/*
    需求：
    点击“打电话”：进入拨号界面
    长按“打电话”：直接拨打电话
    点击“发短信”：进入编辑短信界面
    长按“发短信”：直接将短信发送出去

    技术点：
    LinearLayout
    隐示意图、携带数据
    AndroidManifest.xml中配置权限
    Android6.0之后的动态权限检测
    SMSManager发送短信
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1000;
    private static final int REQUEST_SEND_SMS_PERMISSION = 1001;
    
    private EditText mEtPhone;
    private EditText mEtContent;
    private Button mBtnPhone;
    private Button mBtnMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  在Activity中国初始化需要操作的视图对象
        mEtPhone = findViewById(R.id.et_phone);
        mEtContent = findViewById(R.id.et_content);
        mBtnPhone = findViewById(R.id.btn_phone);
        mBtnMessage = findViewById(R.id.btn_message);

        //  给Button设置点击监听
        mBtnPhone.setOnClickListener(v -> {
            //  进入拨号界面

            //  创建一个隐示意图
            Intent intent = new Intent(Intent.ACTION_DIAL);
            //  携带数据
            String phone = mEtPhone.getText().toString();
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            //  启动Activity
            startActivity(intent);
        });

        mBtnMessage.setOnClickListener(v -> {
            //  进入编辑短信界面

            //  创建一个隐示意图
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            //  携带数据
            String phone = mEtPhone.getText().toString();
            intent.setData(Uri.parse("smsto:" + phone));
            //  携带额外数据
            String content = mEtContent.getText().toString();
            intent.putExtra("sms_body", content);
            //  启动Activity
            startActivity(intent);
        });

        //  给Button设置长按监听
        mBtnPhone.setOnLongClickListener(v -> {
            //  拨打电话

            //  Android6.0之后加入了动态权限，不仅要在AndroidManifest.xml中配置，还要在代码中检查权限
            int permission = ContextCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.CALL_PHONE);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PERMISSION);
            }
            //  返回true则表示此事件已经被消费了，不会再触发onClick事件
            return true;
        });

        mBtnMessage.setOnLongClickListener(v -> {
            //  发送短信

            int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[] { Manifest.permission.SEND_SMS },
                        REQUEST_SEND_SMS_PERMISSION);
            }

            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PERMISSION) {
            //  判断拨打电话的权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            }
        }
        else if (requestCode == REQUEST_SEND_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 拨打电话
     */
    private void call() {
        //  创建一个隐示意图
        Intent intent = new Intent(Intent.ACTION_CALL);
        //  携带数据
        String phone = mEtPhone.getText().toString();
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        //  启动Activity
        startActivity(intent);
    }

    /**
     * 发送短信
     */
    private void sendSms() {
        //  得到SmsManager实例
        SmsManager smsManager = SmsManager.getDefault();
        //  发送短信
        String address = mEtPhone.getText().toString();
        String text = mEtContent.getText().toString();
        smsManager.sendTextMessage(address, null, text, null, null);
    }
}