package rxjavatest.tycoding.com.iv2ex.rxjava;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
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
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.adatper.NodeTopticsAdapter;
import rxjavatest.tycoding.com.iv2ex.adatper.NoticeLvAdapter;
import rxjavatest.tycoding.com.iv2ex.adatper.TopicRepliceAdaptar;
import rxjavatest.tycoding.com.iv2ex.adatper.myrecycleadapter;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.ui.activity.MainActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.TopicsDetalisActivity;
import rxjavatest.tycoding.com.iv2ex.ui.activity.photoviewactivity;
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
                                            , edittext, button
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
                                       final EditText editText, final ImageButton button
    ) {
        final InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);


        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet inter = new intertnet(c);
                String result = inter.getTopic(string);

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
                    public void onNext(String s) {
                        Log.d("----", "next");
                        Drawable drawable = c.getResources().getDrawable(R.drawable.ic_img_load);
                        Drawable drawable1 = c.getResources().getDrawable(R.drawable.ic_load_error);
                        final List<Map<String, String>> list = htmlTolist.getdetals(s);
                        if (listView.getHeaderViewsCount() == 0) {
                            View headerview = c.getLayoutInflater().inflate(R.layout.lv_header, null);
                            TextView username = (TextView) headerview.findViewById(R.id.username);
                            ImageView imageView = (ImageView) headerview.findViewById(R.id.imagView);
                            TextView time = (TextView) headerview.findViewById(R.id.time);
                            TextView nodename = (TextView) headerview.findViewById(R.id.nodename);
                            TextView toptictitle = (TextView) headerview.findViewById(R.id.topictitle);
                            final TextView content = (TextView) headerview.findViewById(R.id.content);
                            Document document = Jsoup.parse(s);
                            if (nodetitle == null) {
                                String t = document.select("div[class=header]").select("small[class=gray]").get(0).ownText();
                                String node = document.select("div[class=header]").select("a").get(2).text();
                                String user = document.select("div[class=header]").select("small[class=gray]").select("a").get(0).text();
                                String title = document.select("div[class=header]").select("h1").text();
                                String img = "http:" + document.select("div[class=header]").select("img").attr("src");
                                username.setText(user);
                                time.setText(t);
                                nodename.setText(node);
                                toptictitle.setText(title);
                                setImg(img, imageView, c);
                            } else {
                                username.setText(user);
                                time.setText(t);
                                nodename.setText(nodetitle);
                                toptictitle.setText(title);
                                setImg(img, imageView, c);

                            }

                            String contet = document.getElementsByClass("topic_content").toString();

                            RichText.fromHtml(contet).autoFix(true)
                                    .urlClick(new OnUrlClickListener() {
                                        @Override
                                        public boolean urlClicked(String url) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            Uri uri = Uri.parse(url);
                                            intent.setData(uri);
                                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                            c.startActivity(intent);
                                            return true;
                                        }
                                    }).error(drawable1).placeHolder(drawable).clickable(true).fix(new ImageFixCallback() {
                                @Override
                                public void onInit(ImageHolder holder) {

                                }

                                @Override
                                public void onLoading(ImageHolder holder) {

                                }

                                @Override
                                public void onSizeReady(ImageHolder holder, int width, int height) {
                                    WindowManager wm = c.getWindowManager();
                                    imageloader.saveimage(holder.getSource(), c);
                              /*      int w = wm.getDefaultDisplay().getWidth();
                                    int h = wm.getDefaultDisplay().getHeight();
                                    holder.setMaxHeight(h/2);
                                    holder.setMaxWidth(w);*/
                                }

                                @Override
                                public void onImageReady(ImageHolder holder, int width, int height) {
                                    holder.setImageType(ImageHolder.ImageType.JPG);

                                    holder.setScale(2.0f);
                                }

                                @Override
                                public void onFailure(ImageHolder holder, Exception e) {

                                }

                            }).urlLongClick(new OnUrlLongClickListener() {
                                @Override
                                public boolean urlLongClick(String url) {
                                    ClipboardManager cmb = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cmb.setText(url.trim());
                                    Toast.makeText(c, "网址已复制到剪贴板", Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }).imageClick(new OnImageClickListener() {
                                @Override
                                public void imageClicked(List<String> imageUrls, int position) {
                                    Intent intent = new Intent(c, photoviewactivity.class);
                                    intent.putExtra("position", position);
                                    intent.putExtra("url", imageUrls.get(position));
                                    Log.d("---", imageUrls.get(position));
                                    c.startActivity(intent);
                                }
                            }).imageGetter(new DefaultImageGetter()).cache(CacheType.ALL).imageLongClick(new OnImageLongClickListener() {
                                @Override
                                public boolean imageLongClicked(List<String> imageUrls, int position) {


                                    return true;
                                }
                            }).into(content);
                            listView.addHeaderView(headerview);
                            TopicRepliceAdaptar adaper = new TopicRepliceAdaptar(c.getLayoutInflater(),
                                    list, listView, c);
                            listView.setAdapter(adaper);
                            refreshLayout.setRefreshing(false);


                        } else {
                            TopicRepliceAdaptar adaper = new TopicRepliceAdaptar(c.getLayoutInflater(),
                                    list, listView, c);
                            listView.setAdapter(adaper);
                            refreshLayout.setRefreshing(false);
                        }
                        final String once = intertnet.getrepliceonce(s);
                        int IndexofA = string.indexOf("/");
                        int IndexofB = string.indexOf("#");
                        final String topticid = string.substring(IndexofA + 1, IndexofB);
                        Log.d("----", once + "and" + topticid);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                editText.requestFocus();
                                editText.setText("@" + list.get(position - 1).get("username"));
                                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

                            }
                        });
                        editText.setHint("回复楼主/当前已有" + list.size() + "条评论");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                repliceToptic(c, editText.getText().toString(),
                                        topticid, refreshLayout, listView,
                                        once, img, user, nodetitle,
                                        t, string, once, editText, button,
                                        imm
                                );

                            }
                        });
                        topticdetal = Jsoup.parse(s).select("div[class=topic_buttons]").select("a").get(0).attr("href").substring(1);

                    }
                });

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
                        View v=null;
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
                             v  = activity.getLayoutInflater().inflate(R.layout.lv_footer, null);
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
                                Log.d("----", nowpage + "");
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

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                intertnet inter = new intertnet(c);

                String result = inter.getTopic(string);
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

                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(String s) {
                        refreshLayout.setRefreshing(false);
                        myrecycleadapter adapter;
                        if (isnode) {
                            adapter = new myrecycleadapter(htmlTolist.NodeTopicsToList(s), c);
                            recyclerview.setAdapter(adapter);
                        } else {
                            adapter = new myrecycleadapter(htmlTolist.TopicsToList(s), c);
                            recyclerview.setAdapter(adapter);

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
                                TextView textView = (TextView) view.findViewById(R.id.replice);
                                textView.setBackgroundResource(R.drawable.list_textview_replice1);
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


    public static void setImg(final String url, final ImageView imageView, Context context) {
        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        imageView.setBackgroundResource(R.drawable.ic_img_load);
        final File file = new File(path + "/iv2ex");
        String u = url.replace("/", "!");
        u = u.replace(".", "!");
        if (!file.exists()) {
            file.mkdirs();
        }
        final File f = new File(file, u + ".jpg");

        if (f.exists()) {
            try {
                FileInputStream fos = new FileInputStream(f);
                Bitmap bitmap = BitmapFactory.decodeStream(fos);
                Drawable drawable = new BitmapDrawable(bitmap);
                imageView.setImageDrawable(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                imageView.setBackgroundResource(R.drawable.ic_person_outline_black_24dp);
            }

        } else {

            final String finalU = u;
            Observable.create(new Observable.OnSubscribe<Bitmap>() {
                @Override
                public void call(Subscriber<? super Bitmap> subscriber) {
                    URL picUrl = null;
                    try {
                        picUrl = new URL(url);

                        Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
                        OutputStream os = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Bitmap>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Bitmap bitmap) {
                            Drawable drable = new BitmapDrawable(bitmap);
                            imageView.setImageDrawable(drable);

                        }
                    });

        }

    }

    public static void getNotice(final Activity activity, final String string, final ListView lstview, final SwipeRefreshLayout refreshLayout) {
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

                    }

                    @Override
                    public void onNext(String s) {
                        Elements pages = Jsoup.parse(s).select("div[class=box]").get(2).select("div[class=cell]").get(0).select("td").get(0).select("a");
                        final int page = Integer.parseInt(pages.get(pages.size() - 1).text());
                        final List<Map<String, String>> list = htmlTolist.getNotice(s);
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
