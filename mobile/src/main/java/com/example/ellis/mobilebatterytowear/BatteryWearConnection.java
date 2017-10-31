package com.example.ellis.mobilebatterytowear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Random;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BatteryWearConnection extends Service {

    GoogleApiClient mGoogleApiClient;
    final String BATTERY_MONITOR_PATH = "/BATTERY_MONITORING_PATH";


    //@Override
    public void onCreate(Context context) {

        mGoogleApiClient = new GoogleApiClient.Builder(context).addApi(Wearable.API).build();
        mGoogleApiClient.connect();
        super.onCreate();
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
        int Randvalue = new Random().nextInt(5000);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(BATTERY_MONITOR_PATH);
        putDataMapRequest.getDataMap().putString("/ChargeStatus", battStatus.Charging + " : " + Randvalue);
        putDataMapRequest.getDataMap().putString("/ChargeLevel", battStatus.ChargeLevel + " : " + Randvalue);
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        int reconnectAttempts=0;
        while (!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
            reconnectAttempts+=1;
            if(reconnectAttempts>5){
                return;
            }
            Log.d("Attempt reconnect","trying to reconnect");
        }

        Log.d("BatterySend:Charge", battStatus.Charging + " : " + Randvalue);
        Log.d("BatterySend:level", battStatus.ChargeLevel + " : " + Randvalue);


        DataApi.DataItemResult dataItemResult = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request).await();
    }
}
