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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;


import java.util.List;

/**
 * Created by Ellis on 10/14/2017.
 */

public class ReceiveBatteryStatus extends WearableListenerService {

    final String BATTERY_MONITOR_PATH = "/BATTERY_MONITORING_PATH";
    final String BATTERY_ALARM_PATH = "/BATTERY_ALARM_PATH";

    private Context context;

    public static Boolean Charging;
    public static Float ChargeLevel;
    public static Boolean RecievedData=false;
    BatteryFullChargeNotification Notification = new BatteryFullChargeNotification();



    @Override
    public void onDataChanged(DataEventBuffer dataevents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataevents);
        dataevents.release();
        RecievedData=false;
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

                if (BATTERY_ALARM_PATH.equals(path)) {
                    Notification.getContext(context);
                    Notification.sendNotification();
                }
            }
        }
    }

    ;
}
