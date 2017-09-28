package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v2ex.R;
import com.example.v2ex.adapter.NodesArrayAdapter;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.model.NodesModel;
import com.example.v2ex.ui.fragment.Collection_PageFragment;
import com.example.v2ex.utils.LoadDate;
import com.example.v2ex.utils.LoadImg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.example.v2ex.ui.fragment.Collection_PageFragment.getNodesList;

/**
 * Created by 佟杨 on 2017/9/9.
 */

public class CreateTopticActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private Toolbar toolbar;
    private ImageView img;
    private ProgressDialog dialog;
    private String nodename;
    private EditText mTitle;
    private EditText mContent;
    private String mNodeName;
    private View shadowView;
    private TextView node;
    private View view;
    private TextView submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtoptic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img = (ImageView) findViewById(R.id.img);
        mTitle = (EditText) findViewById(R.id.topic_add_title);
        mContent = (EditText) findViewById(R.id.topic_add_content);
        node = (TextView) findViewById(R.id.node);
        submit = (TextView) findViewById(R.id.bt_submit);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTitle.getText().length() != 0) {
                    submit.setClickable(true);
                    submit.setTextColor(Color.parseColor("#1E90FF"));

                } else {

                    submit.setClickable(false);
                    submit.setTextColor(Color.GRAY);
                }

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getString("userimg", "").equals("")) {

        } else {
            LoadImg.LoadCircleImageView(sharedPreferences.getString("userimg", ""), img, getApplicationContext());
        }
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

                subscriber.onNext(null);
            }
        }).map(new Func1<Object, List<NodesModel>>() {
            @Override
            public List<NodesModel> call(Object o) {

                return getNodesList(getApplicationContext());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NodesModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<NodesModel> nodesModels) {


                        final NodesArrayAdapter arrayAdapter = new NodesArrayAdapter(getApplication(), nodesModels, getLayoutInflater());
                        popupWindow = new PopupWindow(CreateTopticActivity.this);
                        final View view = getLayoutInflater().inflate(R.layout.popu_create, null);
                        SearchView searchView = (SearchView) view.findViewById(R.id.search);
                        searchView.setFocusable(true);
                        ListView listView = (ListView) view.findViewById(R.id.lv);
                        popupWindow.setContentView(view);
                        popupWindow.setFocusable(true);
                        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        listView.setAdapter(arrayAdapter);
                        Drawable drawable = new ColorDrawable(Color.WHITE);
                        popupWindow.setBackgroundDrawable(drawable);
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                arrayAdapter.getFilter().filter(newText);
                                return true;
                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                node.setText(arrayAdapter.objects.get(position).getTitle());
                                nodename = arrayAdapter.objects.get(position).getName();
                                popupWindow.dismiss();
                            }
                        });
                        node.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                    Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                                    node.setCompoundDrawables(null, null, drawable, null);

                                } else {
                                    Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                                    node.setCompoundDrawables(null, null, drawable, null);
                                    popupWindow.showAsDropDown(node);

                                }
                            }
                        });
                    }
                });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTitle.getText().toString().length() != 0) {
                    dialog = new ProgressDialog(CreateTopticActivity.this);
                    dialog.setTitle("发表中...");
                    dialog.show();
                } else {

                    Toast.makeText(getApplicationContext(), "请填写Title", Toast.LENGTH_LONG).show();
                }


                Internet_Manager.getInstance()
                        .getNodeToptics("new/" + nodename)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(CreateTopticActivity.this, "创建失败", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(String s) {
                                Map<String, String> map = new HashMap<String, String>();
                                String once = getOnceStringFromHtmlResponseObject(s);
                                map.put("once", once);
                                map.put("content", mContent.getText().toString());
                                map.put("title", mTitle.getText().toString());
                                Internet_Manager.getInstance()
                                        .creatToptic("new/" + nodename, map)
                                        .enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                int code = response.code();

                                                dialog.dismiss();
                                                Log.d("----", "onResponse:");
                                                if (code == 302) {
                                                    Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {

                                                    Toast.makeText(CreateTopticActivity.this, "创建失败", Toast.LENGTH_SHORT).show();

                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {

                                            }
                                        });

                            }
                        });
            }
        });


    }

    private String getOnceStringFromHtmlResponseObject(String content) {


        Pattern pattern = Pattern.compile("<input type=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    @Override
    public void onBackPressed() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else if (mTitle.getText().toString().isEmpty() &&
                mContent.getText().toString().isEmpty()) {
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setTitle("创作新主题")
                    .setMessage("放弃此次创建的主题？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null).show();
            dialog.show();

        }
    }
}
