package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v2ex.R;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.utils.LoadDate;
import com.example.v2ex.utils.LoadImg;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by tongyang on 16-11-22.
 */

public class SiginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private android.support.design.widget.TextInputEditText username;
    private android.support.design.widget.TextInputEditText password;
    private TextView signin;
    private TextView signup;
    private ImageView iv_code;
    private android.support.design.widget.TextInputEditText tv_code;
    private Map<String, String> map;
    private String once;
    private String code;
    private String cv_name;
    private String cv_pass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        signup = (TextView) findViewById(R.id.signup);
        iv_code = (ImageView) findViewById(R.id.iv_code);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_code = (android.support.design.widget.TextInputEditText) findViewById(R.id.text_code);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        username = (android.support.design.widget.TextInputEditText) findViewById(R.id.username);
        password = (android.support.design.widget.TextInputEditText) findViewById(R.id.password);
        signin = (TextView) findViewById(R.id.login);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (!sharedPreferences.getString("username", "").equals("")) {
            username.setText(sharedPreferences.getString("username", ""));
        }
        if (!sharedPreferences.getString("password", "").equals("")) {
            password.setText(sharedPreferences.getString("password", ""));
        }
        if (username.getText().length() == 0 || password.getText().length() == 0) {
            signin.setClickable(false);
            signin.setTextColor(Color.GRAY);

        } else {
            signin.setClickable(true);
            signin.setTextColor(Color.rgb(0, 229, 238));

        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiginActivity.this, WebViewActivity.class);
                intent.putExtra("intent", "https://www.v2ex.com/signup");

                startActivity(intent);
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (username.getText().length() == 0 || password.getText().length() == 0) {
                    signin.setClickable(false);
                    signin.setTextColor(Color.GRAY);

                } else {
                    signin.setClickable(true);
                    signin.setTextColor(Color.rgb(0, 229, 238));


                }

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (username.getText().length() == 0 || password.getText().length() == 0) {
                    signin.setClickable(false);
                    signin.setTextColor(Color.GRAY);

                } else {
                    signin.setClickable(true);
                    signin.setTextColor(Color.rgb(0, 229, 238));


                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });

        getOnce();
        iv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnce();
            }
        });
    }


    ProgressDialog dialog;

    void Signin() {

        String name = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", name);
        editor.putString("password", pass);
        editor.commit();
        dialog = new ProgressDialog(this);
        dialog.setTitle("登录中");
        dialog.setCancelable(false);
        dialog.show();
        map = new HashMap<>();
        map.put(cv_name, name);
        map.put(cv_pass, pass);
        map.put("once", once);
        map.put(code, tv_code.getText().toString().trim());
        map.put("next", "/");

        Log.d("testtest", cv_name);
        Log.d("testtest", cv_pass);
        Log.d("testtest", once);
        Log.d("testtest", code);

        LoadDate.userLogin(map, dialog, this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void getOnce() {
        iv_code.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Internet_Manager.getInstance()
                .getUserFormat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Document document = Jsoup.parse(s);
                        Elements elements1 = document.select("div[class=box]");
                        Elements tr = elements1.get(1).select("form").select("table").select("tr");
                        cv_name = tr.get(0).select("td").get(1).select("input").attr("name");
                        cv_pass = tr.get(1).select("td").get(1).select("input").attr("name");
                        //String once = tr.get(2).select("td").get(1).select("input").attr("value");
                        code = tr.get(2).select("td").get(1).select("input").attr("name");
                        String imgcode = tr.get(2).select("td").get(1).select("div").attr("style");
                        Log.d("testtest", imgcode);
                        int start = imgcode.indexOf("/");
                        int end = imgcode.lastIndexOf("'");
                        String img = imgcode.substring(start + 1, end);

                        int start1 = img.indexOf("=");
                        once = img.substring(start1 + 1);


                        return img;
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getApplicationContext(), "111111111" + e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        iv_code.setVisibility(View.VISIBLE);
                        iv_code.setImageResource(R.drawable.ic_error);
                    }

                    @Override
                    public void onNext(String s) {

                        Log.d("testtest", s);

                        Internet_Manager.getInstance()
                                .downLoad(s)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                        if (response.code() == 200) {

                                            iv_code.setImageBitmap(BitmapFactory.decodeStream(response.body().byteStream()));

                                            iv_code.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            iv_code.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            iv_code.setImageResource(R.drawable.ic_error);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        iv_code.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        iv_code.setImageResource(R.drawable.ic_error);
                                    }
                                });


                    }
                });
    }
}
