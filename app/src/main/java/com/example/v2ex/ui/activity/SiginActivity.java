package com.example.v2ex.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.v2ex.R;
import com.example.v2ex.utils.LoadDate;


/**
 * Created by tongyang on 16-11-22.
 */

public class SiginActivity extends Activity {
    EditText username;
    EditText password;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.login);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (!sharedPreferences.getString("username", "").equals("")) {
            username.setText(sharedPreferences.getString("username", ""));
        }
        if (!sharedPreferences.getString("password", "").equals("")) {
            password.setText(sharedPreferences.getString("password", ""));
        }
        if (username.getText().length() == 0 || password.getText().length() == 0) {
            signin.setClickable(false);
            signin.setBackgroundResource(R.drawable.button_login2);

        } else {
            signin.setClickable(true);
            signin.setBackgroundResource(R.drawable.button_login);

        }
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
                    signin.setBackgroundResource(R.drawable.button_login2);

                } else {
                    signin.setClickable(true);
                    signin.setBackgroundResource(R.drawable.button_login);

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
                    signin.setBackgroundResource(R.drawable.button_login2);

                } else {
                    signin.setClickable(true);
                    signin.setBackgroundResource(R.drawable.button_login);

                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
    }


    ProgressDialog dialog;

    void Signin() {

        String name = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String args[] = {
                name, pass
        };
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", name);
        editor.putString("password", pass);
        editor.commit();
        dialog = new ProgressDialog(this);
        dialog.setTitle("登录中");
        dialog.setCancelable(false);
        dialog.show();
        LoadDate.userLogin(username.getText().toString().trim(), password.getText().toString().trim(),dialog,this);

        //rxjava.login(username.getText().toString().trim(),password.getText().toString().trim(),this,dialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
