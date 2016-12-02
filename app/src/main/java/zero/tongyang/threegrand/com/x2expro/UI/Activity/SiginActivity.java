package zero.tongyang.threegrand.com.x2expro.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zero.tongyang.threegrand.com.x2expro.Design.XEditText;
import zero.tongyang.threegrand.com.x2expro.Internet.GetTopics;
import zero.tongyang.threegrand.com.x2expro.R;
import zero.tongyang.threegrand.com.x2expro.Utils.User;


/**
 * Created by tongyang on 16-11-22.
 */

public class SiginActivity extends Activity {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.login)
    Button signin;
    @BindView(R.id.linear)
    LinearLayout linear;
    private GetTopics login;
    private User user;
    private ProgressDialog dialog;
    private boolean iscansee = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (!sharedPreferences.getString("username", "").equals("")){
            username.setText(sharedPreferences.getString("username", ""));
        }
        if (!sharedPreferences.getString("password", "").equals("")){
            password.setText(sharedPreferences.getString("password", ""));
        }
            password.setDrawableRightListener(new XEditText.DrawableRightListener() {
                @Override
                public void onDrawableRightClick(View view) {

                    if (iscansee) {
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.nosee, 0);
                        iscansee = false;
                    } else {
                        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.see, 0);
                        password.setInputType(InputType.TYPE_NULL);

                        iscansee = true;

                    }
                }
            });
        Resources r = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(r, R.drawable.ff);
        Drawable drawable = new BitmapDrawable(blur(bitmap, 25f));
        linear.setBackground(drawable);
    }

    private Bitmap blur(Bitmap bitmap, float radius) {
        Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
        RenderScript rs = RenderScript.create(this); // 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 创建用于输入的脚本类型
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 创建用于输出的脚本类型
        gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setInput(allIn); // 设置输入脚本类型
        gaussianBlue.forEach(allOut); // 执行高斯模糊算法，并将结果填入输出脚本类型中
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        rs.destroy(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        return output;
    }

    @OnClick(R.id.login)
    void Signin() {
        getFormat getFormat = new getFormat();
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
        getFormat.execute(args);
    }

    class GetUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Document document = Jsoup.parse(s);
            Elements elements = document.select("div[id=Rightbar]").select("div[class=box]");
            Log.d("----", elements.size() + "");

            if (elements.size() == 7) {
                user = new User(SiginActivity.this);
                try {
                    user.getUserInfo(elements);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(SiginActivity.this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {

            dialog.dismiss();
        }
    }
}
