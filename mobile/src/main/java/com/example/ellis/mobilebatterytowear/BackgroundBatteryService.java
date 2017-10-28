package com.example.ellis.mobilebatterytowear;

import android.app.*;
import android.content.*;
import android.os.*;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BackgroundBatteryService extends Service {

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
    }

    private Runnable batteryLevelTask = new Runnable() {
        @Override
        public void run() {
            //Calculate and send battery level and charging status to wear
            calculateBatteryInformation();
            sendBatteryInformation();
            stopSelf();
        }

        public void calculateBatteryInformation() {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level / (float) scale;
            batteryStatusObject.updateStatus(batteryPct, isCharging);
        }

        public void sendBatteryInformation() {
            wearConnection.sendBatteryStatus(batteryStatusObject);
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
