package com.example.ellis.mobilebatterytowear;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Ellis on 10/10/2017.
 */

public class BatteryMonitor {

    ProgressBar percentBar;
    TextView percentText;
    TextView chargingText;
    BatteryMonitor_Service batteryService;

    private Handler handler = new Handler();

    void connect(ProgressBar percent_pbView, TextView percent_txtView, TextView chargeStatus_txtView) {
        percentBar = percent_pbView;
        percentText = percent_txtView;
        chargingText = chargeStatus_txtView;
        batteryService = new BatteryMonitor_Service();

    }

    void start() {
        r.run();
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
                percentBar.setProgress(percent);
                percentText.setText(percent + "%");
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
                        Log.d("ChargeStatus:", "Charging");

                        break;
                    case 1:
                        //fully Charged
                        chargingText.setText("Fully Charged");
                        chargingText.setTextColor(Color.GREEN);
                        Log.d("ChargeStatus:", "Fully Charged");
                        break;
                }
            }
        });
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            updateBatteryPercent_Container(batteryService.requestBatteryPercent());
            updateChargingStatus(batteryService.requestBatteryStatus());
            handler.postDelayed(this, 1000);
        }
    };

}
