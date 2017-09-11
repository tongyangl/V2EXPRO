package com.example.v2ex.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v2ex.R;
import com.example.v2ex.ui.activity.NodeCollectActivity;
import com.example.v2ex.ui.activity.SiginActivity;
import com.example.v2ex.ui.activity.SpecialActivity;
import com.example.v2ex.ui.activity.TopticCollectActivity;
import com.example.v2ex.utils.GlideCacheUtil;
import com.leon.lib.settingview.LSettingItem;

/**
 * Created by 佟杨 on 2017/9/3.
 */

public class Setting_PageFragment extends BaseFragment {
    private ImageView userIcon;
    private TextView userName;
    private LSettingItem nodeCollect;
    private LSettingItem topticsCollect;
    private LSettingItem special;
    private LSettingItem night;
    private LSettingItem noImg;
    private LSettingItem about;
    private LSettingItem clearCache;

    // private LSettingItem noImg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        userIcon = (ImageView) view.findViewById(R.id.userIcon);
        userName = (TextView) view.findViewById(R.id.userName);
        nodeCollect = (LSettingItem) view.findViewById(R.id.nodeCollect);
        topticsCollect = (LSettingItem) view.findViewById(R.id.topticsCollect);
        special = (LSettingItem) view.findViewById(R.id.special);
        night = (LSettingItem) view.findViewById(R.id.night);
        noImg = (LSettingItem) view.findViewById(R.id.noImg);
        about = (LSettingItem) view.findViewById(R.id.about);
        clearCache = (LSettingItem) view.findViewById(R.id.clearcache);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        nodeCollect.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), NodeCollectActivity.class);
                startActivity(intent);
            }
        });
        nodeCollect.setLeftText("节点收藏");

        topticsCollect.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), TopticCollectActivity.class);
                startActivity(intent);
            }
        });
        topticsCollect.setLeftText("主题收藏");
        special.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), SpecialActivity.class);
                startActivity(intent);
            }
        });
        special.setLeftText("特别关注");
        noImg.setLeftText("移动网络下显示图片");
        noImg.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("noImg", isChecked);
                editor.commit();
            }
        });
        night.setLeftText("夜间模式");
        about.setLeftText("关于");
        clearCache.setLeftText("清理缓存");
        clearCache.setRightText(GlideCacheUtil.getInstance().getCacheSize(getContext()));

    }

    @Override
    protected void loadData() {
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SiginActivity.class);
                startActivity(intent);
            }
        });
    }
}
