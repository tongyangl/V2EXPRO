package com.example.v2ex;

import com.weavey.loading.lib.LoadingLayout;

import java.net.ConnectException;

/**
 * Created by 佟杨 on 2017/9/7.
 */

public class MyException {

    public static void onError(Throwable throwable, LoadingLayout loadingLayout) {
        if (throwable instanceof ConnectException) {

            loadingLayout.setStatus(LoadingLayout.No_Network);
        }else {

            loadingLayout.setStatus(LoadingLayout.Error);

        }

    }
}
