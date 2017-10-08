package com.example.ellis.mobilebatterytowear;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Main_Battery_Level extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);
        mTextView = (TextView) findViewById(R.id.text);
    }
}
