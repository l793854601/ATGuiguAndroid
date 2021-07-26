package com.example.popupwindowtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class AppInfo {
    //  图标
    private Drawable icon;
    //  名称
    private String name;
    //  包名
    private String packageName;

    public static List<AppInfo> getAppInfos(Context context) {
        //  获取PackageManager对象
        PackageManager packageManager = context.getPackageManager();
        //  创建用于启动应用主界面的Intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //  得到包含应用信息的列表
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        List<AppInfo> appInfos = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            AppInfo appInfo = new AppInfo(
                    resolveInfo.loadIcon(packageManager),
                    resolveInfo.loadLabel(packageManager).toString(),
                    resolveInfo.activityInfo.packageName);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    public AppInfo() {
    }

    public AppInfo(Drawable icon, String name, String packageName) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
