package com.example.newsdemo;

import java.util.ArrayList;
import java.util.List;

public class News {
    private int id;
    private String title;
    private String content;

    public static List<News> getNews() {
        List<News> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            int id = i;
            String title = "标题" + id;
            String content = "正文内容" + id;
            News news = new News(id, title, content);
            list.add(news);
        }
        return list;
    }

    public News() {
    }

    public News(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
