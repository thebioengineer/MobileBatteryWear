package com.example.ellis.mobilebatterytowear;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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
    public static final String BATTERY_ALARM_PATH = "/Mobile_Battery_Alarm";

    private Context context;

    @Override
    public void onDataChanged(DataEventBuffer dataevents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataevents);
        dataevents.release();

        for (DataEvent event : events)
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();

                Log.d("path", path);

                if (BATTERY_MONITOR_PATH.equals(path)) {

                    Intent BatteryPowerConnection = new Intent(getApplicationContext(), BatteryPowerConnection.class);
                    boolean MonitorRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, BatteryPowerConnection, PendingIntent.FLAG_NO_CREATE) != null);
                    if (MonitorRunning == false) {
                        sendBroadcast(BatteryPowerConnection);
                    }
                }

                if (BATTERY_ALARM_PATH.equals(path)) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    Boolean SetBatteryAlarm = dataMapItem.getDataMap().getBoolean("/setAlarm");

                    Intent BatteryAlarmConnection = new Intent(getApplicationContext(), BatteryAlarmConnection.class);
                    boolean AlarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, BatteryAlarmConnection, PendingIntent.FLAG_NO_CREATE) != null);
                    Log.d("runBackground", "gotStartrun!");

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, BatteryAlarmConnection, 0);

                    if (AlarmRunning && !SetBatteryAlarm) {
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(getApplicationContext(), "Battery Alarm is off", Toast.LENGTH_SHORT).show();
                    } else if( SetBatteryAlarm) {
                        // rerun check of battery alarm every 30 seconds
                        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 3000, pendingIntent);
                        Toast.makeText(getApplicationContext(), "Battery Alarm is On", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Battery Alarm running: " + AlarmRunning,
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
    }

    ;

}
