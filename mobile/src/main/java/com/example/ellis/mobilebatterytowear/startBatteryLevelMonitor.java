package com.example.ellis.mobilebatterytowear;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ellis on 10/14/2017.
 */

public class startBatteryLevelMonitor extends WearableListenerService {

    public static final String BATTERY_MONITOR_PATH = "/Mobile_Battery_Request";
    private Context context;

    @Override
    public void onDataChanged(DataEventBuffer dataevents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataevents);
        dataevents.release();

        for (DataEvent event : events) {

            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();

                Log.d("path",path);

                if (BATTERY_MONITOR_PATH.equals(path)) {

                    Intent BatteryPowerConnection = new Intent(getApplicationContext(), BatteryPowerConnection.class);
                    boolean MonitorRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, BatteryPowerConnection, PendingIntent.FLAG_NO_CREATE) != null);
                    Log.d("runBackground","gotStartrun!");
                    if (MonitorRunning == false) {
                        Log.d("runBackground","getBatteryLevel!");
                        sendBroadcast(BatteryPowerConnection);
                    }
                }
            }
        }
    }

    ;

}
