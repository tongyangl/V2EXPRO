package com.example.v2ex.internet_service;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public interface MyServiec_interface {


    @GET("?")
    Observable<String> getToptictab(@Query("tab") String tab);

    @GET("{t}")
    Observable<String> getSome(@Path("t") String t);

    @GET("t/{thing}")
    Observable<String> getTopticDetalis(@Path("thing") String thing);

    //https://www.v2ex.com/api/replies/show.json?topic_id=
    @GET("api/replies/show.json?")
    Observable<String> getTopticDetalisApi(@Query("topic_id") String topic_id);

    @Headers({
            "Origin: https://www.v2ex.com/",
            "Referer:https://www.v2ex.com/signin",
            "Content-Type:application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("signin")
    Observable<String> userLogin(@FieldMap Map<String, String> map);

    @GET("signin")
    Observable<String> getUserFormat();

    @GET("{t}")
    Observable<String> getNodesJson(@Path("t") String t);

    @GET
    Observable<String> getNodeToptics(@Url String t);

    @Headers({

            "Origin: https://www.v2ex.com/",

            "Content-Type:application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST
    Call<String> repliceToptic(@Url String url, @FieldMap Map<String, String> map);


    @Headers({
            "Origin: https://www.v2ex.com/",
            "Content-Type:application/x-www-form-urlencoded"
    })
    @GET
    Observable<String> getNotice(@Url String t);

    @GET
    Observable<String> getNodeCollect(@Url String t);

    @Headers({

            "Origin: https://www.v2ex.com/",

            "Content-Type:application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST
    Call<String> creatToptoc(@Url String url, @FieldMap Map<String, String> map);

    @GET
    Call<String> collect(@Url String url);
}
