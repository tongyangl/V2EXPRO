package rxjavatest.tycoding.com.iv2ex.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 16-11-15.
 */

public class htmlTolist {
    public static String gettoticdetalis(String s) {

        Document document = Jsoup.parse(s);
        return document.select("div[class=topic_content]").toString();


    }

    public static List<Map<String, String>> getNotice(String string) {
        List<Map<String, String>> list = new ArrayList<>();

        Elements elements = Jsoup.parse(string).select("div[class=box]").get(2).select("div[class=cell]");
        for (int i = 1; i < elements.size() - 1; i++) {
            Map<String, String> map = new HashMap<>();
            String content = elements.get(i).select("div[class=payload]").text();
            String img = "http://"+ elements.get(i).select("img").attr("src").substring(2);
            Log.d("---",img);
            String notice = elements.get(i).select("span[class=fade]").text();
            String time = elements.get(i).select("span[class=snow]").text();
            String url = elements.get(i).select("span[class=fade]").select("a").get(1).attr("href").substring(1);
            map.put("content", content);
            map.put("img", img);
            map.put("notice", notice);
            map.put("time", time);
            map.put("url", url);
            list.add(map);
        }
        return list;
    }
    public static List<Map<String,String>> getjsondetals(String s){
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONArray array=new JSONArray(s);
            for (int i=0;i<array.length();i++){
                JSONObject object=new JSONObject(array.getString(i));
                Map<String, String> map = new HashMap<>();
                map.put("content",object.getString("content_rendered"));
                String time=object.getString("created");

                SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
                @SuppressWarnings("unused")
                long lcc = Long.valueOf(time);
                int t = Integer.parseInt(time);
                String times = sdr.format(new Date(t * 1000L));
                map.put("time",times);
                JSONObject object1=new JSONObject(object.getString("member"));
                map.put("img","http:"+object1.getString("avatar_normal"));
                map.put("username",object1.getString("username"));

                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<Map<String, String>> getdetals(String s) {
        List<Map<String, String>> list = new ArrayList<>();
        Document document = Jsoup.parse(s);
        Elements elements = document.select("div[id=Main]").select("div[class=box]");
        Log.d("lllll", elements.size() + "");
        if (elements.get(1).select("div").hasClass("cell")) {
            Elements elements1 = elements.get(1).select("div[class=cell]");
            for (int i = 1; i < elements1.size(); i++) {
                Elements e = elements1.get(i).select("td");
                Map<String, String> map = new HashMap<>();
                String image = e.get(0).select("img").attr("src");
                if (image.length() == 0) {
                    String img = "http:" + "//v2ex.assets.uxengine.net/gravatar/8dc8d210f5b0da01db85f3884c656ec2?s=48&d=retro";
                    map.put("img", img);
                } else {
                    String img = "http://" + image.substring(2, image.length());
                    map.put("img", img);
                }
                String username = e.get(2).select("strong").text();
                String time = e.get(2).select("span[class=fade small]").text();
                String content = e.get(2).select("div[class=reply_content]").toString();
                map.put("username", username);
                map.put("time", time);
                map.put("content", content);
                list.add(map);

            }
            return list;
        } else {

            return list;
        }
    }

    public static List<Map<String, String>> NodeTopicsToList(String s) {
        List<Map<String, String>> list = new ArrayList<>();
        Document document = Jsoup.parse(s);
        Elements elements = document.select("div[id=TopicsNode]").select("tbody");
        Log.d("eeeee", elements.size() + "asd");
        for (Element element : elements) {
            Map<String, String> map = new HashMap<>();
            Elements tr = element.select("tr").select("td");
            String image = tr.get(0).select("img").attr("src");
            String img = image.substring(2, image.length());
            map.put("img", "http://" + img);
            String replice = "";
            String title = tr.get(2).select("span[class=item_title]").select("a").text();
            if (tr.get(3).hasText()) {
                replice = tr.get(3).select("a").text();

            }
            String username = "";
            String lastreplice = "";

            map.put("title", title);
            map.put("replies", replice);
            Elements elements1 = tr.get(2).select("span[class=small fade]").select("strong");
            if (elements1.size() == 2) {
                username = elements1.get(0).select("a").text();
                if (elements1.get(1).hasText()) {
                    lastreplice = elements1.get(1).select("a").text();

                } else {
                    username = elements1.get(0).select("a").text();
                    lastreplice = null;
                }
            } else if (elements1.size() == 1) {
                username = elements1.get(0).select("a").text();
                lastreplice = null;

            }
            map.put("username", username);
            map.put("lastreplice", lastreplice);
            Element t = tr.get(2).select("span[class=small fade]").first();
            String ti = t.ownText();
            String time = ti.substring(3, ti.length());
            map.put("time", time);
            String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
            map.put("repliceurl", repliceurl.substring(1, repliceurl.length()));
            list.add(map);
        }

        return list;
    }

    public static List<Map<String, String>> TopicsToList(String string) {

        List<Map<String, String>> list = new ArrayList<>();

        Document document = Jsoup.parse(string);
        Elements elements = document.select("div[class=cell item]");
        for (int i = 0; i < elements.size(); i++) {

            Elements tr = elements.get(i).select("tr").select("td");
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < tr.size(); j++) {
                if (j == 0) {
                    String image = tr.get(j).select("img").attr("src");
                    String img = image.substring(2, image.length());
                    map.put("img", "http://" + img);
                } else if (j == 2) {
                    String lastreplice = "";
                    String username = "";
                    String title = tr.get(j).select("span[class=item_title]").select("a").text();
                    String repliceurl = tr.get(j).select("span[class=item_title]").select("a").attr("href");

                    String nodetitle = tr.get(j).select("span[class=small fade]").select("a[class=node]").text();

                    Element t = tr.get(j).select("span[class=small fade]").first();
                    String ti = t.ownText();
                    String time = ti.substring(3, ti.length());

                    Elements elements1 = tr.get(j).select("span[class=small fade]").select("strong");
                    if (elements1.size() == 2) {
                        username = elements1.get(0).select("a").text();
                        if (elements1.get(1).hasText()) {
                            lastreplice = elements1.get(1).select("a").text();

                        } else {
                            username = elements1.get(0).select("a").text();
                            lastreplice = null;
                        }
                    } else if (elements1.size() == 1) {
                        username = elements1.get(0).select("a").text();
                        lastreplice = null;

                    }
                    map.put("repliceurl", repliceurl.substring(1, repliceurl.length()));
                    Log.d("===", repliceurl.substring(1, repliceurl.length()));
                    map.put("title", title);
                    map.put("nodetitle", nodetitle);
                    map.put("time", time);
                    map.put("username", username);
                    map.put("lastreplice", lastreplice);

                } else if (j == 3) {
                    String replice = "";
                    if (tr.get(j).hasText()) {
                        replice = tr.get(j).select("a").text();

                    }
                    map.put("replies", replice);
                    Log.d("replies", replice);
                }

            }
            list.add(map);
        }
        return list;
    }
}
