package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NoticeActivity extends AppCompatActivity {
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        toolbar.setTitle("消息提醒");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                rxjava.getNotice(NoticeActivity.this, tyutils.NOTIFI_URL, lv, swipe,tv);
            }
        };
        swipe.setOnRefreshListener(listener);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
        listener.onRefresh();
    }
}
