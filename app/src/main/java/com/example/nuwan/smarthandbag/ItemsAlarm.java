package com.example.nuwan.smarthandbag;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import java.util.Timer;
import java.util.TimerTask;


public class ItemsAlarm extends BroadcastReceiver{
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.PREF_SESSION_DATA,Context.MODE_PRIVATE);
        String des=prefs .getString("itemDescription","0");
        notificationManager = NotificationManagerCompat.from(context);
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.loginhandbagimage)
                .setContentTitle("Reminder")
                .setContentText(des)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                mediaPlayer.stop();
            }
        },10000);
    }
}
