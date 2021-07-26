package com.example.popupwindowtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

/*
    1.PopupWindow的布局
        1）背景图片为9patch图片
        2）内容整体布局
        3）多状态背景（selector+shape）
    2.点击item显示PopupWindow
    3.滑动移除正在显示的PopupWindow
--------------------------------------------
    1.分析界面结构，编写布局文件
        1）整体布局：
            LinearLayout
                TextView
                ListView
        2）item布局：
            LinearLayout
                ImageView TextView
    2.使用ListView+BaseAdapter显示所有永盈信息
        1）得到所有应用信息数据对象的集合List<AppInfo>
        2）定义BaseAdapter的实现类：AppInfoAdapter
    3）设置ListView的item点击监听
    4）设置ListView的长按监听
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {

    private static final String TAG = "MainActivity";

    private ListView mListView;
    private List<AppInfo> mList;
    private AppInfoAdapter mAdapter;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.lv);
        mList = AppInfo.getAppInfos(this);
        mAdapter = new AppInfoAdapter(this, mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mListView.setOnScrollListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    /*
        ListView行点击回调
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         showPopupWindow(parent, view, position, id);
    }

    /*
        ListView行长按回调
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定隐藏此应用吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    mList.remove(position);
                    mAdapter.notifyDataSetChanged();
                })
                .show();
        return true;
    }

    /*
        ListView滑动状态改变回调
        SCROLL_STATE_IDLE：空闲状态（未滑动）
        SCROLL_STATE_TOUCH_SCROLL：跟着手指，正在滚动
        SCROLL_STATE_FLING：手指已经离开ScrollView，但ScrollView仍在滚动
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d(TAG, "onScrollStateChanged: scrollState = " + scrollState);

        //  如果开始滑动，且PopupWindow不为空，则移除PopupWindow
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL &&
            mPopupWindow != null &&
            mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /*
        ListView正在滚动时回调（产生Move时回调）
        firstVisibleItem：第一个可见Item的下标
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d(TAG, "onScroll: firstVisibleItem = " + firstVisibleItem);
    }

    private void showPopupWindow(AdapterView<?> parent, View view, int position, long id) {
        //  懒加载PopupWindow
        if (mPopupWindow == null) {
            //  初始化PopupWindow的contentView
            View contentView = LayoutInflater.from(this).inflate(R.layout.layout_menu, null);
            //  初始化PopupWindow
            mPopupWindow = new PopupWindow(contentView, view.getWidth() - 300, view.getHeight());
            //  设置focusable是为了可以让PopupWindow响应返回键
//            mPopupWindow.setFocusable(true);

            //  必须指定背景，setFocusable才能生效
            //  建议指定一个背景
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  指定PopupWindow的显示/隐藏动画
            mPopupWindow.setAnimationStyle(R.style.MenuWindowShowStyle);

            //  初始化子控件
            LinearLayout llSafe = contentView.findViewById(R.id.ll_safe);
            LinearLayout llSetting = contentView.findViewById(R.id.ll_setting);
            LinearLayout llQrcode = contentView.findViewById(R.id.ll_qrcode);

            //  子控件设置点击监听
            llSafe.setOnClickListener(v -> {
                mPopupWindow.dismiss();
                Toast.makeText(MainActivity.this, "安全", Toast.LENGTH_SHORT).show();
            });

            llSetting.setOnClickListener(v -> {
                mPopupWindow.dismiss();
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
            });

            llQrcode.setOnClickListener(v -> {
                mPopupWindow.dismiss();
                Toast.makeText(MainActivity.this, "二维码", Toast.LENGTH_SHORT).show();
            });
        }

        //  如果PopupWindow正在显示，则移除
        if (mPopupWindow.isShowing()) {
            //  隐藏PopupWindow，但PopupWindow依然在内存中
            mPopupWindow.dismiss();
        }

        //  显示PopupWindow
        mPopupWindow.showAsDropDown(view, 200, -view.getHeight());
    }
}