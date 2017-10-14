package com.example.ellis.mobilebatterytowear;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.common.api.ResultCallback;

import java.util.Random;


/**
 * Created by Ellis on 10/11/2017.
 */

public class BatteryMonitor_Service extends Service {

    public static final String BATTERY_MONITOR_PATH = "/Mobile_Battery_Request";

    GoogleApiClient mGoogleApiClient;
    int mobileBatteryLevel;
    int mobileBatteryStatus;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void requestBatteryPercent_and_Status() {

        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(BATTERY_MONITOR_PATH);
        putDataMapRequest.getDataMap().putInt("/notifiertoupdate", new Random(5000).nextInt());
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        if (!mGoogleApiClient.isConnected())
            return;
        DataApi.DataItemResult dataItemResult = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request).await();

    }

    public int BatteryPercent() {
        Float battPercent = ReceiveBatteryStatus.ChargeLevel;
        if (battPercent != null) {
            return battPercent.intValue();
        } else {
            return 0;
        }

    }

    public int BatteryStatus() {
        Boolean battStatus = ReceiveBatteryStatus.Charging;
        Float battPercent = ReceiveBatteryStatus.ChargeLevel;
        if (battStatus) {
            if (battPercent < 100) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }

    }



}
