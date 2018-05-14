package com.beeshop.beeshop.net;

import java.io.Serializable;

/**
 * Author : Cooper
 * Time : 2018/1/2  15:49
 * Description : 接口返回实体类分装
 */

public class ResponseEntity<T> implements Serializable {
    private int code;
    private String msg;
    private String time;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
