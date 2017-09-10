package com.example.v2ex.ui.fragment;

import android.content.Intent;
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
        //  noImg= (LSettingItem) view.findViewById(R.id.noImg);
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
