package zero.tongyang.threegrand.com.x2expro.Internet;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static zero.tongyang.threegrand.com.x2expro.Value.value.Cookie;

/**
 * Created by tongyang on 16-11-13.
 */

public class GetTopics {
    public static String getTopic(String string) {
        String url = "https://www.v2ex.com/" + string;

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).header("Cookie", Cookie).build();
        Log.d("aaa",Cookie+"dd");
        try {
            Response response = okHttpClient.newCall(request).execute();

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
}
