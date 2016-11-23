package zero.tongyang.threegrand.com.x2expro.Internet;

import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.CookieHandler;

/**
 * Created by tongyang on 16-11-22.
 */

public class Login {
    private OkHttpClient okHttpClient;
    public String login(String username, String password) {
        String args[] = getformat();
        String signinurl = "https://www.v2ex.com/signin";

        okHttpClient.setFollowRedirects(false);
        okHttpClient.setFollowSslRedirects(false);

        Log.d("aaaa", args[0]);
        Log.d("aaaa", args[1]);
        Log.d("aaaa", args[2]);
        RequestBody formBody = new FormEncodingBuilder().add(args[0], username).add(args[1], password).add("once", args[2]).add("next", "/").build();
        Request request = new Request.Builder().url(signinurl).post(formBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            int code = response.code();

            if (code == 302) {
                String cookie = response.headers("Set-Cookie").get(0);

                 Log.d("cccc", cookie);
                return cookie;
            } else if (code == 200) {
                String cookie = response.header("Set-Cookie");
                Log.d("cccc", cookie);
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

        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();

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
