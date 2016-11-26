package zero.tongyang.threegrand.com.x2expro.Internet;

import android.content.Context;
import android.util.Log;


import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zero.tongyang.threegrand.com.x2expro.Utils.PersistentCookieStore;


/**
 * Created by tongyang on 16-11-13.
 */

public class GetTopics {
    private Context context;
    ClearableCookieJar cookieJar;

    public GetTopics(Context context) {
        this.context = context;
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
    }

    private OkHttpClient getokhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).followRedirects(false).followSslRedirects(false)
                .connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
        return okHttpClient;


    }


    public String getTopic(String string) {
        String url = "https://www.v2ex.com/" + string;

        Request request = new Request.Builder().url(url).build();
        try {
            Response response = getokhttp().newCall(request).execute();

            if (response.isSuccessful()) {

                return response.body().string();
            } else {

                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String login(String username, String password) {
        String args[] = getformat();
        String signinurl = "https://www.v2ex.com/signin";
        Log.d("aaaa", args[0]);
        Log.d("aaaa", args[1]);
        Log.d("aaaa", args[2]);
        RequestBody requestBody = new FormBody.Builder().
                addEncoded(args[0], username).
                addEncoded(args[1], password).
                addEncoded("once", args[2]).
                addEncoded("next", "/").build();
        Request request = new Request.Builder().url(signinurl).header("Referer","https://www.v2ex.com/signin").header("Content-Type","application/x-www-form-urlencoded").post(requestBody).build();
        try {
            Response response = getokhttp().newCall(request).execute();
            int code = response.code();

            if (code == 302) {
                Log.d("wwww", args[0]);
                Log.d("wwww", args[1]);
                Log.d("wwww", args[2]);
                String cookie = response.headers().toString();
                Log.d("wwww", cookie);
                return cookie;
            } else if (code == 200) {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] getformat() {
        String s = "";
        String url = "https://www.v2ex.com/signin";
        Log.d("----", url);

        Request request = new Request.Builder().url(url).build();
        try {
            Response response = getokhttp().newCall(request).execute();

            if (response.isSuccessful()) {

                s = response.body().string();
                Document document = Jsoup.parse(s);
                Elements elements = document.select("div[class=box]");
                Elements tr = elements.get(1).select("form").select("table").select("tr");
                String name = tr.get(0).select("td").get(1).select("input").attr("name");

                String pass = tr.get(1).select("td").get(1).select("input").attr("name");
                String once = tr.get(2).select("td").get(1).select("input").attr("value");

                return new String[]{

                        name, pass, once,
                };
            } else {

                s = "";
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
