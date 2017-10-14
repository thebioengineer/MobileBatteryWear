package com.example.ellis.mobilebatterytowear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BatteryPowerConnection extends BroadcastReceiver {
    Intent backgroundBatteryMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {
        backgroundBatteryMonitor = new Intent(context, BackgroundBatteryService.class);
        context.startService(backgroundBatteryMonitor);
    }

}
