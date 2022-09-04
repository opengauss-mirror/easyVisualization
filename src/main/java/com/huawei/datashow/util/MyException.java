package com.huawei.datashow.util;

public class MyException extends java.lang.Exception {

    private String message;

    public MyException(String message){
        super(message);
        this.message = message;
    }
}
