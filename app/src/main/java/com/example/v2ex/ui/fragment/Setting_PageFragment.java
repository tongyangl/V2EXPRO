package com.example.v2ex.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v2ex.MainActivity;
import com.example.v2ex.R;
import com.example.v2ex.ui.activity.CreateTopticActivity;
import com.example.v2ex.ui.activity.NodeCollectActivity;
import com.example.v2ex.ui.activity.SiginActivity;
import com.example.v2ex.ui.activity.SpecialActivity;
import com.example.v2ex.ui.activity.TopticCollectActivity;
import com.example.v2ex.ui.activity.WebViewActivity;
import com.example.v2ex.utils.GlideCacheUtil;
import com.example.v2ex.utils.LoadImg;
import com.example.v2ex.utils.SomeUtils;
import com.leon.lib.settingview.LSettingItem;

import static android.content.Context.MODE_PRIVATE;

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
    private Button set_bt;

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
        set_bt = (Button) view.findViewById(R.id.setting_logout);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        nodeCollect.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

                if (SomeUtils.islogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), NodeCollectActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请登陆后再操作", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), SiginActivity.class);
                    startActivity(intent);
                }

            }
        });
        nodeCollect.setLeftText("节点收藏");

        topticsCollect.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {


                if (SomeUtils.islogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), TopticCollectActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请登陆后再操作", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), SiginActivity.class);
                    startActivity(intent);
                }
            }
        });
        topticsCollect.setLeftText("主题收藏");
        special.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {

                if (SomeUtils.islogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), SpecialActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请登陆后再操作", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), SiginActivity.class);
                    startActivity(intent);
                }
            }
        });
        special.setLeftText("特别关注");
        noImg.setLeftText("移动网络下显示图片");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("noImg", true)) {
            noImg.clickOn();
        }

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
        about.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("关于")
                        .setMessage("V2EX")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
        clearCache.setLeftText("清理缓存");
        clearCache.setRightText(GlideCacheUtil.getInstance().getCacheSize(getContext()));
        clearCache.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("清空缓存？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                GlideCacheUtil.getInstance().clearImageDiskCache(getContext());
                                clearCache.setRightText("0.0Byte");
                                Toast.makeText(getContext(), "清理成功", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        if (!sharedPreferences1.getString("userimg", "").equals("")) {

            LoadImg.LoadCircleImageView(sharedPreferences1.getString("userimg", ""), userIcon, getContext());
            userName.setText(sharedPreferences1.getString("username", ""));
            set_bt.setVisibility(View.VISIBLE);

        }

        set_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("您将退出当前登录状态，并且清空账号信息，是否继续")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("finishApp", true);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });

    }

    @Override
    protected void loadData() {
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SiginActivity.class);
                startActivityForResult(intent, 1);


            }
        });
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SiginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("userimg", "").equals("")){
            userName.setText(sharedPreferences.getString("username", ""));
            set_bt.setVisibility(View.VISIBLE);
            LoadImg.LoadCircleImageView(sharedPreferences.getString("userimg", ""), userIcon, getContext());

        }


    }
}
