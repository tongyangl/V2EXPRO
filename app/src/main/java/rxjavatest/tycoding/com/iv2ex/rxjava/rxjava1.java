package rxjavatest.tycoding.com.iv2ex.rxjava;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rxjavatest.tycoding.com.iv2ex.internet.intertnet;
import rxjavatest.tycoding.com.iv2ex.utils.htmlTolist;

/**
 * Created by 佟杨 on 2017/4/10.
 */

public class rxjava1 {

    public static void getnodeToptics(final Activity c, final String string, final SwipeRefreshLayout refreshLayout, final RecyclerView recyclerview, final boolean isnode) {

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

                    }

                    @Override
                    public void onNext(String s) {
                        
                        htmlTolist.NodeTopicsToList(s);
                    }
                });

    }
}
