package rxjavatest.tycoding.com.iv2ex.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

import rxjavatest.tycoding.com.iv2ex.receiver.MyReceiver;

/**
 * Created by 佟杨 on 2017/4/15.
 */

public class noticeservice extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("---","oncreat");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int Minutes = 1;
        long triggerAtTime = SystemClock.elapsedRealtime() + Minutes;
        Intent i = new Intent(this, MyReceiver.class);
        Log.d("---",System.currentTimeMillis()+"");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, pi);
        Log.d("---",(System.currentTimeMillis()+1000)+"");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("---","destroy");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, MyReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
