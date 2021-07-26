package com.example.componenttest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

/*
    对话框Component
 */
public class DialogComponentActivity extends AppCompatActivity {

    private static final String TAG = "TAG_Dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_component);
    }

    /**
     * 显示一般AlertDialog
     * @param v
     */
    public void showAlertDialog(View v) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定退出登录？")
                .setNegativeButton("取消", (dialog, which) -> {
                    Toast.makeText(DialogComponentActivity.this, "点击了取消：" + which, Toast.LENGTH_SHORT).show();
                })
                .setPositiveButton("确定", (dialog, which) -> {
                    Toast.makeText(DialogComponentActivity.this, "点击了确定：" + which, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    /**
     * 显示单选列表AlertDialog
     * @param v
     */
    public void showListAlertDialog(View v) {
        String[] items = {"红", "蓝", "绿", "灰"};

        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setSingleChoiceItems(items, -1, (dialog, which) -> {
                    String color = items[which];
                    Toast.makeText(DialogComponentActivity.this, "选择了：" + color, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .show();
    }

    /**
     * 显示自定义AlertDialog
     * @param v
     */
    public void showCustomAlertDialog(View v) {
        //  动态加载布局文件，得到对应的View对象
        //  view的真实类型为布局文件根标签类型（LinearLayout）
        View view = getLayoutInflater().inflate(R.layout.layout_custom_alert_dialog, null);

        new AlertDialog.Builder(this)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    //  读取view上的子控件
                    EditText etUsername = view.findViewById(R.id.et_username);
                    EditText etPassword = view.findViewById(R.id.et_password);
                    //  读取EditText文本
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    //  Toast显示
                    Toast.makeText(DialogComponentActivity.this, "用户名：" + username + ", 密码：" + password, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    /**
     * 显示环形进度ProgressDialog
     * @param v
     */
    public void showCircleProgressDialog(View v) {
        ProgressDialog dialog = ProgressDialog.show(this, "提示", "数据加载中...");
        //  模拟延迟3秒后，dialog消失
        //  不能在除主线程以外的线程更新UI
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //  在子线程中切换至主线程执行逻辑，可调用runOnUiThread()方法
                runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(DialogComponentActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    /**
     * 显示水平进度ProgressDialog
     * @param v
     */
    public void showHorizontalProgressDialog(View v) {
        //  使用构造器创建dialog
        ProgressDialog dialog = new ProgressDialog(this);
        //  设置样式为水平样式
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //  设置最大值
        dialog.setMax(30);
        //  显示dialog
        dialog.show();

        //  模拟3秒之内更新进度，进度更新完毕后隐藏dialog
        new Thread(() -> {
            for (int i = 0; i <= 30; i++) {
                int current = i;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(() -> {
                        int progress = dialog.getProgress() + 1;
                        dialog.setProgress(progress);

                        if (current == 30) {
                            dialog.dismiss();
                            Toast.makeText(DialogComponentActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 显示DatePickerDialog
     * @param v
     */
    public void showDatePickerDialog(View v) {
        //  获取日历对象
        Calendar calendar = Calendar.getInstance();
        //  通过日历对象获取年、月、日
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //  创建并显示DatePickerDialog
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);
            }
        }, year, month, dayOfMonth).show();
    }

    /**
     * 显示TimePickerDialog
     * @param v
     */
    public void showTimePickerDialog(View v) {
        //  获取日历对象
        Calendar calendar = Calendar.getInstance();
        //  通过日历对象获取时、分
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //  创建并显示TimePickerDialog
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(TAG, "hourOfDay: " + hourOfDay + ", minute: " + minute);
            }
        }, hourOfDay, minute, true).show();
    }
}