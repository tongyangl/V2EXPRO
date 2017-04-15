package rxjavatest.tycoding.com.iv2ex.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import rxjavatest.tycoding.com.iv2ex.Application;
import rxjavatest.tycoding.com.iv2ex.R;

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
        ViewGroup root = (ViewGroup) getView();
        ListView localListView = (ListView) root.findViewById(android.R.id.list);
        localListView.setBackgroundColor(0);
        localListView.setCacheColorHint(0);
        root.removeView(localListView);
        ViewGroup localViewGroup = (ViewGroup) LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_settings, null);
        ((ViewGroup) localViewGroup.findViewById(R.id.setting_content))
                .addView(localListView, -1, -1);
        localViewGroup.setVisibility(View.VISIBLE);
        root.addView(localViewGroup);
        mLogout = (Button) localViewGroup.findViewById(R.id.setting_logout);
       if (Application.islogin(getActivity())){
           mLogout.setVisibility(View.VISIBLE);
       } else {
           mLogout.setVisibility(View.GONE);
       }
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示")
                        .setMessage("您将退出应用")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });
        wifibox = (CheckBoxPreference) findPreference("pref_noimage_nowifi");
       // wifibox.setChecked();
        wifibox.setSummary(wifibox.isChecked()
                ? "2G/3G/4G不显示图片"
                :"2G/3G/4G显示图片");
        wifibox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
               // mApp.setConfigLoadImageInMobileNetwork(!wifibox.isChecked());
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("set", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if (wifibox.isChecked()){

                    editor.putBoolean("wifi",false);
                }else {
                    editor.putBoolean("wifi",true);
                }
                editor.commit();
                wifibox.setSummary(wifibox.isChecked()
                        ? "2G/3G/4G显示图片"
                        :"2G/3G/4G不显示图片");
                return true;
            }
        });

    }
}
