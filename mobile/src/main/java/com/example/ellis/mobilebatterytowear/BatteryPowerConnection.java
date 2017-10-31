package com.example.ellis.mobilebatterytowear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BatteryPowerConnection extends WakefulBroadcastReceiver {
    Intent backgroundBatteryMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {
        backgroundBatteryMonitor = new Intent(context, BackgroundBatteryService.class);
        context.startService(backgroundBatteryMonitor);
    }

}
