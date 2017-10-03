package com.example.v2ex.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.v2ex.R;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.utils.FastBlurUtil;
import com.example.v2ex.utils.LoadDate;
import com.github.jdsjlzx.recyclerview.LRecyclerView;


import net.qiujuer.genius.blur.StackBlur;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 佟杨 on 2017/4/10.
 */

public class NodeTopticsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LRecyclerView listView;
    // private SmartRefreshLayout swipe;
    private String url;
    private String num;
    private String title;
    private ProgressDialog dialog;

    private CollapsingToolbarLayout toolbarLayout;

    private RelativeLayout relativeLayout;
    private TextView nums;
    private TextView toptics;
    private TextView collect;
    private ProgressBar progressBar;
    private TextView tv_header;
    private TextView tv_footer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (LRecyclerView) findViewById(R.id.lv);
        nums = (TextView) findViewById(R.id.nums);
        toptics = (TextView) findViewById(R.id.toptics);
        relativeLayout = (RelativeLayout) findViewById(R.id.relat);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        tv_footer = (TextView) findViewById(R.id.footer);
        collect = (TextView) findViewById(R.id.collect);
        tv_header = (TextView) findViewById(R.id.header);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        url = getIntent().getStringExtra("url");
        num = getIntent().getStringExtra("num");
        title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "我在v2ex找到个不错的主题！" + "https://www.v2ex.com/" + url);
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享主题到"));
                return true;
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collect.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Internet_Manager.getInstance()
                        .collect(LoadDate.nodetoptics)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                int code = response.code();
                                if (code == 200) {
                                    if (LoadDate.nodetoptics.startsWith("unfavorite")) {
                                        LoadDate.nodetoptics = LoadDate.nodetoptics.substring(2);
                                        collect.setText("关注");
                                        collect.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(NodeTopticsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                        Log.d("---", LoadDate.nodetoptics);
                                        collect.setBackgroundResource(R.drawable.collect);
                                        Drawable drawable = getDrawable(R.drawable.ic_add_black_24dp);
                                        drawable.setBounds(0, 0, 32, 32);
                                        collect.setCompoundDrawables(drawable, null, null, null);
                                    } else {
                                        collect.setText("已关注");
                                        collect.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        LoadDate.nodetoptics = "un" + LoadDate.nodetoptics;

                                        Toast.makeText(NodeTopticsActivity.this, "节点收藏成功", Toast.LENGTH_SHORT).show();
                                        collect.setBackgroundResource(R.drawable.collect2);
                                        Drawable drawable = getDrawable(R.drawable.ic_check_black_24dp);
                                        drawable.setBounds(0, 0, 32, 32);
                                        collect.setCompoundDrawables(drawable, null, null, null);
                                    }
                                } else {
                                    collect.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(NodeTopticsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(NodeTopticsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                collect.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back1_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (!url.contains("https")) {
            url = url.replace("http://www.v2ex.com/go/", "");

        } else if (url.contains("https")) {
            url = url.replace("https://www.v2ex.com/go/", "");
        }
        LoadDate.getNodeToptics(collect, relativeLayout, toolbar, false, url
                , listView,
                getLayoutInflater(), NodeTopticsActivity.this);
        Internet_Manager.getInstance()
                .collect("/api/nodes/show.json?name=" + url)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        int code = response.code();

                        if (code == 200) {

                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                String name = jsonObject.getString("title_alternative");
                                String imgurl = jsonObject.getString("avatar_mini");
                                String largImg = jsonObject.getString("avatar_large");
                                String topics = jsonObject.getString("topics");
                                String starts = jsonObject.getString("stars");

                                String header = jsonObject.getString("header");
                                String footer = jsonObject.getString("footer");
                                if (!header.equals("null")) {

                                    tv_header.setText(header);
                                }else {
                                    tv_header.setVisibility(View.GONE);
                                }
                                if (!footer.equals("null")) {

                                    tv_footer.setText(footer);

                                }else {
                                    tv_footer.setVisibility(View.GONE);

                                }


                                toptics.setText(starts);

                                Glide.with(NodeTopticsActivity.this).load("http:" + imgurl).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                        toolbar.setLogo(new BitmapDrawable(resource));

                                    }
                                });

                                nums.setText(topics);
                                if (imgurl.startsWith("/static/")) {

                                    Glide.with(NodeTopticsActivity.this).load("http://img5.imgtn.bdimg.com/it/u=153372076,822495421&fm=27&gp=0.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            Bitmap blurBitmap = FastBlurUtil.toBlur(resource, 30);
                                            toolbarLayout.setBackground(new BitmapDrawable(blurBitmap));


                                        }
                                    });
                                } else {
                                    Glide.with(NodeTopticsActivity.this).load("http:" + largImg).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            Bitmap blurBitmap = FastBlurUtil.toBlur(resource, 30);
                                            toolbarLayout.setBackground(new BitmapDrawable(blurBitmap));


                                        }
                                    });
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.node, menu);


        return super.onCreateOptionsMenu(menu);

    }


}
