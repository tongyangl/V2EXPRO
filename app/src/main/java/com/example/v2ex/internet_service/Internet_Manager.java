package com.example.v2ex.internet_service;

import android.content.Context;
import android.util.Log;

import com.example.v2ex.BuildConfig;
import com.example.v2ex.MainActivity;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public class Internet_Manager {
    private static Internet_Manager manager = null;


    public synchronized static Internet_Manager getInstance() {


        return manager != null ? manager : new Internet_Manager();
    }

    public static Context context;

    //设定日志级别
    private OkHttpClient.Builder getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zcb", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);


        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new ReceivedCookiesInterceptor(context)).
                cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)));
        httpClientBuilder.retryOnConnectionFailure(true);

        return httpClientBuilder;
    }

    // private Gson gson=new GsonBuilder().setLenient().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.v2ex.com/")
            .client(getOkHttpClient().build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    private MyServiec_interface service = retrofit.create(MyServiec_interface.class);

    public Observable<String> getToptictab(String tab) {

        return service.getToptictab(tab);

    }

    public Observable<String> getTopticdetals(String thing) {

        return service.getTopticDetalis(thing);

    }

    public Observable<String> getTopticdetalsApi(String id) {

        return service.getTopticDetalisApi(id);

    }


    public Observable<String> userLogin(Map<String, String> map) {

        return service.userLogin(map);

    }

    public Observable<String> getUserFormat() {

        return service.getUserFormat();
    }

    public Observable<String> getSome() {

        return service.getSome("");
    }

    public Observable<String> getNodesJson() {


        return service.getNodesJson("api/nodes/all.json");
    }

    public Observable<String> getNodeToptics(String t) {


        return service.getNodeToptics(t);
    }

    public Observable<String> getNotice(String t) {


        return service.getNotice(t);
    }

    public Observable<String> getNodecollec(String t) {


        return service.getNodeCollect(t);
    }

    public Observable<String> getTopticclooect(String t) {


        return service.getNodeCollect(t);
    }

    public Observable<String> getSpecial(String t) {


        return service.getNodeCollect(t);
    }


    public Call<String> repliceToptic(String url, Map<String, String> map) {


        return service.repliceToptic(url, map);
    }

    public Call<String> creatToptic(String url, Map<String, String> map) {


        return service.creatToptoc(url, map);
    }
}
