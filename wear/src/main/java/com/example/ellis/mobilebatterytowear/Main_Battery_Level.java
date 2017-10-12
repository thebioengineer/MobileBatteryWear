package com.example.ellis.mobilebatterytowear;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main_Battery_Level extends Activity {

    private TextView battery_chargeStatus_Text;
    private TextView battery_percent_Text;
    private ProgressBar battery_percent_PB;
    private BatteryMonitor batteryMonitor = new BatteryMonitor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        // Get widgets reference from XML layout
        battery_chargeStatus_Text = (TextView) findViewById(R.id.batteryCharge_Status);
        battery_percent_Text = (TextView) findViewById(R.id.batteryPercentage_text);
        battery_percent_PB = (ProgressBar) findViewById(R.id.batteryPercentage_pb);
        battery_percent_PB.setMax(100);
        battery_percent_PB.setSecondaryProgress(100);

        batteryMonitor.connect(battery_percent_PB, battery_percent_Text, battery_chargeStatus_Text);

        //TODO: Send message to phone to start background process to send phone battery percentage
        //TODO: create listener to update the wearable UI when: battery percentage changes, changes charging status: charging, discharging, fully charged
    }

    @Override
    protected void onStart() {
        super.onStart();
        batteryMonitor.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        batteryMonitor.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        batteryMonitor.stop();
    }


}
