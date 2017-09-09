package com.example.v2ex.internet_service.mythrowable;

/**
 * Created by 佟杨 on 2017/9/5.
 */
public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }
}