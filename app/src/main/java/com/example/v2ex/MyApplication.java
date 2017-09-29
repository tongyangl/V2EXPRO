package com.example.v2ex;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.squareup.leakcanary.LeakCanary;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by 佟杨 on 2017/9/4.
 */

public class MyApplication extends Application {
    public static boolean noImg;
    public static boolean isNight;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        SharedPreferences sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        noImg = sharedPreferences.getBoolean("noImg", false);
        isNight = sharedPreferences.getBoolean("isNight", false);
        Glide.get(getApplicationContext()).setMemoryCategory(MemoryCategory.HIGH);
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
