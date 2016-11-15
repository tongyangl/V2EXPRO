package zero.tongyang.threegrand.com.x2expro.Internet;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.spdy.Http2;

import java.io.IOException;

/**
 * Created by tongyang on 16-11-13.
 */

public class PostTopics {

    public static String PostTopics(String node_id, String node_name, String username) {
        String url = "https://www.v2ex.com/api/topics/show.json";
        String key = "";
        String value = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        if (!node_id.equals("")) {

            key = "node_id";
            value = node_id;
        } else if (!node_name.equals("")) {
            key = "node_name";
            value = node_name;

        } else if (!username.equals("")) {
            key = "username";
            value = username;
        }


        Request request = new Request.Builder().url(url+"?"+key+"="+value).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String a = response.body().string();
                Log.d("=-=-=", a + "adsasd");
                return a;

            } else {
                Log.d("=-=-=", "adsasd");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("=-=-=", "aaaaa");

        return null;
    }
}
