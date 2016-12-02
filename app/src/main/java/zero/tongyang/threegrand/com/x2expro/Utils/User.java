package zero.tongyang.threegrand.com.x2expro.Utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.attr.value;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tongyang on 16-11-26.
 */

public class User {
    private Context context;

    public User(Context context) {
        this.context = context;
    }

    public void getUserInfo(Elements elements) throws IOException {

        String image = elements.get(0).select("img").attr("src");
        String img = "http://" + image.substring(2, image.length());
        String username = elements.get(0).select("table").get(0).text();
        Log.d("---", img);
        Log.d("---", username);
        Log.d("---", elements.get(0).select("table").size() + "");

        Elements td = elements.get(0).select("table").get(1).select("td");
        String nodenum = td.get(0).select("span").get(0).text();
        String topicnum = td.get(1).select("span").get(0).text();
        String importemt = td.get(2).select("span").get(0).text();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img", img);
        editor.putString("nickname", username);
        editor.putString("nodenum", nodenum);
        editor.putString("topicnum", topicnum);
        String notice = getnumber(elements.get(0).select("div[class=inner]").select("a").get(1).text());
        int a = Integer.parseInt(notice);
        if (a != 0) {


        }
        editor.commit();
        getuserimg(img);
        Intent intent = new Intent("userinfo");
        intent.putExtra("img", img);
        intent.putExtra("nickname", username);
        Log.d("---",username+"aaa");

        context.sendBroadcast(intent);
    }

    public void getuserimg(String url) throws IOException {

        Observable.just(url).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Bitmap bitmap = null;
                InputStream in = null;
                try {
                    in = new URL(s).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    String path = "";
                    if (tyutils.SdCardHelper()) {
                        path = Environment.getExternalStorageDirectory().getPath() + "/v2expro/userimg";


                    } else {


                    }
                    Log.d("----", path);
                    File file = new File(path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    file = new File(path + "/userimg.png");
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream out = null;

                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public String getnumber(String s) {

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String all = matcher.replaceAll("");
        return Pattern.compile("[^0-9]").matcher(s).replaceAll("");
    }

}
