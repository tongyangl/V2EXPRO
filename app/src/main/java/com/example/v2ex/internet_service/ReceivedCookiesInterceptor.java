package com.example.v2ex.internet_service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by 佟杨 on 2017/9/5.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());
        final StringBuffer cookieBuffer = new StringBuffer();
        Observable.from(response.headers("Set-Cookie"))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        cookieBuffer.append(cookie).append(";");
                    }
                });
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("---", cookieBuffer.toString());
        editor.putString("cookie", cookieBuffer.toString());
        editor.commit();


        return response;
    }
}
