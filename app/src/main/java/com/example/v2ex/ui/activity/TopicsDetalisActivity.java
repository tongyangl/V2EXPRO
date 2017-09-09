package com.example.v2ex.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;
import com.example.v2ex.utils.LoadImg;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by 佟杨 on 2017/4/6.
 */

public class TopicsDetalisActivity extends AppCompatActivity {
    private SmartRefreshLayout smartRefreshLayout;

    private ListView listView;
    private Toolbar toolbar;
    private String url;
    private String titleicon;
    private String userName;
    private PopupWindow popupWindow;
    private LoadingLayout loadingLayout;
    private EditText editText;
    private TextView replice;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topticdetal);
        listView = (ListView) findViewById(R.id.lv);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.swipe);
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText = (EditText) findViewById(R.id.editText);
        replice = (TextView) findViewById(R.id.replice);
        final ImageView imageView = (ImageView) toolbar.findViewById(R.id.titleIcon);
        final TextView title = (TextView) toolbar.findViewById(R.id.my_title);
        ImageView back = (ImageView) toolbar.findViewById(R.id.back);
        ImageView menu = (ImageView) toolbar.findViewById(R.id.bt_menu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        popupWindow = new PopupWindow(this);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {

                    View view = getLayoutInflater().inflate(R.layout.popu_menu, null);
                    Button dissMiss = (Button) view.findViewById(R.id.bt_cancel);
                    dissMiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    TextView share = (TextView) view.findViewById(R.id.tv_share);
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "我在v2ex找到个不错的主题！" + "https://www.v2ex.com/" + url);
                            shareIntent.setType("text/plain");

                            //设置分享列表的标题，并且每次都显示分享列表
                            startActivity(Intent.createChooser(shareIntent, "分享主题到"));
                        }
                    });

                    ColorDrawable dw = new ColorDrawable(0xffffffff);
                    popupWindow.setBackgroundDrawable(dw);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    popupWindow.setContentView(view);
                    popupWindow.setAnimationStyle(R.style.popu_style);
                    popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_topticdetal, null), Gravity.BOTTOM, 0, 0);

                }

            }
        });
        Intent intent = getIntent();
        url = intent.getStringExtra("url");


        if (intent.hasExtra("icon")) {
            titleicon = intent.getStringExtra("icon");
            userName = intent.getStringExtra("username");
            title.setText(userName);
            LoadImg.LoadImage(titleicon, imageView, getApplicationContext());
        } else {
            title.setText("主题详情");
        }

        // smartRefreshLayout.autoRefresh(0);
        loadingLayout.setStatus(LoadingLayout.Loading);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.d("ddd", url);
                LoadDate.LoadTopticdetalis(true, loadingLayout, url, listView, smartRefreshLayout, getLayoutInflater(), getApplicationContext());

            }
        });
        LoadDate.LoadTopticdetalis(false, loadingLayout, url, listView, smartRefreshLayout, getLayoutInflater(), getApplicationContext());

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
                    replice.setTextColor(Color.parseColor("#1E90FF"));

                } else {

                    replice.setClickable(false);
                    replice.setTextColor(Color.GRAY);
                }
            }
        });

        replice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(TopicsDetalisActivity.this);

                LoadDate.repliceToptic(smartRefreshLayout, loadingLayout, editText, url.substring(2, 8), getApplicationContext(), progressDialog);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (popupWindow != null && popupWindow.isShowing()) {

            popupWindow.dismiss();


        }
    }
}
