package rxjavatest.tycoding.com.iv2ex.internet;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

/**
 * Created by 佟杨 on 2017/4/6.
 */

public class intertnet {

    private Context context;
    ClearableCookieJar cookieJar;
    File cacheFile;
    Cache cache;

    /**
     * 初始化
     *
     * @param context
     */
    public intertnet(Context context) {
        this.context = context;
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

        cacheFile = new File(context.getCacheDir().getAbsolutePath(), "Cache");
        cache = new Cache(cacheFile, 1024 * 1024 * 1024);//缓存文件为100MB
    }

    /*
       获取回复的once
     */
    public static String getrepliceonce(String url) {

        Pattern pattern = Pattern.compile("<input type=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(url);
        if (matcher.find())
            return matcher.group(1);
        return null;

    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        String args[] = getformat();
        String signinurl = "https://www.v2ex.com/signin";
        Log.d("----", args[2]);
        RequestBody requestBody = new FormBody.Builder().
                addEncoded(args[0], username).
                addEncoded(args[1], password).
                addEncoded("once", args[2]).
                addEncoded("next", "/").build();
        Request request = new Request.Builder().url(signinurl).header("Referer", "https://www.v2ex.com/signin")
                .header("Content-Type", "application/x-www-form-urlencoded").post(requestBody).build();
        try {
            Response response = getokhttp().newCall(request).execute();
            int code = response.code();
            if (code == 302) {
                Log.d("wwww", args[0]);
                Log.d("wwww", args[1]);
                Log.d("wwww", args[2]);
                String cookie = getTopic("");
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

    /**
     * 回复主题
     *
     * @param content
     * @param topticid
     * @param once
     * @return
     */
    public int replice(String content, String topticid, String once) {
        RequestBody requestBody = new FormBody.Builder().
                addEncoded("content", content).
                addEncoded("once", once).
                build();
        String url = tyutils.BASE_URL + "t/" + topticid;
        Log.d("---", url);
        Request request = new Request.Builder().url(url)
                .addHeader("Referer", url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Origin", tyutils.BASE_URL)
                .post(requestBody)
                .build();
        try {
            Response response = getokhttp().newCall(request).execute();

            int code = response.code();
            Log.d("---", code + "");
            return code;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @return 获取登录的once
     */
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

    /**
     * 获取okhttpcliet
     *
     * @return
     */
    private OkHttpClient getokhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).followRedirects(false).followSslRedirects(false)
                .connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS).retryOnConnectionFailure(true).addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        int maxAge = 60 * 60 * 24; // 有网络时 设置缓存超时时间1个小时
                        int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                        Request request = chain.request();
                        if (tyutils.isNetworkReachable(context)) {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
                                    .build();
                        } else {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                                    .build();
                        }
                        Response response = chain.proceed(request);
                        if (tyutils.isNetworkReachable(context)) {
                            response = response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, max-age=" + maxAge)
                                    .build();
                        } else {
                            response = response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return response;
                    }


                }).cache(cache).build();
        return okHttpClient;

    }

    /**
     * 收藏主题或者节点
     *
     * @param
     * @return
     */
    public int collection(String string) {


        Request request = new Request.Builder().url(string).addHeader("Referer", tyutils.BASE_URL).
                addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = getokhttp().newCall(request).execute();

            return response.code();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;

    }

    /**
     * 根据url获取node下主题
     *
     * @param
     * @return
     */
    public String getNodetoptic(String string) {


        Request request = new Request.Builder().url(string).addHeader("Referer", tyutils.BASE_URL).
                addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
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

    /**
     * 发布主题
     *
     * @param
     * @return
     */
    public int topicCreateWithNodeName(final Context cxt, final String nodeName,
                                       final String title, final String content) {
        final String urlString = tyutils.BASE_URL + "new/" + nodeName;

        String s = getNodetoptic(urlString);
        String once = getOnceStringFromHtmlResponseObject(s);

        RequestBody requestBody = new FormBody.Builder().
                addEncoded("content", content).
                addEncoded("once", once).
                add("title", title).
                build();

        Log.d("---", urlString);
        Request request = new Request.Builder().url(urlString)
                .addHeader("Referer", urlString)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Origin", tyutils.BASE_URL)
                .post(requestBody)
                .build();
        try {
            Response response = getokhttp().newCall(request).execute();
            return response.code();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 传入网页的数据
     * 获取发布主题的once
     *
     * @param
     * @return 返回once
     */
    private static String getOnceStringFromHtmlResponseObject(String content) {


        Pattern pattern = Pattern.compile("<input type=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    /**
     * 获取toptic等信息
     *
     * @param
     * @return
     */
    public String getTopic(String string) {


        String url = tyutils.BASE_URL + string;


        Request request = new Request.Builder().url(url).addHeader("Referer", tyutils.BASE_URL).
                addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
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
}
