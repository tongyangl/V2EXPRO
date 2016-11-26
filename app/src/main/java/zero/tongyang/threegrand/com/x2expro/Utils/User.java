package zero.tongyang.threegrand.com.x2expro.Utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tongyang on 16-11-26.
 */

public class User extends Application {

    public void getUserInfo(Elements elements) {

        String image = elements.get(0).select("img").attr("src");
        String img = "http://" + image.substring(2, image.length());
        String username = elements.get(0).select("table").get(0).text();
        Log.d("---", img);
        Log.d("---", username);

        Elements td = elements.get(0).select("table").get(1).select("td");
        String nodenum = td.get(0).select("span").get(0).text();
        String topicnum = td.get(0).select("span").get(1).text();
        String importent = td.get(0).select("span").get(2).text();
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img", img);
        editor.putString("username", username);
        editor.putString("nodenum", nodenum);
        editor.putString("topicnum", topicnum);
        String notice = getnumber(elements.get(0).select("div[class=inner]").select("a").get(1).text());
        int a = Integer.parseInt(notice);
        if (a != 0) {


        }

    }

    public String getnumber(String s) {

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String all = matcher.replaceAll("");
        return Pattern.compile("[^0-9]").matcher(s).replaceAll("");
    }

}
