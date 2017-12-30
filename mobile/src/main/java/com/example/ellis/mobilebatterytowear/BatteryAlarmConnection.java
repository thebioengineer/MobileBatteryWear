package com.example.ellis.mobilebatterytowear;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BatteryAlarmConnection extends WakefulBroadcastReceiver {
    Intent backgroundBatteryMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {
        backgroundBatteryMonitor = new Intent(context, BackgroundBatteryAlarmService.class);
        context.startService(backgroundBatteryMonitor);
    }

}
