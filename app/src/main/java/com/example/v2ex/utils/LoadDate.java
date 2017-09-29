package com.example.v2ex.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.v2ex.MyException;
import com.example.v2ex.R;
import com.example.v2ex.adapter.NodeCollectAdapter;
import com.example.v2ex.adapter.NodeRecycleAdapter;
import com.example.v2ex.adapter.NodesTopticAdapter;
import com.example.v2ex.adapter.NoticeLvAdapter;
import com.example.v2ex.adapter.TopicRepliceAdaptar;
import com.example.v2ex.adapter.TopticsListViewAdapter;
import com.example.v2ex.internet_service.Internet_Manager;
import com.example.v2ex.model.NodeCollectModel;
import com.example.v2ex.model.NodesModel;
import com.example.v2ex.model.NoticeModel;
import com.example.v2ex.model.TopticModel;
import com.example.v2ex.model.TopticdetalisModel;
import com.example.v2ex.ui.activity.TopicsDetalisActivity;
import com.example.v2ex.widget.RichTextView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.weavey.loading.lib.LoadingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by 佟杨 on 2017/9/4.
 */

public class LoadDate {
    public static String topticdetal = "";

    private static int page;
    private static String nodesUrl = "?p=";

    private static String repliceOnce = "";

    public static void repliceToptic(final SmartRefreshLayout smartRefreshLayout, LoadingLayout loadingLayout, final EditText content, String toptiId, final Context context, final ProgressDialog dialog) {
        if (repliceOnce == null) {

            Toast.makeText(context, "请登录..", Toast.LENGTH_LONG).show();
            return;
        }
        dialog.setTitle("回复中...");
        dialog.show();

        Map<String, String> map = new HashMap<>();
        map.put("content", content.getText().toString());
        map.put("once", repliceOnce);


        Internet_Manager.getInstance().repliceToptic("t/" + toptiId, map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int code = response.code();
                dialog.dismiss();
                if (code == 200) {

                    Toast.makeText(context, "回复成功" + response.message(), Toast.LENGTH_LONG).show();
                    smartRefreshLayout.autoRefresh();
                    content.setText("");
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {

                    Toast.makeText(context, "回复失败" + response.errorBody(), Toast.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "回复失败" + t.getMessage() + repliceOnce, Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        });
    }

    /**
     * 获取个人的特别关注
     *
     * @param isRefresh          判断是否需要下拉刷新
     * @param smartRefreshLayout
     * @param context
     * @param listView
     * @param loadingLayout
     * @param l
     */
    public static void getSpecial(boolean isRefresh, final SmartRefreshLayout smartRefreshLayout
            , final Context context, final ListView listView, final LoadingLayout loadingLayout, final LayoutInflater l
    ) {
        if (!isRefresh) {
            loadingLayout.setStatus(LoadingLayout.Loading);
        }

        Internet_Manager.getInstance()
                .getSpecial("my/following")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<TopticModel>>() {
                    @Override
                    public List<TopticModel> call(String s) {
                        List<TopticModel> list = new ArrayList<TopticModel>();
                        Elements elements = Jsoup.parse(s).select("div[class=cell item]");
                        if (elements.size() > 0) {
                            for (int i = 0; i < elements.size(); i++) {
                                TopticModel top = new TopticModel();
                                Elements tr = elements.get(i).select("tr").select("td");
                                String image = tr.get(0).select("img").attr("src");
                                String img = image.substring(2, image.length());
                                top.setImg("http:" + img);
                                String replice = "";
                                String title = tr.get(2).select("span[class=item_title]").select("a").text();
                                if (tr.get(3).hasText()) {
                                    replice = tr.get(3).select("a").text();

                                }
                                String username = "";
                                String lastreplice = "";

                                top.setTitle(title);
                                top.setReplices(replice);
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
                                top.setUserName(username);
                                top.setLastreplice(lastreplice);
                                Element t = tr.get(2).select("span[class=small fade]").first();
                                String ti = t.ownText();
                                String time = ti.substring(3, ti.length());
                                top.setTime(time);
                                String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
                                top.setRepliceurl(repliceurl.substring(1, repliceurl.length()));
                                list.add(top);


                            }
                        }


                        return list;
                    }
                }).subscribe(new Subscriber<List<TopticModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MyException.onError(e, loadingLayout);
                if (smartRefreshLayout.isRefreshing())
                    smartRefreshLayout.finishRefresh();
            }

            @Override
            public void onNext(final List<TopticModel> topticModels) {
                if (smartRefreshLayout.isRefreshing())
                    smartRefreshLayout.finishRefresh();

                final TopticsListViewAdapter adapter = new TopticsListViewAdapter(topticModels, l, context);

                if (topticModels.size() != 0) {
                    loadingLayout.setStatus(LoadingLayout.Success);
                    listView.setAdapter(adapter);
                } else {

                    loadingLayout.setStatus(LoadingLayout.Empty);
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context, TopicsDetalisActivity.class);
                        intent.putExtra("url", topticModels.get(position).getRepliceurl());
                        context.startActivity(intent);
                    }
                });

                smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                    int nowPage = 1;

                    @Override
                    public void onLoadmore(RefreshLayout refreshlayout) {


                        nowPage++;
                        Log.d("test", "my/following?p=" + nowPage);

                        Internet_Manager.getInstance()
                                .getTopticclooect("my/following?p=" + nowPage)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .map(new Func1<String, List<TopticModel>>() {
                                    @Override
                                    public List<TopticModel> call(String s) {
                                        List<TopticModel> list = new ArrayList<TopticModel>();
                                        Elements elements = Jsoup.parse(s).select("div[class=cell item]");
                                        if (elements.size() > 0) {
                                            for (int i = 0; i < elements.size(); i++) {
                                                TopticModel top = new TopticModel();
                                                Elements tr = elements.get(i).select("tr").select("td");
                                                String image = tr.get(0).select("img").attr("src");
                                                String img = image.substring(2, image.length());
                                                top.setImg("http:" + img);
                                                String replice = "";
                                                String title = tr.get(2).select("span[class=item_title]").select("a").text();
                                                if (tr.get(3).hasText()) {
                                                    replice = tr.get(3).select("a").text();

                                                }
                                                String username = "";
                                                String lastreplice = "";

                                                top.setTitle(title);
                                                top.setReplices(replice);
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
                                                top.setUserName(username);
                                                top.setLastreplice(lastreplice);
                                                Element t = tr.get(2).select("span[class=small fade]").first();
                                                String ti = t.ownText();
                                                String time = ti.substring(3, ti.length());
                                                top.setTime(time);
                                                String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
                                                top.setRepliceurl(repliceurl.substring(1, repliceurl.length()));
                                                list.add(top);


                                            }
                                        }


                                        return list;
                                    }
                                }).subscribe(new Subscriber<List<TopticModel>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                nowPage--;
                                MyException.onError(e, loadingLayout);
                                if (smartRefreshLayout.isRefreshing())
                                    smartRefreshLayout.finishRefresh();
                            }

                            @Override
                            public void onNext(List<TopticModel> toptics) {

                                if (toptics.size() == 0) {
                                    smartRefreshLayout.finishLoadmore();
                                    nowPage--;
                                    Toast.makeText(context, "到底啦", Toast.LENGTH_LONG).show();

                                } else {
                                    if (topticModels.get(topticModels.size() - 1).getTitle().contains(toptics.get(toptics.size() - 1).getTitle())) {
                                        nowPage--;
                                        smartRefreshLayout.finishLoadmore();
                                        Toast.makeText(context, "到底啦", Toast.LENGTH_LONG).show();
                                    } else {
                                        topticModels.addAll(toptics);
                                        adapter.notifyDataSetChanged();
                                        smartRefreshLayout.finishLoadmore();
                                    }
                                }


                            }
                        });

                    }

                });


            }
        });

    }

    /**
     * 获取主题收藏
     *
     * @param isRefresh
     * @param smartRefreshLayout
     * @param context
     * @param listView
     * @param loadingLayout
     * @param l
     */
    public static void getTopticCollect(boolean isRefresh, final SmartRefreshLayout smartRefreshLayout
            , final Context context, final ListView listView, final LoadingLayout loadingLayout, final LayoutInflater l

    ) {
        if (!isRefresh) {
            loadingLayout.setStatus(LoadingLayout.Loading);
        }
        Internet_Manager.getInstance()
                .getTopticclooect("my/topics")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<TopticModel>>() {
                    @Override
                    public List<TopticModel> call(String s) {
                        List<TopticModel> list = new ArrayList<TopticModel>();
                        Elements elements = Jsoup.parse(s).select("div[id=Main]").select("div[class=box]").select("div[class=cell item]");
                        if (elements.size() != 0) {
                            for (Element element : elements) {
                                TopticModel top = new TopticModel();
                                Elements tr = element.select("tr").select("td");
                                String image = tr.get(0).select("img").attr("src");
                                String img = image.substring(2, image.length());
                                top.setImg("http:" + img);
                                String replice = "";
                                String title = tr.get(2).select("span[class=item_title]").select("a").text();
                                if (tr.get(3).hasText()) {
                                    replice = tr.get(3).select("a").text();

                                }
                                String username = "";
                                String lastreplice = "";

                                top.setTitle(title);
                                top.setReplices(replice);
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
                                top.setUserName(username);
                                top.setLastreplice(lastreplice);
                                Element t = tr.get(2).select("span[class=small fade]").first();
                                String ti = t.ownText();
                                String time = ti.substring(3, ti.length());
                                top.setTime(time);
                                String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
                                top.setRepliceurl(repliceurl.substring(1, repliceurl.length()));
                                list.add(top);
                            }

                            return list;
                        } else {
                            return null;
                        }
                    }
                }).

                subscribe(new Subscriber<List<TopticModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        MyException.onError(e, loadingLayout);
                        if (smartRefreshLayout.isRefreshing())
                            smartRefreshLayout.finishRefresh();


                    }

                    @Override
                    public void onNext(final List<TopticModel> topticModels) {
                        if (smartRefreshLayout.isRefreshing())
                            smartRefreshLayout.finishRefresh();

                        final TopticsListViewAdapter adapter = new TopticsListViewAdapter(topticModels, l, context);

                        if (topticModels.size() != 0) {
                            loadingLayout.setStatus(LoadingLayout.Success);
                            listView.setAdapter(adapter);
                        } else {

                            loadingLayout.setStatus(LoadingLayout.Empty);
                        }
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(context, TopicsDetalisActivity.class);
                                intent.putExtra("url", topticModels.get(position).getRepliceurl());
                                context.startActivity(intent);
                            }
                        });

                        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                            int nowPage = 1;

                            @Override
                            public void onLoadmore(RefreshLayout refreshlayout) {


                                page++;
                                Internet_Manager.getInstance()
                                        .getTopticclooect("my/topics?p=" + nowPage)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .map(new Func1<String, List<TopticModel>>() {
                                            @Override
                                            public List<TopticModel> call(String s) {
                                                List<TopticModel> list = new ArrayList<TopticModel>();
                                                Elements elements = Jsoup.parse(s).select("div[id=Main]").select("div[class=box]").select("div[class=cell item]");
                                                if (elements.size() != 0) {
                                                    for (Element element : elements) {
                                                        TopticModel top = new TopticModel();
                                                        Elements tr = element.select("tr").select("td");
                                                        String image = tr.get(0).select("img").attr("src");
                                                        String img = image.substring(2, image.length());
                                                        top.setImg(img);
                                                        String replice = "";
                                                        String title = tr.get(2).select("span[class=item_title]").select("a").text();
                                                        if (tr.get(3).hasText()) {
                                                            replice = tr.get(3).select("a").text();

                                                        }
                                                        String username = "";
                                                        String lastreplice = "";

                                                        top.setTitle(title);
                                                        top.setReplices(replice);
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
                                                        top.setUserName(username);
                                                        top.setLastreplice(lastreplice);
                                                        Element t = tr.get(2).select("span[class=small fade]").first();
                                                        String ti = t.ownText();
                                                        String time = ti.substring(3, ti.length());
                                                        top.setTime(time);
                                                        String repliceurl = tr.get(2).select("span[class=item_title]").select("a").attr("href");
                                                        top.setRepliceurl(repliceurl.substring(1, repliceurl.length()));
                                                        list.add(top);
                                                    }

                                                    return list;
                                                } else {
                                                    return null;
                                                }
                                            }
                                        }).subscribe(new Subscriber<List<TopticModel>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        nowPage--;
                                        MyException.onError(e, loadingLayout);
                                        if (smartRefreshLayout.isRefreshing())
                                            smartRefreshLayout.finishRefresh();
                                    }

                                    @Override
                                    public void onNext(List<TopticModel> toptics) {
                                        if (toptics.size() == 0) {
                                            smartRefreshLayout.finishLoadmore();
                                            nowPage--;
                                            Toast.makeText(context, "到底啦", Toast.LENGTH_LONG).show();

                                        } else {
                                            if (topticModels.get(topticModels.size() - 1).getTitle().contains(toptics.get(toptics.size() - 1).getTitle())) {
                                                nowPage--;
                                                smartRefreshLayout.finishLoadmore();
                                                Toast.makeText(context, "到底啦", Toast.LENGTH_LONG).show();
                                            } else {
                                                topticModels.addAll(toptics);
                                                adapter.notifyDataSetChanged();
                                                smartRefreshLayout.finishLoadmore();
                                            }
                                        }


                                    }
                                });

                            }

                        });
                    }
                });


    }


    /**
     * 获取节点收藏
     *
     * @param isRefresh
     * @param smartRefreshLayout
     * @param context
     * @param recyclerView
     * @param loadingLayout
     */
    public static void getNodeCollect(boolean isRefresh, final SmartRefreshLayout smartRefreshLayout,
                                      final Context context, final RecyclerView recyclerView, final LoadingLayout loadingLayout

    ) {
        if (!isRefresh) {
            loadingLayout.setStatus(LoadingLayout.Loading);
        }

        Internet_Manager.getInstance()
                .getNodecollec("my/nodes")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<NodeCollectModel>>() {
                    @Override
                    public List<NodeCollectModel> call(String s) {
                        List<NodeCollectModel> list = new ArrayList<NodeCollectModel>();
                        Elements elements = Jsoup.parse(s).select("div[id=MyNodes]").select("a[class=grid_item]");
                        if (elements.size() != 0) {
                            for (int i = 0; i < elements.size(); i++) {

                                NodeCollectModel model = new NodeCollectModel();
                                String url = "http://www.v2ex.com/" + elements.get(i).attr("href").substring(1);
                                String imgurl = "http://" + elements.get(i).select("img").attr("src").substring(2);
                                String title = elements.get(i).select("div").get(0).ownText();
                                String num = elements.get(i).select("span[class=fade f12]").text();
                                model.setUrl(url);
                                model.setImgurl(imgurl);
                                model.setTitle(title);
                                model.setNum(num);
                                list.add(model);
                            }

                            return list;
                        } else {
                            return null;
                        }
                    }
                }).

                subscribe(new Subscriber<List<NodeCollectModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        MyException.onError(e, loadingLayout);

                        if (smartRefreshLayout.isRefreshing())
                            smartRefreshLayout.finishRefresh(0);
                    }

                    @Override
                    public void onNext(final List<NodeCollectModel> nodeCollectModels) {
                        if (smartRefreshLayout.isRefreshing())
                            smartRefreshLayout.finishRefresh(0);

                        final NodeCollectAdapter adapter = new NodeCollectAdapter(nodeCollectModels, context);
                        Log.d("---", nodeCollectModels.size() + "sss");
                        if (nodeCollectModels.size() == 0) {

                            loadingLayout.setStatus(LoadingLayout.Empty);
                        } else {


                            loadingLayout.setStatus(LoadingLayout.Success);


                            recyclerView.setAdapter(adapter);

                        }


                        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                            int nowPage = 1;

                            @Override
                            public void onLoadmore(RefreshLayout refreshlayout) {
                                nowPage++;
                                Internet_Manager.getInstance()
                                        .getNodecollec("my/nodes?p=" + nowPage)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .map(new Func1<String, List<NodeCollectModel>>() {
                                            @Override
                                            public List<NodeCollectModel> call(String s) {
                                                List<NodeCollectModel> list = new ArrayList<NodeCollectModel>();
                                                Elements elements = Jsoup.parse(s).select("div[id=MyNodes]").select("a[class=grid_item]");
                                                if (elements.size() != 0) {
                                                    for (int i = 0; i < elements.size(); i++) {

                                                        NodeCollectModel model = new NodeCollectModel();
                                                        String url = "http://www.v2ex.com/" + elements.get(i).attr("href").substring(1);
                                                        String imgurl = "http://" + elements.get(i).select("img").attr("src").substring(2);
                                                        String title = elements.get(i).select("div").get(0).ownText();
                                                        String num = elements.get(i).select("span[class=fade f12]").text();
                                                        model.setUrl(url);
                                                        model.setImgurl(imgurl);
                                                        model.setTitle(title);
                                                        model.setNum(num);
                                                        list.add(model);
                                                    }

                                                    return list;
                                                } else {
                                                    return null;
                                                }
                                            }
                                        }).subscribe(new Subscriber<List<NodeCollectModel>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        nowPage--;

                                        MyException.onError(e, loadingLayout);

                                        if (smartRefreshLayout.isRefreshing())
                                            smartRefreshLayout.finishRefresh(0);
                                    }

                                    @Override
                                    public void onNext(List<NodeCollectModel> lectModels) {
                                        nodeCollectModels.addAll(lectModels);
                                        adapter.notifyDataSetChanged();


                                    }
                                });


                            }
                        });


                    }
                });

    }

    /**
     * 获取回复信息
     *
     * @param isRefresh
     * @param loadingLayout
     * @param listview
     * @param smart
     * @param c
     * @param inflater
     */
    public static void getNotice(
            boolean isRefresh,
            final LoadingLayout loadingLayout, final ListView listview, final SmartRefreshLayout smart,
            final Context c, final LayoutInflater inflater

    ) {
        if (!isRefresh) {
            loadingLayout.setStatus(LoadingLayout.Loading);

        }
        Internet_Manager.getInstance()
                .getNotice("notifications")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<NoticeModel>>() {
                    @Override
                    public List<NoticeModel> call(String s) {


                        return HtmlToList.getNotice(s);
                    }
                }).subscribe(new Subscriber<List<NoticeModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                smart.finishRefresh(0);
                MyException.onError(e, loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        LoadDate.getNotice(false, loadingLayout, listview, smart, c, inflater);

                    }
                });
            }

            @Override
            public void onNext(final List<NoticeModel> noticeModels) {
                final NoticeLvAdapter adapter = new NoticeLvAdapter(noticeModels, c, inflater);
                listview.setAdapter(adapter);
                loadingLayout.setStatus(LoadingLayout.Success);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(c, TopicsDetalisActivity.class);
                        //intent.putExtra("@user", username);

                        intent.putExtra("url", noticeModels.get(position).getUrl());
                        c.startActivity(intent);
                    }
                });
                smart.finishRefresh(0);

                smart.setOnLoadmoreListener(new OnLoadmoreListener() {
                    int nowPage = 1;

                    @Override
                    public void onLoadmore(RefreshLayout refreshlayout) {

                        nowPage++;
                        Internet_Manager.getInstance()
                                .getNotice("notifications?p=" + nowPage)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .map(new Func1<String, List<NoticeModel>>() {
                                    @Override
                                    public List<NoticeModel> call(String s) {


                                        return HtmlToList.getNotice(s);
                                    }
                                }).subscribe(new Subscriber<List<NoticeModel>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                smart.finishLoadmore(0);
                                nowPage--;
                                Toast.makeText(c, "出错了...", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(List<NoticeModel> list) {


                                if (list.get(list.size() - 1).getContent().contains(noticeModels.get(noticeModels.size() - 1).getContent())) {

                                    smart.finishLoadmore();
                                    Toast.makeText(c, "到底啦", Toast.LENGTH_LONG).show();
                                } else {
                                    noticeModels.addAll(list);
                                    adapter.notifyDataSetChanged();
                                    smart.finishLoadmore();
                                }


                            }
                        });


                    }
                });


            }
        });


    }

    /**
     * 获取节点下的主题
     *
     * @param isFrefresh
     * @param url
     * @param listView
     * @param layoutInflater
     * @param c
     */
    public static void getNodeToptics(
            final ImageView iv,
            final Toolbar toolbar,
            //final LoadingLayout loadingLayout,
            final ImageView collapsingToolbarLayout,
            boolean isFrefresh,
            final String url,

            final LRecyclerView listView,
            final LayoutInflater layoutInflater,
            final Context c


    ) {

        //   loadingLayout.setStatus(LoadingLayout.Loading);
        final String[] imgUrl = new String[1];

        page = 1;
        Internet_Manager.getInstance()
                .getNodeToptics("go/" + url + nodesUrl + page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<TopticModel>>() {
                    @Override
                    public List<TopticModel> call(String s) {
                        Element els = Jsoup.parse(s).select("div[class=header]").get(0);

                        imgUrl[0] = "http:" + els.select("img").attr("src");
                        String des = els.select("span[class=f12 gray]").text();
                        String node_title = els.ownText();

                        String top_num = els.select("strong[class=gray]").text();


                        Elements elements;
                        elements = Jsoup.parse(s).select("div[class=cell]").get(4).select("a");
                        String p = "";
                        p = elements.get(elements.size() - 1).text();
                        if (!SomeUtils.isNumeric(p)) {
                            elements = Jsoup.parse(s).select("div[class=cell]").get(5).select("a");
                            p = elements.get(elements.size() - 1).text();
                        }
                        if (!SomeUtils.isNumeric(p)) {
                            elements = Jsoup.parse(s).select("div[class=cell]").get(3).select("a");
                            p = elements.get(elements.size() - 1).text();
                        }
                        page = Integer.parseInt(p);


                        return HtmlToList.NodeTopicsToList(s);
                    }
                }).subscribe(new Subscriber<List<TopticModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(final List<TopticModel> topticModels) {
                String urls;
                //toolbar.setSubtitle("主题总数" + num);
                Log.d("---", imgUrl[0]);
                if (imgUrl[0].length() < 10) {

                    urls = "http:////v2ex.assets.uxengine.net/site/logo@2x.png?m=1346064962";
                } else {
                    urls = imgUrl[0];
                }
                LoadImg.LoadCircleImageView(urls, iv, c);
                Glide.with(c).load(urls).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        collapsingToolbarLayout.setImageBitmap(BlurBitmapUtil.blurBitmap(c, resource, 24));

                    }
                });

                listView.setLayoutManager(new LinearLayoutManager(c));
                final NodesTopticAdapter adapter = new NodesTopticAdapter(topticModels, c);

                final LRecyclerViewAdapter adapter1 = new LRecyclerViewAdapter(adapter);
                listView.setPullRefreshEnabled(false);
                adapter1.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(c, TopicsDetalisActivity.class);
                        intent.putExtra("url", topticModels.get(position).getRepliceurl());
                        c.startActivity(intent);
                    }
                });
                DividerDecoration divider = new DividerDecoration.Builder(c)
                        .setHeight(R.dimen.lv)
                        .setPadding(R.dimen.lv)
                        .setColorResource(R.color.dark_background)
                        .build();
                listView.addItemDecoration(divider);
                listView.setAdapter(adapter1);
                listView.setLoadMoreEnabled(true);
                listView.setOnLoadMoreListener(new OnLoadMoreListener() {
                    int nowPage = 1;

                    @Override
                    public void onLoadMore() {

                        nowPage++;
                        Internet_Manager.getInstance()
                                .getNodeToptics("go/" + url + nodesUrl + nowPage)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .map(new Func1<String, List<TopticModel>>() {
                                    @Override
                                    public List<TopticModel> call(String s) {

                                        return HtmlToList.NodeTopicsToList(s);
                                    }
                                }).subscribe(new Subscriber<List<TopticModel>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                nowPage--;
                                Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
                                //   MyException.onError(e, loadingLayout);
                                Toast.makeText(c, "出错啦...", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(List<TopticModel> toptic) {
                                listView.refreshComplete(toptic.size());
                                if (topticModels.get(topticModels.size() - 1).getTitle().contains(toptic.get(toptic.size() - 1).getTitle())) {
                                    listView.setNoMore(true);

                                } else {

                                    topticModels.addAll(toptic);
                                    adapter1.notifyDataSetChanged();

                                }

                            }
                        });
                    }
                });

            }
        });


    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

    /**
     * 获取所有节点的json数据
     *
     * @param c
     * @param swip
     * @param recycl
     */
    public static void getNodesJson(final Context c, final SmartRefreshLayout swip, final RecyclerView recycl) {

        Internet_Manager.getInstance()
                .getNodesJson()
                .map(new Func1<String, List<NodesModel>>() {
                    @Override
                    public List<NodesModel> call(String s) {
                        List<NodesModel> list = new ArrayList<NodesModel>();

                        try {
                            JSONArray array = new JSONArray(s);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = new JSONObject(array.getString(i));
                                NodesModel nodes = new NodesModel();
                                nodes.setTitle(object.getString("title"));
                                nodes.setContent(object.getString("header"));
                                nodes.setNum(object.getString("topics"));
                                nodes.setUrl(object.getString("url"));
                                nodes.setName(object.getString("name"));
                                list.add(nodes);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return list;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<NodesModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // Toast.makeText(c, "失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                        swip.finishRefresh(0);
                    }

                    @Override
                    public void onNext(List<NodesModel> nodesModels) {
                        Toast.makeText(c, "成功" + nodesModels.size(), Toast.LENGTH_LONG).show();

                        NodeRecycleAdapter adapter = new NodeRecycleAdapter(nodesModels, c);
                        adapter.notifiy(nodesModels);

                        adapter.notify();

                        swip.finishRefresh(0);
                    }
                });


    }

    /**
     * 用户登录
     *
     * @param userName
     * @param passWord
     * @param dialog
     * @param context
     */
    public static void userLogin(final String userName,
                                 final String passWord,
                                 final ProgressDialog dialog,
                                 final Activity context

    ) {


        Internet_Manager.getInstance()
                .getUserFormat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String[]>() {
                    @Override
                    public String[] call(String s) {
                        Document document = Jsoup.parse(s);
                        Elements elements1 = document.select("div[class=box]");
                        Elements tr = elements1.get(1).select("form").select("table").select("tr");
                        String name = tr.get(0).select("td").get(1).select("input").attr("name");
                        String pass = tr.get(1).select("td").get(1).select("input").attr("name");
                        String once = tr.get(2).select("td").get(1).select("input").attr("value");

                        return new String[]{
                                name, pass, once

                        };
                    }
                })
                .subscribe(new Subscriber<String[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(context, "111111111" + e.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }

                    @Override
                    public void onNext(String[] s) {

                        Log.d("ssss", s[0] + " " + s[1] + " " + s[2]);

                        Map<String, String> map = new HashMap<String, String>();
                        map.put(s[0], userName);
                        map.put(s[1], passWord);
                        map.put("once", s[2]);
                        map.put("next", "/");
                        Internet_Manager.getInstance()
                                .userLogin(map)

                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<String>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toast.makeText(context, e.getMessage() + "22222222", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onNext(String s) {

                                        Internet_Manager.getInstance()
                                                .getToptictab("all")
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Subscriber<String>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Toast.makeText(context, "登录失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                                                        dialog.dismiss();

                                                    }

                                                    @Override
                                                    public void onNext(String s) {
                                                        Document document = Jsoup.parse(s);
                                                        String elements = document.select("div[id=Rightbar]").text();


                                                        if (elements.contains("条未读提醒")) {
                                                            Toast.makeText(context, "登录成功", Toast.LENGTH_LONG).show();
                                                            //context.sendBroadcast();

                                                            Elements elements1 = document.select("div[id=Rightbar]").select("div[class=box]");
                                                            Log.d("----", elements1.size() + "");
                                                            String userimg = "http://" + elements1.get(0).select("img").attr("src").substring(2);
                                                            SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                            context.setResult(2);

                                                            Log.d("===", userimg);
                                                            editor.putString("userimg", userimg);
                                                            editor.commit();
                                                            context.finish();
                                                            dialog.dismiss();

                                                        } else {
                                                            Toast.makeText(context, "登录失败", Toast.LENGTH_LONG).show();
                                                            dialog.dismiss();
                                                        }
                                                    }
                                                });


                                    }
                                });
                    }
                });


    }

    /**
     * 获取首页主题
     *
     * @param isRefresh
     * @param url
     * @param listView
     * @param smartRefreshLayout
     * @param layoutInflater
     * @param context
     * @param loadingLayout
     */
    public static void loadTopticsData(boolean isRefresh, final String url, final ListView listView,
                                       final SmartRefreshLayout smartRefreshLayout, final LayoutInflater
                                               layoutInflater, final Context context,
                                       final LoadingLayout loadingLayout

    ) {
        if (listView.getFooterViewsCount() == 0) {
            View view = layoutInflater.inflate(R.layout.layout_recyclerview_list_footer_end, null);

            listView.addFooterView(view);
        }
        listView.setDividerHeight(1);
        if (!isRefresh) {
            loadingLayout.setStatus(LoadingLayout.Loading);
        }
        Internet_Manager.getInstance()
                .getToptictab(url)
                .map(new Func1<String, List<TopticModel>>() {


                    @Override
                    public List<TopticModel> call(String s) {


                        return HtmlToList.TopicsToList(s);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TopticModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ssss", e.getMessage());
                        MyException.onError(e, loadingLayout);
                        if (smartRefreshLayout.isRefreshing()) {
                            smartRefreshLayout.finishRefresh(0);
                        }
                        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                LoadDate.loadTopticsData(false, url, listView, smartRefreshLayout, layoutInflater, context, loadingLayout);

                            }
                        });
                    }

                    @Override
                    public void onNext(final List<TopticModel> topticModels) {
                        Log.d("ssss", topticModels.toString());
                        final TopticsListViewAdapter adapter = new TopticsListViewAdapter(topticModels, layoutInflater, context);
                        loadingLayout.setStatus(LoadingLayout.Success);
                        listView.setAdapter(adapter);
                        if (smartRefreshLayout.isRefreshing()) {
                            smartRefreshLayout.finishRefresh(0);
                        }
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                if (position < topticModels.size()) {
                                    Intent i = new Intent(context, TopicsDetalisActivity.class);
                                    i.putExtra("url", topticModels.get(position).getRepliceurl());
                                    i.putExtra("icon", topticModels.get(position).getImg());
                                    i.putExtra("username", topticModels.get(position).getUserName());
                                    context.startActivity(i);
                                    adapter.isTouch.set(position, true);
                                    TextView lastreplice = (TextView) view.findViewById(R.id.replice);

                                    lastreplice.setBackgroundResource(R.drawable.list_textview_replice1);
                                }

                            }
                        });
                    }
                });
    }

    /**
     * 获取主题详情
     *
     * @param isRefresh
     * @param loadingLayout
     * @param url
     * @param listView
     * @param smartRefreshLayout
     * @param inflater
     * @param Context
     */
    public static void LoadTopticdetalis(

            boolean isRefresh, final LoadingLayout loadingLayout, final String url, final ListView listView,
            final SmartRefreshLayout smartRefreshLayout,
            final LayoutInflater inflater,
            final Activity Context, final Toolbar toolbar


    ) {

        if (listView.getFooterViewsCount() == 0) {
            View view = inflater.inflate(R.layout.layout_recyclerview_list_footer_end, null);

            listView.addFooterView(view);
        }

        if (!isRefresh) {
            loadingLayout.setStatus(LoadingLayout.Loading);
        }
        final String[] t = new String[1];
        final String[] node = new String[1];
        final String[] user = new String[1];
        final String[] title = new String[1];
        final String[] img = new String[1];
        final String[] Content = new String[1];
        Internet_Manager.getInstance()
                .getTopticdetals(url.substring(2, 8))
                .map(new Func1<String, List<TopticdetalisModel>>() {
                    @Override
                    public List<TopticdetalisModel> call(String s) {
                        Document document = Jsoup.parse(s);

                        t[0] = document.select("div[class=header]").select("small[class=gray]").get(0).ownText();
                        node[0] = document.select("div[class=header]").select("a").get(2).text();
                        user[0] = document.select("div[class=header]").select("small[class=gray]").select("a").get(0).text();
                        title[0] = document.select("div[class=header]").select("h1").text();
                        img[0] = "http:" + document.select("div[class=header]").select("img").attr("src");
                        Content[0] = document.getElementsByClass("topic_content").toString();

                        topticdetal = Jsoup.parse(s).select("div[class=topic_buttons]").select("a").get(0).attr("href").substring(1);
                        repliceOnce = SomeUtils.getrepliceonce(s);
                        return HtmlToList.getdetals(s);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TopticdetalisModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Throwable", e.getMessage());
                        MyException.onError(e, loadingLayout);
                        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                LoadDate.LoadTopticdetalis(false, loadingLayout, url, listView, smartRefreshLayout, inflater, Context, toolbar);

                            }
                        });
                    }

                    @Override
                    public void onNext(List<TopticdetalisModel> topticdetalisModels) {
                        TopicsDetalisActivity.list = topticdetalisModels;

                        if (Integer.parseInt(url.substring(url.indexOf("y") + 1, url.length())) > 100) {
                            Internet_Manager.getInstance()
                                    .getTopticdetalsApi(url.substring(2, 8))
                                    .map(new Func1<String, List<TopticdetalisModel>>() {
                                        @Override
                                        public List<TopticdetalisModel> call(String s) {

                                            return HtmlToList.getjsondetals(s);
                                        }
                                    }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<List<TopticdetalisModel>>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.d("Throwable", e.getMessage());
                                            MyException.onError(e, loadingLayout);

                                        }

                                        @Override
                                        public void onNext(final List<TopticdetalisModel> topticdetalisModels) {
                                            loadingLayout.setStatus(LoadingLayout.Success);
                                            TopicsDetalisActivity.list = topticdetalisModels;

                                            if (listView.getHeaderViewsCount() == 0) {

                                                View headerview = inflater.inflate(R.layout.lv_header, null);
                                                TextView username = (TextView) headerview.findViewById(R.id.username);
                                                ImageView imageView = (ImageView) headerview.findViewById(R.id.imagView);
                                                TextView time = (TextView) headerview.findViewById(R.id.time);
                                                TextView nodename = (TextView) headerview.findViewById(R.id.nodename);
                                                TextView toptictitle = (TextView) headerview.findViewById(R.id.topictitle);
                                                RichTextView content = (RichTextView) headerview.findViewById(R.id.content);
                                                nodename.setBackgroundResource(R.drawable.list_textview_replice);
                                                nodename.setTextColor(Color.WHITE);
                                                Log.d("---", user[0] + " " + t[0]);
                                                username.setText(user[0]);
                                                time.setText(t[0]);
                                                nodename.setText(node[0]);
                                                toptictitle.setText(title[0]);

                                                LoadImg.LoadCircleImageView(img[0], imageView, Context);


                                                content.setRichText(Content[0]);

                                                listView.addHeaderView(headerview);
                                                Point p = new Point();
                                                Context.getWindowManager().getDefaultDisplay().getSize(p);
                                                int screenWidth = p.x;
                                                int screenHeight = p.y;
                                                Rect rect = new Rect(0, 0, screenWidth, screenHeight);
                                                int[] location = new int[2];
                                                toptictitle.getLocationInWindow(location);
                                                if (toptictitle.getLocalVisibleRect(rect)) {
// 控件在屏幕可见区域
                                                    toolbar.setSubtitle("");
                                                } else {
                                                    toolbar.setSubtitle(title[0]);

                                                }
                                            }
                                            smartRefreshLayout.finishRefresh(100);
                                            listView.setAdapter(new TopicRepliceAdaptar(inflater, topticdetalisModels, listView, Context));


                                        }
                                    });


                        } else {
                            loadingLayout.setStatus(LoadingLayout.Success);

                            if (listView.getHeaderViewsCount() == 0) {
                                View headerview = inflater.inflate(R.layout.lv_header, null);
                                TextView username = (TextView) headerview.findViewById(R.id.username);
                                ImageView imageView = (ImageView) headerview.findViewById(R.id.imagView);
                                TextView time = (TextView) headerview.findViewById(R.id.time);
                                TextView nodename = (TextView) headerview.findViewById(R.id.nodename);
                                TextView toptictitle = (TextView) headerview.findViewById(R.id.topictitle);
                                RichTextView content = (RichTextView) headerview.findViewById(R.id.content);
                                nodename.setBackgroundResource(R.drawable.list_textview_replice);
                                nodename.setTextColor(Color.WHITE);
                                Log.d("---", user[0] + " " + t[0]);
                                username.setText(user[0]);
                                time.setText(t[0]);
                                nodename.setText(node[0]);
                                toptictitle.setText(title[0]);
                                LoadImg.LoadCircleImageView(img[0], imageView, Context);
                                content.setRichText(Content[0]);

                                listView.addHeaderView(headerview);

                            }
                            smartRefreshLayout.finishRefresh(100);
                            Log.d("dddd", topticdetalisModels.size() + "size");

                            listView.setAdapter(new TopicRepliceAdaptar(inflater, topticdetalisModels, listView, Context));


                        }


                    }
                });


    }

}
