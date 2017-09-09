package com.example.v2ex.internet_service.mythrowable;

import rx.functions.Func1;

/**
 * Created by 佟杨 on 2017/9/5.
 */

public class ServerResponseFunc<T> implements Func1<Response<T>, T> {
    @Override
    public T call(Response<T> tResponse) {
        if (tResponse.state != 0) {

             // throw new servere
        }

        return tResponse.data;
    }
}
