package rxjavatest.tycoding.com.iv2ex.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rxjavatest.tycoding.com.iv2ex.BaseApplication;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.rxjava.rxjava;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

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
    private ProgressDialog dialog;
    private String title;
    private String repliceurl;
    private String img;
    private String Username;
    private String nodetitle;
    private String time;
    private String collectionurl;
    private  collecttion cool;
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
        if (intent.hasExtra("@user")){

            editText.setText("@"+intent.getStringExtra("@user"));
        }

        if (editText.getText().length() != 0) {
            replice.setClickable(true);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_send2_black_24dp);
            replice.setBackgroundDrawable(drawable);
        } else {
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
                } else {
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

    public void gettopticdetal() {
        rxjava.getTopticDetils(TopicsDetalisActivity.this, repliceurl, swipe,
                lv, title, img, Username, nodetitle, time
                , editText, replice,false
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.it_collect) {
            if (BaseApplication.islogin(getApplicationContext())){
                 cool = new collecttion();
                cool.execute();
            }else {
                Intent intent3 = new Intent(TopicsDetalisActivity.this, SiginActivity.class);
                startActivity(intent3);
            }

            return true;
        } else if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "这个主题不错快来看看吧！" + tyutils.BASE_URL + repliceurl);
            shareIntent.setType("text/plain");

            //设置分享列表的标题，并且每次都显示分享列表
            startActivity(Intent.createChooser(shareIntent, "分享到"));//分享的标题

        } else if (item.getItemId() == R.id.replice) {
            if (BaseApplication.islogin(getApplicationContext())){
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }else {
                Intent intent3 = new Intent(TopicsDetalisActivity.this, SiginActivity.class);
                startActivity(intent3);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.topticdetal, menu);

        return super.onCreateOptionsMenu(menu);


    }

    class collecttion extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TopicsDetalisActivity.this);
            dialog.setTitle("收藏中...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Integer i) {
            Log.d("---", i + "");
            if (i == 302) {
                if (rxjava.topticdetal.startsWith("unfavorite")) {
                    rxjava.topticdetal = rxjava.topticdetal.substring(2);
                    dialog.dismiss();

                    Toast.makeText(TopicsDetalisActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    Log.d("---", rxjava.topticdetal);
                } else {
                    dialog.dismiss();

                    rxjava.topticdetal = "un" + rxjava.topticdetal;
                    Log.d("---", rxjava.topticdetal);
                    Toast.makeText(TopicsDetalisActivity.this, "主题收藏成功", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(TopicsDetalisActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(i);
        }

        @Override
        protected Integer doInBackground(String... params) {
            intertnet net = new intertnet(TopicsDetalisActivity.this);
            String url = tyutils.BASE_URL + rxjava.topticdetal;
            Log.d("---", url);
            return net.collection(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cool!=null){
            cool.cancel(true);

        }
    }
}
