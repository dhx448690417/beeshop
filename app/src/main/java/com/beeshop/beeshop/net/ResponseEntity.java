package com.beeshop.beeshop.net;

import java.io.Serializable;

/**
 * Author : Cooper
 * Time : 2018/1/2  15:49
 * Description : 接口返回实体类分装
 */

public class ResponseEntity<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
