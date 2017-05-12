package rxjavatest.tycoding.com.iv2ex.rxjava;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.pagestate.PageManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;
import com.zzhoujay.richtext.callback.OnUrlLongClickListener;
import com.zzhoujay.richtext.ig.DefaultImageGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rxjavatest.tycoding.com.iv2ex.BaseApplication;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodeTopticsAdapter;
import rxjavatest.tycoding.com.iv2ex.adatper.NoticeLvAdapter;
import rxjavatest.tycoding.com.iv2ex.adatper.TopicRepliceAdaptar;
import rxjavatest.tycoding.com.iv2ex.adatper.myrecycleadapter;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.ui.activity.MainActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.NoticeActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.SiginActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.TopicsDetalisActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.photoviewactivity;
import rxjavatest.tycoding.com.iv2ex.ui.widget.RichTextView;
import rxjavatest.tycoding.com.iv2ex.utils.htmlTolist;
import rxjavatest.tycoding.com.iv2ex.utils.imageloader;
import rxjavatest.tycoding.com.iv2ex.utils.tyutils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by 佟杨 on 2017/4/6.
 */

public class rxjava {
    public static String topticdetal = "";
    private static int indexpage = -1;
    private static int nowpage = -1;
    public static String nodetoptics = "";
    private static int topticpage = -1;
    private static List<Map<String, String>> tlist;
    private static List<Map<String, String>> alllist;
    private static TopicRepliceAdaptar adaper;
    private static String s;

