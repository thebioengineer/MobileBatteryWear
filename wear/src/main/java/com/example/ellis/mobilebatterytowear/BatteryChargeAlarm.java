package com.example.ellis.mobilebatterytowear;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Random;

/**
 * Created by ellis on 12/18/17.
 */

public class BatteryChargeAlarm {

    public static final String PREFS_NAME = "Mobile_Battery_Alarm";
    public static final String BATTERY_ALARM_PATH = "/Mobile_Battery_Alarm";


    Context applicationContext;
    boolean AlarmStatus;
    GoogleApiClient mGoogleApiClient;

    void addContext(Context context, GoogleApiClient inGoogleApiClient){
        applicationContext=context;
        mGoogleApiClient = inGoogleApiClient;
    }

    void onStart(){
        SharedPreferences settings = applicationContext.getSharedPreferences(PREFS_NAME, 0);
        AlarmStatus = settings.getBoolean("AlarmStatus", false);
    }

    void onPause(){
        saveAlarmStatus();
    }

    void onStop(){
        saveAlarmStatus();
    }

    boolean longbuttonClick(){
        AlarmStatus = !AlarmStatus;
        String toastText="Battery Alarm unset";
        if(AlarmStatus){
            toastText="Battery Alarm set";
        }

        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(BATTERY_ALARM_PATH);
        putDataMapRequest.getDataMap().putBoolean("/setAlarm", AlarmStatus);
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        int reconnectAttempts=0;
        while (!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
            reconnectAttempts+=1;
            if(reconnectAttempts>5){
                return true;
            }
        }

        PendingResult<DataApi.DataItemResult> pendingResult  = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request);

        Toast.makeText(applicationContext, toastText,
                Toast.LENGTH_SHORT).show();

        saveAlarmStatus();
        return true;
    }

    void updateAlarmStatustoFalse(){
        SharedPreferences settings = applicationContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("AlarmStatus", false);
        editor.commit();
        AlarmStatus = false;
    }

    void updateAlarmStatustoTrue(){
        SharedPreferences settings = applicationContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("AlarmStatus", true);
        editor.commit();
        AlarmStatus = true;
    }
    private void saveAlarmStatus(){
        SharedPreferences settings = applicationContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("AlarmStatus", AlarmStatus);
        editor.commit();
    }
};
