package com.example.ellis.mobilebatterytowear;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;

/**
 * Created by Ellis on 10/14/2017.
 */

public class ReceiveBatteryStatus extends WearableListenerService {

    final String BATTERY_MONITOR_PATH = "/BATTERY_MONITORING_PATH";
    private Context context;

    public static Boolean Charging;
    public static Float ChargeLevel;

    @Override
    public void onDataChanged(DataEventBuffer dataevents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataevents);
        dataevents.release();

        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();
                if (BATTERY_MONITOR_PATH.equals(path)) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());

                    Charging = dataMapItem.getDataMap().getBoolean("/ChargeStatus");
                    ChargeLevel = dataMapItem.getDataMap().getFloat("/ChargeLevel");
                }
            }
        }
    }

    ;
}
