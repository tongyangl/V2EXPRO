package rxjavatest.tycoding.com.iv2ex.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by 佟杨 on 2017/5/7.
 */

public class MyImageLoader {
    private LruCache<String, Bitmap> lruCache;
    private Context context;
    private String ImageUrl;
    private Bitmap tBitmap;
    private volatile static MyImageLoader instance;
    private int maxMemery = (int) (Runtime.getRuntime().maxMemory() /1024/8);

    public static MyImageLoader getInstance(){
        if (instance == null) {
            synchronized (MyImageLoader.class) {
                if (instance == null) {
                    instance = new MyImageLoader();
                }
            }
        }
        return instance;
    }
    public Bitmap LoadImage(String imageUrl,Context context) {
        this.ImageUrl=imageUrl;
        this.context=context;
        lruCache=new LruCache<String, Bitmap>(maxMemery){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return  value.getRowBytes()*value.getHeight()/1024;
            }
        };
        if (LoadImgFromlruCache() != null) {
            Log.d("ssss","LoadImgFromlruCache");
            return LoadImgFromlruCache();
        } else if (LoadImgFromFile() != null) {
            Log.d("ssss","LoadImgFromFile");

            return LoadImgFromFile();
        } else if (LoadImgFromNet()!=null){
            Log.d("ssss","LoadImgFromNet");

            return LoadImgFromNet();
        }else {
            Log.d("ssss","null");

            return null;
        }


    }

    private Bitmap LoadImgFromNet() {

        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        ImageUrl.replace("/", "");
        ImageUrl.replace(".", "");
        final File f = new File(path, ImageUrl + ".jpg");

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL picUrl = null;
                try {
                    picUrl = new URL(ImageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
                    Log.d("ssss","nnnn");
                    lruCache.put(ImageUrl, bitmap);
                    OutputStream os = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        return tBitmap;

    }

    private Bitmap LoadImgFromFile() {

        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        ImageUrl.replace("/", "");
        ImageUrl.replace(".", "");
        File f = new File(path, ImageUrl + ".jpg");
        if (!f.exists()) {
            return null;
        } else {

            try {
                FileInputStream fos = new FileInputStream(f);
                Bitmap mBitmap = BitmapFactory.decodeStream(fos);

                return mBitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }


    }

    private Bitmap LoadImgFromlruCache() {
        if (lruCache.get(ImageUrl) != null) {

            Bitmap bitmap = lruCache.get(ImageUrl);

            return bitmap;
        } else {

            return null;
        }


    }

}
