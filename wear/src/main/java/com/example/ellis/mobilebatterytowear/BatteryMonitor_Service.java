package com.example.ellis.mobilebatterytowear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
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

    public void onCreate(GoogleApiClient mGoogleApiClientIN) {
        super.onCreate();
        mGoogleApiClient=mGoogleApiClientIN;
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
        int BatteryNotifierInt = new Random().nextInt(5000);
        putDataMapRequest.getDataMap().putInt("/notifiertoupdate", BatteryNotifierInt);
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        if (!mGoogleApiClient.isConnected())
            return;
        PendingResult<DataApi.DataItemResult> pendingResult  = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request);

    }

    public int BatteryPercent() {
        Float battPercent = null;
        if(ReceiveBatteryStatus.RecievedData) {
            battPercent = ReceiveBatteryStatus.ChargeLevel;
        }
        if (battPercent != null) {
            return battPercent.intValue();
        } else {
            return 0;
        }

    }

    public int BatteryStatus() {
        Float battPercent = null;
        Boolean battStatus = null;

        if(ReceiveBatteryStatus.RecievedData) {
            battPercent = ReceiveBatteryStatus.ChargeLevel;
            battStatus = ReceiveBatteryStatus.Charging;
        }

        if(battStatus==null){
            return -1 ;
        }

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
