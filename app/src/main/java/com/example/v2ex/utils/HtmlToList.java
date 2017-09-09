package com.example.v2ex.utils;

import android.util.Log;

import com.example.v2ex.model.NodesModel;
import com.example.v2ex.model.NoticeModel;
import com.example.v2ex.model.TopticModel;
import com.example.v2ex.model.TopticdetalisModel;

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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by 佟杨 on 2017/9/3.
 */

public class HtmlToList {

    public static List<NoticeModel> getNotice(String string) {
        List<NoticeModel> list = new ArrayList<>();
        Elements elements = Jsoup.parse(string).select("div[class=box]").get(2).select("div[class=cell]");
        for (int i = 1; i < elements.size() - 1; i++) {
            NoticeModel nodesModel=new NoticeModel();
            String content = elements.get(i).select("div[class=payload]").text();
            String img = "http://" + elements.get(i).select("img").attr("src").substring(2);
            Log.d("---", img);
            String notice = elements.get(i).select("span[class=fade]").text();
            String time = elements.get(i).select("span[class=snow]").text();
            String url = elements.get(i).select("span[class=fade]").select("a").get(1).attr("href").substring(1);
            String username= elements.get(i).select("span[class=fade]").select("strong").text();
            nodesModel.setContent( content);
            nodesModel.setImg( img);
            nodesModel.setNotice(notice);
            nodesModel.setTime(time);
            nodesModel.setUrl(url);
            nodesModel.setUsername( username);
            list.add(nodesModel);
        }
        return list;
    }


    public static List<TopticModel> NodeTopicsToList(String s) {
        List<TopticModel> list = new ArrayList<>();
        Document document = Jsoup.parse(s);
        Elements elements = document.select("div[id=TopicsNode]").select("tbody");
        Log.d("eeeee", elements.size() + "asd");
        for (Element element : elements) {

            TopticModel topticModel = new TopticModel();
            Elements tr = element.select("tr").select("td");
            String image = tr.get(0).select("img").attr("src");
            String img = image.substring(2, image.length());
            topticModel.setImg("http://" + img);
            String replice = "";
            String title = tr.get(2).select("span[class=item_title]").select("a").text();
            if (tr.get(3).hasText()) {
                replice = tr.get(3).select("a").text();

            }
            String username = "";
            String lastreplice = "";

            topticModel.setTitle(title);
            topticModel.setReplices(replice);
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
            topticModel.setUserName(username);
            topticModel.setLastreplice(lastreplice);
            Element t = tr.get(2).select("span[class=small fade]").first();
            String ti = t.ownText();
            String time = ti.substring(3, ti.length());
            topticModel.setTime(time);
            String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
            topticModel.setRepliceurl(repliceurl.substring(1, repliceurl.length()));
            list.add(topticModel);
        }

        return list;
    }


    public static List<TopticModel> TopicsToList(final String string) {
        List<TopticModel> list = new ArrayList<>();

        Document document = Jsoup.parse(string);
        Elements elements = document.select("div[class=cell item]");
        for (int i = 0; i < elements.size(); i++) {

            Elements tr = elements.get(i).select("tr").select("td");
            TopticModel model = new TopticModel();

            for (int j = 0; j < tr.size(); j++) {
                if (j == 0) {
                    String image = tr.get(j).select("img").attr("src");
                    String img = image.substring(2, image.length());
                    model.setImg("http://" + img);
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
                    model.setRepliceurl(repliceurl.substring(1, repliceurl.length()));
                    model.setTitle(title);
                    model.setNodeTitle(nodetitle);
                    model.setTime(time);
                    model.setUserName(username);
                    model.setLastreplice(lastreplice);

                } else if (j == 3) {
                    String replice = "";
                    if (tr.get(j).hasText()) {
                        replice = tr.get(j).select("a").text();

                    }
                    model.setReplices(replice);
                    Log.d("replies", replice);
                }

            }
            list.add(model);
        }

        return list;
    }

    public static List<TopticdetalisModel> getjsondetals(String s) {
        List<TopticdetalisModel> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = new JSONObject(array.getString(i));
                TopticdetalisModel model = new TopticdetalisModel();
                model.setContent(object.getString("content_rendered"));
                String time = object.getString("created");

                SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
                @SuppressWarnings("unused")
                long lcc = Long.valueOf(time);
                int t = Integer.parseInt(time);
                String times = sdr.format(new Date(t * 1000L));
                model.setTime(times);
                JSONObject object1 = new JSONObject(object.getString("member"));
                model.setImg("http:" + object1.getString("avatar_normal"));
                model.setUsername(object1.getString("username"));
                Log.d("modelmodel", model.getContent());
                Log.d("modelmodel", model.getImg());
                Log.d("modelmodel", model.getTime());
                //Log.d("modelmodel",model.getTitle());
                Log.d("modelmodel", model.getUsername());

                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<TopticdetalisModel> getdetals(final String s) {
        List<TopticdetalisModel> list = new ArrayList<>();

        Document document = Jsoup.parse(s);
        Elements elements = document.select("div[id=Main]").select("div[class=box]");
        if (elements.get(1).select("div").hasClass("cell")) {
            Elements elements1 = elements.get(1).select("div[class=cell]");
            for (int i = 1; i < elements1.size(); i++) {
                Elements e = elements1.get(i).select("td");
                TopticdetalisModel model = new TopticdetalisModel();
                String image = e.get(0).select("img").attr("src");
                if (image.length() == 0) {
                    String img = "http:" + "//v2ex.assets.uxengine.net/gravatar/8dc8d210f5b0da01db85f3884c656ec2?s=48&d=retro";
                    model.setImg(img);
                } else {
                    String img = "http://" + image.substring(2, image.length());
                    model.setImg(img);

                }
                String username = e.get(2).select("strong").text();
                String time = e.get(2).select("span[class=ago]").text();
                String content = e.get(2).select("div[class=reply_content]").toString();
                model.setUsername(username);
                model.setTime(time);
                model.setContent(content);
                list.add(model);

            }
        }

        return list;

    }
}
