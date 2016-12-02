package zero.tongyang.threegrand.com.x2expro.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Utils.User;
import zero.tongyang.threegrand.com.x2expro.Utils.tyutils;

/**
 * Created by tongyang on 16-12-2.
 */

public class LoadActivity extends Activity {
    private GetTopics login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.activity_load);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (tyutils.isNetworkReachable(getApplicationContext())){
                    SharedPreferences sharedPreferences = LoadActivity.this.getSharedPreferences("isfirst", MODE_PRIVATE);
                    boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    if (isFirstRun) {
                        Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                        startActivity(intent);
                        editor.putBoolean("isFirstRun", false);
                        editor.commit();
                        finish();
                    } else {
                        SharedPreferences shared = LoadActivity.this.getSharedPreferences("user", MODE_PRIVATE);
                        String username = shared.getString("username", "");
                        String password = shared.getString("password", "");
                        if (username.equals("") && password.equals("")) {

                            Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            getFormat getFormat = new getFormat();
                            String args[] = {
                                    username, password

                            };
                            getFormat.execute(args);
                            Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                            Toast.makeText(getApplication(),"欢迎回来"+username,Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                            finish();

                        }
                    }

                }else {

                    Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

        }, 1500);

    }

    class GetUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Document document = Jsoup.parse(s);
            Elements elements = document.select("div[id=Rightbar]").select("div[class=box]");
            Log.d("----", elements.size() + "");

            if (elements.size() == 7) {
                User user = new User(LoadActivity.this);
                try {
                    user.getUserInfo(elements);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoadActivity.this);
                builder.setTitle("提示");
                builder.setMessage("登陆遇到问题，请检查用户名密码");
                builder.setPositiveButton("确定", null);
                builder.show();
            }


        }

        @Override
        protected String doInBackground(String... strings) {

            login = new GetTopics(getApplicationContext());
            return login.getTopic("");

        }
    }

    class getFormat extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {

            } else {
                GetUserInfo info = new GetUserInfo();
                info.execute();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            login = new GetTopics(getApplicationContext());

            return login.login(strings[0], strings[1]);
        }
    }

}
