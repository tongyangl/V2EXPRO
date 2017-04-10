package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;

/**
 * Created by 佟杨 on 2017/4/6.
 */

public class TopicsDetalisActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.replice)
    ImageButton replice;
    private String title;
    private String repliceurl;
    private String img;
    private String Username;
    private String nodetitle;
    private String time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topticdetal);
        ButterKnife.bind(this);
        toolbar.setTitle("主题回复");
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent = getIntent();
        title = intent.getStringExtra("title");
        repliceurl = intent.getStringExtra("repliceurl");
        img = intent.getStringExtra("img");
        Username = intent.getStringExtra("username");
        nodetitle = intent.getStringExtra("nodetitle");
        time = intent.getStringExtra("time");
        if (editText.getText().length() != 0) {
            replice.setClickable(true);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_send2_black_24dp);
            replice.setBackgroundDrawable(drawable);
        }else {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_send_black_24dp);
            replice.setBackgroundDrawable(drawable);
            replice.setClickable(false);

        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().length() != 0) {
                    replice.setClickable(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_send2_black_24dp);
                    replice.setBackgroundDrawable(drawable);
                }else {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_send_black_24dp);
                    replice.setBackgroundDrawable(drawable);
                    replice.setClickable(false);

                }
            }
        });

        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gettopticdetal();
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
 public void gettopticdetal(){
     rxjava.getTopticDetils(TopicsDetalisActivity.this, repliceurl, swipe,
             lv, title, img, Username, nodetitle, time
             ,editText,replice
     );
 }
}
