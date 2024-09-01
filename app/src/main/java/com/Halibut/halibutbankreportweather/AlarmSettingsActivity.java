package com.Halibut.halibutbankreportweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.Halibut.halibutbankreport.R;

public class AlarmSettingsActivity extends AppCompatActivity {

    private SharedPreferences settings;

    private CheckBox checkboxAlarm;
    private CheckBox checkboxSound;
    private RadioGroup radioGroupWind;
       private static final String PREF_ALARM = "alarmEnabled";
    private static final String PREF_SOUND = "soundEnabled";
    private static final String PREF_WIND = "windCondition";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getSupportActionBar().setTitle("Alarm Settings");
        setContentView(R.layout.activity_alarm_settings);

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        checkboxAlarm = findViewById(R.id.checkbox_alarm);
        checkboxSound = findViewById(R.id.checkbox_sound);
        radioGroupWind = findViewById(R.id.radio_group_wind);

        // Load settings from SharedPreferences
        boolean alarmEnabled = settings.getBoolean(PREF_ALARM, true);
        boolean soundEnabled = settings.getBoolean(PREF_SOUND, true);
        int windCondition = settings.getInt(PREF_WIND, R.id.radio_wind_calm);

        checkboxAlarm.setChecked(alarmEnabled);
        checkboxSound.setChecked(soundEnabled);
        radioGroupWind.check(windCondition);

        checkboxAlarm.setOnCheckedChangeListener((buttonView, isChecked) ->
                settings.edit().putBoolean(PREF_ALARM, isChecked).apply()
        );

        checkboxSound.setOnCheckedChangeListener((buttonView, isChecked) ->
                settings.edit().putBoolean(PREF_SOUND, isChecked).apply()
        );

        radioGroupWind.setOnCheckedChangeListener((group, checkedId) ->
                settings.edit().putInt(PREF_WIND, checkedId).apply()
        );

        Button applyChangesButton = findViewById(R.id.button_apply_changes);
        applyChangesButton.setOnClickListener(v -> {
            NotificationHelper.scheduleNotification(getApplicationContext());
            Toast.makeText(AlarmSettingsActivity.this, "Changes Applied", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
