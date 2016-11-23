package zero.tongyang.threegrand.com.x2expro.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.Internet.Login;
import zero.tongyang.threegrand.com.x2expro.R;

import static zero.tongyang.threegrand.com.x2expro.Value.value.Cookie;

/**
 * Created by tongyang on 16-11-22.
 */

public class SiginActivity extends Activity {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.signin)
    Button signin;
    private Login login;
    private String usernameformat = "";
    private String passwordformat = "";
    private String once = "";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.signin)
    void Signin() {
        getFormat getFormat = new getFormat();
        String name = username.getText().toString();
        String pass = password.getText().toString();
        String args[] = {
                name, pass
        };
        getFormat.execute(args);
    }

    class GetUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Document document = Jsoup.parse(s);
            Elements elements = document.select("div[id=Rightbar]").select("div[class=box]");

            Log.d("sss", elements.size() + "ss");
        }

        @Override
        protected String doInBackground(String... strings) {
            return GetTopics.getTopic("");

        }
    }

    class getFormat extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SiginActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("登陆中...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SiginActivity.this);
                builder.setTitle("提示");
                builder.setMessage("登陆遇到问题，请检查用户名密码");
                builder.setPositiveButton("确定", null);
                builder.show();

            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cookie", s);
                editor.commit();
                Cookie = s;
                GetUserInfo getUserInfo = new GetUserInfo();
                getUserInfo.execute();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            login = new Login();

            return login.login(strings[0], strings[1]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {

            dialog.dismiss();
        }
    }
}
