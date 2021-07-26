package com.example.articlelistdemo;

import java.io.Serializable;
import java.util.List;

public class PagedResult<T extends Serializable> implements Serializable {
    private int currentPage;
    private int totalCount;
    private int pageSize;
    private List<T> contents;

    public PagedResult() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }
}