    public static void repliceToptic(final Activity activity, final String content,
                                     final String topticid, final SwipeRefreshLayout swipeRefreshLayout,
                                     final ListView listView,
                                     final String title, final String img, final String user,
                                     final String nodetitle, final String t, final String string,
                                     final String once,
                                     final EditText edittext, final ImageButton button,
                                     final InputMethodManager mm
    ) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle("回复中");
        dialog.show();
        Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                intertnet net = new intertnet(activity);
                int code = net.replice(content, topticid, once);
                subscriber.onNext(code);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(activity, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Integer s) {
                        if (s == 302) {
                            Toast.makeText(activity, "回复成功", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    rxjava.getTopticDetils(activity, string, swipeRefreshLayout,
                                            listView, title, img, user, nodetitle, t
                                            , edittext, button, true
                                    );
                                    listView.smoothScrollToPosition(listView.getCount() - 1);
                                }
                            };
                            swipeRefreshLayout.setOnRefreshListener(listener);

                            swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(true);
                                }
                            });
                            listener.onRefresh();
                            edittext.setText("");
                            if (mm.isActive()) {
                                mm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);// 隐藏输入法
                            }

                        } else {
                            Toast.makeText(activity, "回复失败", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });

    }

    public static void getTopticDetils(final Activity c, final String string,
                                       final SwipeRefreshLayout refreshLayout,
                                       final ListView listView,
                                       final String title, final String img, final String user,
                                       final String nodetitle, final String t,
                                       final EditText editText, final ImageButton button, final boolean isture
    ) {
        final InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);


        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet inter = new intertnet(c);
                String result = inter.getTopic(string);
                Log.d("---", string);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("----", "onError" + e.getMessage());

                        refreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onNext(final String s) {
                        tlist = Collections.synchronizedList(new ArrayList<Map<String, String>>());
                        if (listView.getHeaderViewsCount() == 0) {
                            View headerview = c.getLayoutInflater().inflate(R.layout.lv_header, null);
                            TextView username = (TextView) headerview.findViewById(R.id.username);
                            ImageView imageView = (ImageView) headerview.findViewById(R.id.imagView);
                            TextView time = (TextView) headerview.findViewById(R.id.time);
                            TextView nodename = (TextView) headerview.findViewById(R.id.nodename);
                            TextView toptictitle = (TextView) headerview.findViewById(R.id.topictitle);
                            final RichTextView content = (RichTextView) headerview.findViewById(R.id.content);
                            Document document = Jsoup.parse(s);
                            nodename.setBackgroundResource(R.drawable.list_textview_replice);
                            nodename.setTextColor(Color.WHITE);
                            if (nodetitle == null) {
                                String t = document.select("div[class=header]").select("small[class=gray]").get(0).ownText();
                                String node = document.select("div[class=header]").select("a").get(2).text();
                                String user = document.select("div[class=header]").select("small[class=gray]").select("a").get(0).text();
                                String title = document.select("div[class=header]").select("h1").text();
                                String img = "http:" + document.select("div[class=header]").select("img").attr("src");
                                username.setText(user);

                                Log.d("----", t);
                                time.setText(t);
                                nodename.setText(node);
                                toptictitle.setText(title);
                                ImageLoader.getInstance().displayImage(img, imageView);
                            } else {
                                username.setText(user);
                                time.setText(t);
                                nodename.setText(nodetitle);
                                toptictitle.setText(title);
                                ImageLoader.getInstance().displayImage(img, imageView);

                            }
                            String contet = document.getElementsByClass("topic_content").toString();
                          content.setRichText(contet);

                            listView.addHeaderView(headerview);

                        }

                        Document document1 = Jsoup.parse(s);
                        Elements elements = document1.select("div[id=Main]").select("div[class=box]");
                        if (elements.get(1).select("div").hasClass("cell")) {
                            Elements elements1 = elements.get(1).select("div[class=cell]");
                            Log.d("---", elements1.get(1).id() + "id");
                            if (elements1.get(1).id().contains("r")) {
                                Observable.create(new Observable.OnSubscribe<List<Map<String,String>>>() {
                                    @Override
                                    public void call(Subscriber<? super List<Map<String, String>>> subscriber) {
                                        List<Map<String,String>>list=htmlTolist.getdetals(s);
                                       subscriber.onNext(list);
                                        subscriber.onCompleted();
                                    }
                                }).subscribeOn(Schedulers.computation())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<List<Map<String, String>>>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                refreshLayout.setRefreshing(false);
                                            }

                                            @Override
                                            public void onNext(List<Map<String, String>> maps) {

                                                tlist=maps;
                                                final String once = intertnet.getrepliceonce(s);
                                                int IndexofA = string.indexOf("/");
                                                int IndexofB = string.indexOf("#");
                                                final String topticid = string.substring(IndexofA + 1, IndexofB);
                                                alllist = Collections.synchronizedList(new ArrayList<Map<String, String>>());
                                                alllist = tlist;
                                                if (tlist.size() > 20)
                                                    tlist = tlist.subList(0, 20);
                                                final int page;
                                                if (alllist.size() % 20 == 0) {
                                                    page = alllist.size() / 20;
                                                } else {
                                                    page = (alllist.size() / 20) + 1;
                                                }

                                                topticpage = 1;
                                                Log.d("---", page + "ddd");
                                                adaper = new TopicRepliceAdaptar(c.getLayoutInflater(), tlist
                                                        , listView, c);
                                                listView.setAdapter(adaper);
                                                refreshLayout.setRefreshing(false);
                                                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                                    @Override
                                                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                                                        switch (scrollState) {
                                                            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 当不迁移转变时
                                                                if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                                                    if (topticpage < page) {
                                                                        topticpage++;
                                                                        getTopticNext(alllist, tlist, adaper, page, topticpage);
                                                                        Log.d("---", topticpage + "sad");
                                                                    }
                                                                }
                                                        }
                                                    }

                                                    @Override
                                                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                                                    }
                                                });


                                                button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (BaseApplication.islogin(c)) {
                                                            repliceToptic(c, editText.getText().toString(),
                                                                    topticid, refreshLayout, listView,
                                                                    once, img, user, nodetitle,
                                                                    t, string, once, editText, button,
                                                                    imm
                                                            );
                                                        } else {
                                                            Intent intent3 = new Intent(c, SiginActivity.class);
                                                            c.startActivity(intent3);
                                                        }


                                                    }
                                                });
                                                editText.setHint("回复楼主/当前已有" + alllist.size() + "条评论");

                                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        if (position > 0) {
                                                            editText.requestFocus();
                                                            imm.toggleSoftInput(0, InputMethod.SHOW_FORCED);
                                                            editText.setText("@" + alllist.get(position - 1).get("username"));
                                                        }


                                                    }
                                                });
                                            }
                                        });


                            } else {
                                new AsyncTask<Void, Void, String>() {
                                    @Override
                                    protected void onPostExecute(final String s) {
                                        super.onPostExecute(s);
                                        Observable.create(new Observable.OnSubscribe<List<Map<String,String>>>() {
                                            @Override
                                            public void call(Subscriber<? super List<Map<String,String>>> subscriber) {
                                                List<Map<String,String>>list=htmlTolist.getjsondetals(s);
                                                subscriber.onNext(list);
                                                subscriber.onCompleted();

                                            }
                                        }).subscribeOn(Schedulers.computation())
                                                .observeOn(AndroidSchedulers.mainThread())

                                                .subscribe(new Subscriber<List<Map<String, String>>>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onNext(List<Map<String, String>> maps) {
                                                        tlist=maps;
                                                        final String once = intertnet.getrepliceonce(s);
                                                        int IndexofA = string.indexOf("/");
                                                        int IndexofB = string.indexOf("#");
                                                        final String topticid = string.substring(IndexofA + 1, IndexofB);
                                                        alllist = Collections.synchronizedList(new ArrayList<Map<String, String>>());
                                                        alllist = tlist;
                                                        if (tlist.size() > 20)
                                                            tlist = tlist.subList(0, 20);
                                                        final int page;
                                                        if (alllist.size() % 20 == 0) {
                                                            page = alllist.size() / 20;
                                                        } else {
                                                            page = (alllist.size() / 20) + 1;

                                                        }

                                                        topticpage = 1;

                                                        adaper = new TopicRepliceAdaptar(c.getLayoutInflater(), tlist
                                                                , listView, c);
                                                        listView.setAdapter(adaper);
                                                        refreshLayout.setRefreshing(false);
                                                        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                                            @Override
                                                            public void onScrollStateChanged(AbsListView view, int scrollState) {

                                                                switch (scrollState) {
                                                                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 当不迁移转变时
                                                                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                                                            if (topticpage < page) {
                                                                                topticpage++;
                                                                                getTopticNext(alllist, tlist, adaper, page, topticpage);
                                                                                Log.d("---", topticpage + "sad");
                                                                            }
                                                                        }
                                                                }
                                                            }

                                                            @Override
                                                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                                                            }
                                                        });


                                                        button.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (BaseApplication.islogin(c)) {
                                                                    repliceToptic(c, editText.getText().toString(),
                                                                            topticid, refreshLayout, listView,
                                                                            once, img, user, nodetitle,
                                                                            t, string, once, editText, button,
                                                                            imm
                                                                    );
                                                                } else {
                                                                    Intent intent3 = new Intent(c, SiginActivity.class);
                                                                    c.startActivity(intent3);
                                                                }


                                                            }
                                                        });
                                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                editText.requestFocus();
                                                                imm.toggleSoftInput(0, InputMethod.SHOW_FORCED);
                                                                editText.setText("@" + alllist.get(position - 1).get("username"));
                                                            }
                                                        });
                                                        editText.addTextChangedListener(new TextWatcher() {
                                                            @Override
                                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                            }

                                                            @Override
                                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                Log.d("----",s.toString()+"onTextChanged");
                                                            }

                                                            @Override
                                                            public void afterTextChanged(Editable s) {
                                                                Log.d("----",s.toString()+"afterTextChanged");
                                                            }
                                                        });
                                                        editText.setHint("回复楼主/当前已有" + alllist.size() + "条评论");
                                                    }
                                                });


                                    }

                                    @Override
                                    protected String doInBackground(Void... params) {

                                        String url = "https://www.v2ex.com/api/replies/show.json?topic_id=" + string.substring(2, 8);
                                        intertnet net = new intertnet(c);
                                        Log.d("===", string.substring(2, 8));
                                        tlist = htmlTolist.getjsondetals(net.getNodetoptic(url));
                                        return net.getNodetoptic(url);
                                    }
                                }.execute();

                            }


                        } else {
                            adaper = new TopicRepliceAdaptar(c.getLayoutInflater(), tlist
                                    , listView, c);
                            listView.setAdapter(adaper);
                            refreshLayout.setRefreshing(false);
                            final String once = intertnet.getrepliceonce(s);
                            int IndexofA = string.indexOf("/");
                            int IndexofB = string.indexOf("#");
                            final String topticid = string.substring(IndexofA + 1, IndexofB);

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (BaseApplication.islogin(c)) {
                                        repliceToptic(c, editText.getText().toString(),
                                                topticid, refreshLayout, listView,
                                                once, img, user, nodetitle,
                                                t, string, once, editText, button,
                                                imm
                                        );
                                    } else {
                                        Intent intent3 = new Intent(c, SiginActivity.class);
                                        c.startActivity(intent3);
                                    }

                                }
                            });
                            editText.setHint("回复楼主/当前已有" + alllist.size() + "条评论");
                        }
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                              /* if (s.toString().contains("@")){
                                   AlertDialog.Builder builder=new AlertDialog.Builder(c);
                                   View view=c.getLayoutInflater().inflate(R.layout.)


                               }*/
                            }
                        });
                        if (isture)
                            listView.setSelection(listView.getCount());
                        topticdetal = Jsoup.parse(s).select("div[class=topic_buttons]").select("a").get(0).attr("href").substring(1);

                    }
                });

    }

    public static void getTopticNext(List<Map<String, String>> alllist, List<Map<String, String>> list, TopicRepliceAdaptar adaptar, int pages, int now) {
        Log.d("---", alllist.size() + "aaa");

        if (pages == now) {
            adaptar.MyNotify(alllist);
        } else {
            adaptar.MyNotify(alllist.subList(0, 20 * now));
        }


    }


    public static void getNodetoptics(final Activity activity, final String string,
                                      final SwipeRefreshLayout swipeRefreshLayout,
                                      final ListView listView,
                                      final String num
    ) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet inter = new intertnet(activity);
                String url = "https" + string.substring(4);
                String result = inter.getNodetoptic(url);
                subscriber.onNext(result);
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("----", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("---", e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        final int page;
                        final List<Map<String, String>> list;
                        list = htmlTolist.NodeTopicsToList(s);
                        final NodeTopticsAdapter adapter = new NodeTopticsAdapter(list, activity);
                        Elements elements;
                        View v = null;
                        if (list.size() > 0) {
                            if (Integer.parseInt(num) > 20) {
                                elements = Jsoup.parse(s).select("div[class=cell]").get(4).select("a");
                                String p = "";
                                p = elements.get(elements.size() - 1).text();
                                if (!tyutils.isNumeric(p)) {
                                    elements = Jsoup.parse(s).select("div[class=cell]").get(5).select("a");
                                    p = elements.get(elements.size() - 1).text();
                                }
                                if (!tyutils.isNumeric(p)) {
                                    elements = Jsoup.parse(s).select("div[class=cell]").get(3).select("a");
                                    p = elements.get(elements.size() - 1).text();
                                }
                                Log.d("ppp", p + "");
                                page = Integer.parseInt(p);
                                v = activity.getLayoutInflater().inflate(R.layout.lv_footer, null);
                                listView.addFooterView(v);

                            } else {
                                page = -2;
                            }

                        } else {
                            page = -2;
                            Toast.makeText(activity, "本节点暂无主题", Toast.LENGTH_SHORT).show();

                        }
                        listView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        if (page > 1)
                            nowpage = 1;
                        final View finalV = v;
                        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
                                switch (scrollState) {
                                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 当不迁移转变时
                                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                            if (nowpage < page && nowpage != -2) {
                                                nowpage++;
                                                getNodepage(list, adapter, nowpage, activity, "https" + string.substring(4));
                                            } else if (nowpage == page) {
                                                listView.removeFooterView(finalV);
                                                Toast.makeText(activity, "已经加载全部信息", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                }
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                            }
                        });
                        nodetoptics = Jsoup.parse(s).select("div[class=fr f12]").select("a").attr("href").substring(1);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(activity, TopicsDetalisActivity.class);
                                intent.putExtra("repliceurl", list.get(position).get("repliceurl"));
                                activity.startActivity(intent);
                            }
                        });
                    }

                });
    }

    public static void getToptics(final Activity c, final String string, final SwipeRefreshLayout refreshLayout, final RecyclerView recyclerview, final boolean isnode) {
        s = "";
        Observable.create(new Observable.OnSubscribe<List<Map<String, String>>>() {
            @Override
            public void call(Subscriber<? super List<Map<String, String>>> subscriber) {
                intertnet inter = new intertnet(c);
                String result = inter.getTopic(string);
                s = result;
                if (isnode) {
                    subscriber.onNext(htmlTolist.NodeTopicsToList(result));

                } else {
                    subscriber.onNext(htmlTolist.TopicsToList(result));

                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Map<String, String>>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("----", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("---", e.getMessage());
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<Map<String, String>> list) {
                        refreshLayout.setRefreshing(false);
                        myrecycleadapter adapter;
                        if (isnode) {
                            adapter = new myrecycleadapter(list, c);
                            recyclerview.setAdapter(adapter);
                        } else {
                            adapter = new myrecycleadapter(list, c);
                            recyclerview.setAdapter(adapter);
                        }

                        String notice = Jsoup.parse(s).select("a[class=fade]").text();
                        String a = "";
                        for (int i = 0; i < notice.length(); i++) {
                            if (notice.charAt(i) >= 48 && notice.charAt(i) <= 57) {
                                a += notice.charAt(i);
                            }
                        }
                        if (Integer.parseInt(a) > 0) {

                            Intent intent2 = new Intent(c, NoticeActivity.class);
                            PendingIntent intent1 = PendingIntent.getActivity(c, 0, intent2, 0);

                            NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
                            Notification notification = new Notification.Builder(c)
                                    .setAutoCancel(true)
                                    .setContentTitle("提示")
                                    .setContentText("您有" + a + "条未读提醒")
                                    .setContentIntent(intent1)
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .setWhen(System.currentTimeMillis())
                                    .build();
                            notification.flags = Notification.FLAG_AUTO_CANCEL;
                            manager.notify(1, notification);
                        }

                        adapter.setOnItemClickListener(new myrecycleadapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Map<String, String> data) {

                                Intent intent = new Intent(c, TopicsDetalisActivity.class);
                                intent.putExtra("repliceurl", data.get("repliceurl"));
                                intent.putExtra("nodetitle", data.get("nodetitle"));
                                intent.putExtra("time", data.get("time"));
                                intent.putExtra("username", data.get("username"));
                                intent.putExtra("img", data.get("img"));
                                intent.putExtra("title", data.get("title"));
                                c.startActivity(intent);
                            }
                        });

                    }
                });

    }

    public static void login(final String username, final String password, final Activity activity, final ProgressDialog dialog) {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet net = new intertnet(activity);
                String result = net.login(username, password);

                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(String s) {
                        Document document = Jsoup.parse(s);
                        Elements elements = document.select("div[id=Rightbar]").select("div[class=box]");
                        Log.d("----", elements.size() + "");
                        String userimg = "http://" + elements.get(0).select("img").attr("src").substring(2);
                        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Log.d("===", userimg);
                        editor.putString("userimg", userimg);
                        editor.commit();
                        if (elements.size() == 8 | elements.size() == 7) {
                            dialog.dismiss();
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            Toast.makeText(activity.getApplicationContext(), "欢迎回来" + username, Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else {
                            dialog.dismiss();

                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle("提示");
                            builder.setMessage("登陆遇到问题，请检查用户名密码");
                            builder.setPositiveButton("确定", null);
                            builder.show();
                        }

                    }
                });


    }




    public static void getNotice(final Activity activity, final String string, final ListView lstview, final SwipeRefreshLayout refreshLayout, final TextView textView) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet net = new intertnet(activity);
                String result = net.getTopic(string);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("好像错了了呢,点击重试");
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rxjava.getNotice(activity, tyutils.NOTIFI_URL, lstview,refreshLayout,textView);

                            }
                        });
                    }

                    @Override
                    public void onNext(String s) {
                        if (textView.isShown()){
                            textView.setVisibility(View.GONE);
                        }
                        Elements pages = Jsoup.parse(s).select("div[class=box]").get(2).select("div[class=cell]").get(0).select("td").get(0).select("a");
                        final int page = Integer.parseInt(pages.get(pages.size() - 1).text());
                        final List<Map<String, String>> list = htmlTolist.getNotice(s);
                        if (list.size() == 0) {
                            refreshLayout.setRefreshing(false);
                            PageManager pageManager = PageManager.init(lstview, "空空如也，什么也没有", false, new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            pageManager.showEmpty();
                        } else {
                            final NoticeLvAdapter adapter = new NoticeLvAdapter(list, activity);
                            final View v = activity.getLayoutInflater().inflate(R.layout.lv_footer, null);
                            lstview.addFooterView(v);
                            lstview.setAdapter(adapter);

                            refreshLayout.setRefreshing(false);
                            if (page > 0)
                                indexpage = 1;
                            lstview.setOnScrollListener(new AbsListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {

                                    switch (scrollState) {

                                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 当不迁移转变时
                                            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                                if (indexpage < page && indexpage != -1) {
                                                    indexpage++;

                                                    getnoticepage(list, adapter, indexpage, activity);
                                                } else if (indexpage == page) {
                                                    lstview.removeFooterView(v);
                                                    Toast.makeText(activity, "已经加载全部信息", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                    }


                                }

                                @Override
                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                                }
                            });
                            lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(activity, TopicsDetalisActivity.class);
                                    intent.putExtra("repliceurl", list.get(position).get("url"));
                                    activity.startActivity(intent);
                                }
                            });
                        }

                    }
                });


    }

    public static void getNodepage(final List<Map<String, String>> list, final NodeTopticsAdapter adapter, int page, final Activity activity, String s) {
        final String url = s + "?p=" + page;
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet net = new intertnet(activity);
                String b = net.getNodetoptic(url);
                Log.d("asdasd", b + "asd");
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                        list.addAll(htmlTolist.NodeTopicsToList(s));

                        adapter.notifyDataSetChanged();
                    }
                });

    }

    public static void getnoticepage(final List<Map<String, String>> list, final NoticeLvAdapter adapter, int page, final Activity activity) {
        final String url = "notifications?p=" + page;
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet net = new intertnet(activity);
                String b = net.getTopic(url);
                subscriber.onNext(b);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        list.addAll(htmlTolist.getNotice(s));
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
