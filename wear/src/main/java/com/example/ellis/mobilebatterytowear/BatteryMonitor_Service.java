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

    GoogleApiClient mGoogleApiClient;

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

    public int requestBatteryPercent() {
        return new Random().nextInt(101);
    }

    public int requestBatteryStatus() {
        return new Random().nextInt(2) - 1;
    }
}
