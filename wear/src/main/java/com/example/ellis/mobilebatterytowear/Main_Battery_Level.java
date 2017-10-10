package com.example.ellis.mobilebatterytowear;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class Main_Battery_Level extends Activity {

    private int batteryStatus = 0;
    private Handler handler = new Handler();
    private TextView battery_Percent;
    private ProgressBar battery_Progess;
    private ImageButton battery_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        // Get widgets reference from XML layout
        battery_Percent = (TextView) findViewById(R.id.batteryPercentage_text);
        battery_Image = (ImageButton) findViewById(R.id.mainBatteryImage);
        battery_Progess = (ProgressBar) findViewById(R.id.batteryPercentage_pb);
        battery_Progess.setMax(100);
        battery_Progess.setSecondaryProgress(100);


        updateView_BatteryPercent(100);

        //TODO: Send message to phone to start background process to send phone battery percentage
        //TODO: create listener to update the wearable UI when: battery percentage changes, changes charging status: charging, discharging, fully charged

        //temporary test, on click of background, set to update battery value

        Button batteryButton = (Button) findViewById(R.id.startButton);
        batteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the progress status zero on each button click
                batteryStatus = 0;

                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (batteryStatus < 100) {
                            // Update the progress status
                            batteryStatus += 1;

                            // Try to sleep the thread for 20 milliseconds
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            updateView_BatteryPercent(batteryStatus);

                        }
                    }
                }).start(); // Start the operation
            }
        });
    }


    void updateView_BatteryPercent(final int percent) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("BATTERY LEVEL:", percent + "%");
                battery_Progess.setProgress(percent);
                battery_Percent.setText(percent + "%");
            }
        });
    }
}
