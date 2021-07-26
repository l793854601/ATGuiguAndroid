package com.example.gridviewtest;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private Drawable icon;
    private String name;

    public static List<Function> getFunctions(Context context) {
        List<Function> list = new ArrayList<>();
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "手机防盗"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "通讯卫士"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "软件管理"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "流量管理"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "进程管理"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "手机杀毒"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "缓存管理"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "高级工具"));
        list.add(new Function(context.getResources().getDrawable(android.R.drawable.btn_star_big_on), "设置中心"));
        return list;
    }

    public Function() {
    }

    public Function(Drawable icon, String name) {
        this.icon = icon;
        this.name = name;
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
}
