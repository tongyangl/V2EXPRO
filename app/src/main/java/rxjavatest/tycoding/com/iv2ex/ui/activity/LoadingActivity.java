package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.service.noticeservice;

/**
 * Created by 佟杨 on 2017/4/14.
 */

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //无初始化数据


                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1500);

    }
}
