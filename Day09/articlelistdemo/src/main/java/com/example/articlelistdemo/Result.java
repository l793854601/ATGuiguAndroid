package com.example.articlelistdemo;

import java.io.Serializable;

public class Result<T extends Serializable> implements Serializable {
    private boolean success;

    private int code;

    private T data;

    public Result() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
