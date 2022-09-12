package com.huawei.datashow.util;

import lombok.Data;

import java.io.Serializable;

/**
 * Data format
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * success flag
     */
    private boolean success = true;

    /**
     * message
     */
    private String message = "success";

    /**
     * result code
     */
    private Integer code = 0;

    /**
     * data
     */
    private T result;

    /**
     * timestamp
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {}

    public Result<T> success(String message) {
        this.message = message;
        this.code = 200;
        this.success = true;
        return this;
    }

    public static<T> Result<T> OK() {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("success");
        return r;
    }

    public static<T> Result<T> OK(T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(200);
        r.setResult(data);
        return r;
    }

    public static<T> Result<T> OK(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static Result<Object> error(String msg) {
        return error(500, msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = 500;
        this.success = false;
        return this;
    }
    /**
     * no auth
     */
    public static Result<Object> noauth(String msg) {
        return error(510, msg);
    }

}
