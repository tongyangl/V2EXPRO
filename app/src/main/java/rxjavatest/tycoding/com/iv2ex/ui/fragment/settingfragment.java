package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import rxjavatest.tycoding.com.iv2ex.BaseApplication;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.ui.activity.MainActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.MainActivity_ViewBinding;
import rxjavatest.tycoding.com.iv2ex.ui.activity.SiginActivity;

import static android.content.Context.MODE_PRIVATE;
import static rxjavatest.tycoding.com.iv2ex.BaseApplication.FormetFileSize;
import static rxjavatest.tycoding.com.iv2ex.BaseApplication.deleteAllFiles;
import static rxjavatest.tycoding.com.iv2ex.BaseApplication.getFileSizes;

/**
 * Created by 佟杨 on 2017/4/15.
 */

public class settingfragment extends PreferenceFragment {
    SharedPreferences mPreferences;
    Preference mCache;
    Button mLogout;
    Preference mAbout;
    CheckBoxPreference wifibox;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
       /* ViewGroup root = (ViewGroup) getView();
        ListView localListView = (ListView) root.findViewById(android.R.id.list);
        localListView.setBackgroundColor(0);
        localListView.setCacheColorHint(0);
        root.removeView(localListView);
        ViewGroup localViewGroup = (ViewGroup) LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_settings, null);
        ((ViewGroup) localViewGroup.findViewById(R.id.setting_content))
                .addView(localListView, -1, -1);
        localViewGroup.setVisibility(View.VISIBLE);
        root.addView(localViewGroup);*/

        mLogout = (Button) getActivity().findViewById(R.id.setting_logout);
        if (BaseApplication.islogin(getActivity())) {
            mLogout.setVisibility(View.VISIBLE);
        } else {
            mLogout.setVisibility(View.GONE);
        }
        mLogout.setOnClickListener(new View.OnClickListener() {
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
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("finishApp", true);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });
        mAbout = findPreference("pref_about");
        mAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("关于")
                        .setMessage("V2EX的第三方客户端")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return true;
            }
        });
        wifibox = (CheckBoxPreference) findPreference("pref_noimage_nowifi");
        // wifibox.setChecked();
        wifibox.setSummary(
                "2G/3G/4G显示图片"
        );
        wifibox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                // mApp.setConfigLoadImageInMobileNetwork(!wifibox.isChecked());

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (wifibox.isChecked()) {
                    BaseApplication.usemobile = true;
                    editor.putBoolean("wifi", false);
                } else {
                    BaseApplication.usemobile = false;
                    editor.putBoolean("wifi", true);
                }
                editor.commit();

                return true;
            }
        });
        mCache = findPreference("pref_cache");

        String path = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        final File file = new File(path + "/iv2ex");
        try {
            mCache.setSummary(BaseApplication.cache);
            mCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示")
                            .setMessage("您将要清空缓存，这可能导致您浏览帖子时消耗大量流量，是否继续？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ImageLoader.getInstance().clearDiskCache();

                                    long size = 0;
                                    try {
                                        size = getFileSizes(ImageLoader.getInstance().getDiskCache().getDirectory());
                                        mCache.setSummary(FormetFileSize(size));
                                        Toast.makeText(getActivity(), "已清空缓存", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null).show();

                    return true;
                }
            });
        } catch (Exception e) {
            Log.d("---", "printStackTrace");
            e.printStackTrace();
        }

    }


}
