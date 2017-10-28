package com.example.ellis.mobilebatterytowear;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class Main_Battery_Level extends Activity {

    private TextView battery_chargeStatus_Text;
    private TextView battery_percent_Text;
    private ProgressBar battery_percent_PB;
    private BatteryLevelUpdater batteryMonitor = new BatteryLevelUpdater();
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        // Get widgets reference from XML layout
        battery_chargeStatus_Text = (TextView) findViewById(R.id.batteryCharge_Status);
        battery_percent_Text = (TextView) findViewById(R.id.batteryPercentage_text);
        battery_percent_PB = (ProgressBar) findViewById(R.id.batteryPercentage_pb);
        battery_percent_PB.setMax(100);
        battery_percent_PB.setSecondaryProgress(0);

        if(null == mGoogleApiClient){
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        }
        if(!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();

        batteryMonitor.connect(battery_percent_PB, battery_percent_Text, battery_chargeStatus_Text, mGoogleApiClient);
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
