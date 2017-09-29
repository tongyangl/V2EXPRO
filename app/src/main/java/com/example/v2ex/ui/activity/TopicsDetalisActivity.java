package com.example.v2ex.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.v2ex.R;
import com.example.v2ex.model.TopticdetalisModel;
import com.example.v2ex.utils.LoadDate;
import com.example.v2ex.utils.LoadImg;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import java.io.Serializable;
import java.util.List;

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
    public static List<TopticdetalisModel> list;
    private int editLength;


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

        setSupportActionBar(toolbar);
        toolbar.setTitle("主题回复");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        popupWindow = new PopupWindow(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

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
                return true;
            }
        });

        Intent intent = getIntent();
        url = intent.getStringExtra("url");


        Log.d("urlrul", url);
        loadingLayout.setStatus(LoadingLayout.Loading);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                LoadDate.LoadTopticdetalis(true, loadingLayout, url, listView, smartRefreshLayout, getLayoutInflater(), TopicsDetalisActivity.this, toolbar);

            }
        });
        LoadDate.LoadTopticdetalis(false, loadingLayout, url, listView, smartRefreshLayout, getLayoutInflater(), TopicsDetalisActivity.this, toolbar);


        replice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(TopicsDetalisActivity.this);

                LoadDate.repliceToptic(smartRefreshLayout, loadingLayout, editText, url.substring(2, 8), getApplicationContext(), progressDialog);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > editLength) {
                    if (s.toString().length() > 0 && s.toString().charAt(s.toString().length() - 1) == '@') {

                        Intent intent = new Intent(TopicsDetalisActivity.this, UsersActivity.class);
                        intent.putExtra("list", (Serializable) list);
                        startActivityForResult(intent, 3);
                    }
                }
                editLength = s.length();

                if (editText.getText().length() != 0) {
                    replice.setClickable(true);
                    replice.setTextColor(Color.parseColor("#1E90FF"));

                } else {

                    replice.setClickable(false);
                    replice.setTextColor(Color.GRAY);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position <= list.size()) {
                    editText.setText(editText.getText().toString() + "@" + list.get(position - 1).getUsername());
                    editText.setSelection(editText.getText().length());
                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (data.hasExtra("name"))
                editText.setText(editText.getText().toString() + data.getStringExtra("name"));
            editText.setSelection(editText.getText().length());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.topticdetal, menu);


        return super.onCreateOptionsMenu(menu);
    }

}
