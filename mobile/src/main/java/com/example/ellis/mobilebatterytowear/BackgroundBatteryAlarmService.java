package com.example.ellis.mobilebatterytowear;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BackgroundBatteryAlarmService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    BatteryStatus batteryStatusObject;
    BatteryWearConnection wearConnection;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(batteryLevelTask);
        batteryStatusObject = new BatteryStatus();
        wearConnection = new BatteryWearConnection();
        wearConnection.onCreate(this);
        Log.d("OnCreate","BackgroundBattService Ran");
    }

    private Runnable batteryLevelTask = new Runnable() {
        @Override
        public void run() {
            //Calculate and send battery level and charging status to wear
            calculateBatteryInformation();
            sendFullNotification();
            stopSelf();
        }

        public void calculateBatteryInformation() {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level * 100 / (float) scale;

            batteryStatusObject.updateStatus(batteryPct, isCharging);
            Log.d("BatteryAlarm","Calculating");
        }

        public void sendFullNotification() {
            if(batteryStatusObject.ChargeLevel==69 && batteryStatusObject.Charging) {
                wearConnection.sendNotificationFullStatus();

                Intent BatteryAlarmConnection = new Intent(context, BatteryAlarmConnection.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0, BatteryAlarmConnection, 0);

                alarmManager.cancel(pendingIntent);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
        wearConnection.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }
}
