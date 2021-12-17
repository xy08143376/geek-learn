package com.geek.gmq.common;

import java.io.Serializable;

/**
 * @author itguoy
 * @date 2021-12-16 16:19
 */

public class RespBean<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    private RespBean() {

    }

    private RespBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> RespBean success(String msg, T data) {
        return new RespBean(200, msg, data);
    }

    public static <T> RespBean success(T data) {
        return new RespBean(200, "success", data);
    }

    public static <T> RespBean success() {
        return new RespBean(200, "success", null);
    }

    public static <T> RespBean fail(int code, String msg, T data) {
        return new RespBean(code, msg, data);
    }

    public static <T> RespBean fail(int code, String msg) {
        return new RespBean(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public RespBean setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RespBean setData(T data) {
        this.data = data;
        return this;
    }
}
