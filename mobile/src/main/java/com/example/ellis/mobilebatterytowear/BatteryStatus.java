package com.example.ellis.mobilebatterytowear;

import java.io.Serializable;

/**
 * Created by Ellis on 10/14/2017.
 */

public class BatteryStatus implements Serializable {
    public float ChargeLevel;
    public Boolean Charging;

    public void updateStatus(float CL, boolean C) {
        ChargeLevel = CL;
        Charging = C;
    }
}
