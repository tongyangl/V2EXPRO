package rxjavatest.tycoding.com.iv2ex.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.zzhoujay.richtext.ig.DefaultImageGetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.ui.widget.PinchImageView;

/**
 * Created by 佟杨 on 2017/4/9.
 */

public class imageloader {


    public static void saveimage(final String url, final Activity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
                final File file = new File(path + "/iv2ex");
                String u = url.replace("/", "!");
                u = u.replace(".", "!");
                if (!file.exists()) {
                    file.mkdirs();
                }
                final File f = new File(file, u+".jpg");
                if (!f.exists()) {
                    URL picUrl = null;
                    try {
                        picUrl = new URL(url);

                        Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
                        OutputStream os = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        Log.d("===",url);
                        os.flush();
                        os.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        ).start();

    }

    public static void dispalyimage(final String url, final Activity activity, final PinchImageView imageView) {

        String path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        final File file = new File(path + "/iv2ex");
        String u = url.replace("/", "!");
        u = u.replace(".", "!");
        if (!file.exists()) {
            file.mkdirs();
        }
        final File f = new File(file, u+".jpg");

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

                        }
                    });


        }
    }

}
