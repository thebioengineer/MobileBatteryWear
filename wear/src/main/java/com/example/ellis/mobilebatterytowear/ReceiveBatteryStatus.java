package com.example.ellis.mobilebatterytowear;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    public static Boolean RecievedData=false;

    @Override
    public void onDataChanged(DataEventBuffer dataevents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataevents);
        dataevents.release();
        RecievedData=true;
        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();
                if (BATTERY_MONITOR_PATH.equals(path)) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());

                    String ChargingRand = dataMapItem.getDataMap().getString("/ChargeStatus");
                    String ChargeLevelRand = dataMapItem.getDataMap().getString("/ChargeLevel");
                    Charging = Boolean.parseBoolean(ChargingRand.split(" : ")[0]);
                    ChargeLevel = Float.valueOf(ChargeLevelRand.split(" : ")[0]);

                    Log.d("DataChanged:chargeLevel",ChargeLevel+"%");
                    Log.d("DataChanged:Charging?",Charging.toString());

                    RecievedData=true;
                }
            }
        }
    }

    ;
}
