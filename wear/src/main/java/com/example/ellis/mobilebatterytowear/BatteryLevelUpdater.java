package com.example.ellis.mobilebatterytowear;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Ellis on 10/10/2017.
 */

public class BatteryLevelUpdater {

    ProgressBar percentBar;
    TextView percentText;
    TextView chargingText;
    BatteryMonitor_Service batteryService;
    ProgressBar dataLoadBar;

    private Handler handler = new Handler();

    void connect(ProgressBar percent_pbView, TextView percent_txtView, TextView chargeStatus_txtView, GoogleApiClient mGoogleApiClient, ProgressBar data_loading_pbView) {
        percentBar = percent_pbView;
        percentText = percent_txtView;
        chargingText = chargeStatus_txtView;
        dataLoadBar = data_loading_pbView;
        batteryService = new BatteryMonitor_Service();
        batteryService.onCreate(mGoogleApiClient);
    }

    void start() {
        r.run();
        handler.post(new Runnable() {
            @Override
            public void run() {
                dataLoadBar.setVisibility(View.VISIBLE);
                percentText.setVisibility(View.GONE);
            }
        });
    }

    void pause() {
        handler.removeCallbacks(r);
    }

    void stop() {
        batteryService.onDestroy();
    }

    private void updateBatteryPercent_Container(final int percent) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(percentText.getVisibility()==View.GONE && percent > 0){
                    percentText.setVisibility(View.VISIBLE);
                }
                if(dataLoadBar.getVisibility()==View.VISIBLE && percent > 0){
                    dataLoadBar.setVisibility(View.GONE);
                }

                percentBar.setProgress(percent);
                percentBar.setSecondaryProgress(percent);

                percentText.setText(percent + "%");
                Log.d("BatteryLevel",percent+"%");
            }
        });
    }

    private void updateChargingStatus(final int Status) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                switch (Status) {
                    case -1:
                        //discharging
                        chargingText.setText("");
                        break;
                    case 0:
                        //charging
                        chargingText.setText("CHARGING");
                        chargingText.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        //fully Charged
                        chargingText.setText("FULLY CHARGED");
                        chargingText.setTextColor(Color.GREEN);
                        break;
                }
            }
        });
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            batteryService.requestBatteryPercent_and_Status();
            updateBatteryPercent_Container(batteryService.BatteryPercent());
            updateChargingStatus(batteryService.BatteryStatus());
            handler.postDelayed(this, 1000);
        }
    };

}
