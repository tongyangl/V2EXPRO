package com.example.v2ex;

import android.app.Application;

import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by 佟杨 on 2017/9/4.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.drawable.ic_error)
                .setEmptyImage(R.drawable.ic_empty)
                .setNoNetworkImage(R.drawable.ic_nonetwork)
                .setAllTipTextColor(R.color.grey)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setLoadingPageLayout(R.layout.layout_loading)
                .setReloadButtonTextColor(R.color.grey)
                .setReloadButtonWidthAndHeight(150, 40);

    }


}
