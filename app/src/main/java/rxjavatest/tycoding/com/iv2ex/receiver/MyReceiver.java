package rxjavatest.tycoding.com.iv2ex.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import rxjavatest.tycoding.com.iv2ex.R;
import rxjavatest.tycoding.com.iv2ex.service.noticeservice;
import rxjavatest.tycoding.com.iv2ex.ui.activity.NoticeActivity;

/**
 * Created by 佟杨 on 2017/4/15.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent2=new Intent(context,NoticeActivity.class);
        PendingIntent intent1=PendingIntent.getActivity(context,0,intent2,0);

        NotificationManager manager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("title")
                .setContentText("describe")
                .setContentIntent(intent1)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();
        notification.flags=Notification.FLAG_AUTO_CANCEL;
        manager.notify(1,notification);
        //再次开启LongRunningService这个服务，从而可以
        Intent i = new Intent(context, noticeservice.class);
        context.startService(i);
    }
}
