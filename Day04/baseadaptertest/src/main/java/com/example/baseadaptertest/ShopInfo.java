package com.example.baseadaptertest;

import java.util.ArrayList;
import java.util.List;

public class ShopInfo {
    private int icon;
    private String name;
    private String content;

    public static List<ShopInfo> shops() {
        List<ShopInfo> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ShopInfo shopInfo = new ShopInfo(android.R.drawable.ic_delete, "name---" + i, "content---" + i);
            list.add(shopInfo);
        }
        return list;
    }

    public ShopInfo() {
    }

    public ShopInfo(int icon, String name, String content) {
        this.icon = icon;
        this.name = name;
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
