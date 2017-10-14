package com.example.ellis.mobilebatterytowear;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BatteryWearConnection extends Service {

    GoogleApiClient mGoogleApiClient;
    final String BATTERY_MONITOR_PATH = "/BATTERY_MONITORING_PATH";


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

    public void sendBatteryStatus(BatteryStatus battStatus) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(BATTERY_MONITOR_PATH);
        putDataMapRequest.getDataMap().putBoolean("/ChargeStatus", battStatus.Charging);
        putDataMapRequest.getDataMap().putFloat("/ChargeLevel", battStatus.ChargeLevel);
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        if (!mGoogleApiClient.isConnected())
            return;

        DataApi.DataItemResult dataItemResult = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request).await();
    }
}
