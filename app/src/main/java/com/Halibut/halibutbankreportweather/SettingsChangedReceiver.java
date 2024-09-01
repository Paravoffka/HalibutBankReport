package com.Halibut.halibutbankreportweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SettingsChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the updated value of isSoundEnabled from SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isSoundEnabled = preferences.getBoolean("soundEnabled", true);

        // Do whatever you need to do with the updated value of isSoundEnabled
    }
}
