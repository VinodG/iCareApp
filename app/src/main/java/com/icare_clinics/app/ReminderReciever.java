package com.icare_clinics.app;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Baliram.Kumar on 30-03-2017.
 */

public class ReminderReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

            int MID=0;
            long when = System.currentTimeMillis();
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent repeatt_intent=new Intent(context,MyReminder.class);
            repeatt_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeatt_intent,PendingIntent.FLAG_UPDATE_CURRENT);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder=  new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle("Medicine Reminder")
                    .setContentText("Time completed take Medicine").setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationManager.notify(100,builder.build());

    }




}
