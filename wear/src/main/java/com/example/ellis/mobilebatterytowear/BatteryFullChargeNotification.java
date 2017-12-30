package com.example.ellis.mobilebatterytowear;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ellis on 12/29/17.
 */

public class BatteryFullChargeNotification {

    int notificationId = 9917;
    String id = "BatteryAlarm";
    Context context;

    void getContext(Context contextIn){
        context=contextIn;
    }


    void sendNotification(){
        Log.d("RecievedFull", "sendNotification");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("WearBattery")
                        .setContentText("Battery Fully Charged!")
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        // Sets an ID for the notification
        int mNotificationId = 9917;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

        SharedPreferences settings = context.getSharedPreferences("Mobile_Battery_Alarm", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("AlarmStatus", false);
        editor.commit();

    }
}
