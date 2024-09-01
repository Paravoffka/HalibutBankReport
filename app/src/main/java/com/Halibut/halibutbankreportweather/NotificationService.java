package com.Halibut.halibutbankreportweather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.google.gson.Gson;

import androidx.core.app.NotificationCompat;

import com.Halibut.halibutbankreport.R;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 100;
    boolean isSoundEnabled;
    boolean isAlarmEnabled;
    int selectedWindConditionId;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        String collectionOfTheNotifications = "";
        super.onStartCommand(intent, flags, startId);

        if (intent == null || !intent.hasExtra("forecastData")) {
            LoggerUtils.log(TAG, "Intent or extras are null");
            return START_STICKY;
        }

        // Retrieve the state of the sound checkbox from SharedPreferences or other storage mechanism
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isAlarmEnabled = preferences.getBoolean("alarmEnabled", true);
        isSoundEnabled = preferences.getBoolean("soundEnabled", true);
        selectedWindConditionId = preferences.getInt("windCondition", R.id.radio_wind_calm);



        // Retrieve the forecast data from the Intent  USING GSON IN INTENT BECAUSE THERE IS ONLY ONE WAY TO KEEP ORDER IN THE LINKED HASH MAP
        String jsonString = intent.getStringExtra("forecastData");
        Gson gson = new Gson();
        @SuppressWarnings("unchecked")
        Map<String, String> forecastData = gson.fromJson(jsonString, Map.class);


        try {
            forecastData = Utils.splitFirstEntry(forecastData);
        } catch (Exception e) {
            LoggerUtils.log(TAG, "SplitFirstEntry method in Utilities has failed.");
        }

        //*****************************
        if (forecastData == null || forecastData.isEmpty()) {
            LoggerUtils.log(TAG, "forecastData is null or empty");
            return START_STICKY;
        }

        if (forecastData != null && !forecastData.isEmpty()) {
            for (Map.Entry<String, String> entry : forecastData.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (isAlarmEnabled && value != null) {  // Check if the alarm is enabled
                    switch (selectedWindConditionId) {
                        case R.id.radio_wind_calm:
                            if ((value.contains("Wind light") || value.contains("Wind calm")) &&
                                    (!value.contains("10") || !value.contains("15") || !value.contains("20") || !value.contains("25") || !value.contains("30") || !value.contains("35"))) {
                                String notificationMessage = key + ": " + value;
                                collectionOfTheNotifications = collectionOfTheNotifications + "\n" + notificationMessage;
                            }
                            break;
                        case R.id.radio_wind_10:
                            if (!value.contains("30") && !value.contains("25") && !value.contains("10 to 15") && !value.contains("20") &&
                                    (value.contains("5 to 15") || value.contains("Wind light") || value.contains("Wind calm"))) {
                                String notificationMessage = key + ": " + value;
                                collectionOfTheNotifications = collectionOfTheNotifications + "\n" + notificationMessage;
                            }
                            break;
                        case R.id.radio_wind_15:
                            if (!value.contains("to 20") &&
                                    (value.contains("10 to 15") || value.contains("Wind light") || value.contains("Wind calm") || value.contains("5 to 15") || value.contains("10 to 15"))) {
                                String notificationMessage = key + ": " + value;
                                collectionOfTheNotifications = collectionOfTheNotifications + "\n" + notificationMessage;
                            }
                            break;
                        case R.id.radio_wind_20:
                            if (value.contains("to 20")|| value.contains("25") || value.contains("Wind light") || value.contains("Wind calm") || value.contains("to 15")) {
                                String notificationMessage = key + ": " + value;
                                collectionOfTheNotifications = collectionOfTheNotifications + "\n" + notificationMessage;
                            }
                            break;
                    }
                }
            }

            if (!collectionOfTheNotifications.isEmpty()) {
                showNotification(getApplicationContext(), collectionOfTheNotifications); // Triggering the notification
            }
            LoggerUtils.log("NOTIFICATION SERVICE IS RUNNING", "End of onStartCommand in Notification service");
        }

        startTimer();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
// Register BroadcastReceiver to listen for changes in SharedPreferences
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(settingsChangedReceiver, new IntentFilter("SETTINGS_CHANGED_ACTION"), null, null, Context.RECEIVER_NOT_EXPORTED);
        }
        LoggerUtils.log(TAG, "onCreate called in Notification Service");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        LoggerUtils.log(TAG,"onDestroy Called in Notification Service");
        // Unregister BroadcastReceiver
        unregisterReceiver(settingsChangedReceiver);
        stoptimertask();
        super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
        LoggerUtils.log(TAG, "Timer started in Notification Service");
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
            LoggerUtils.log(TAG, "Timer stopped in Notification Service");

        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        //TODO CALL NOTIFICATION FUNC
                        //      showNotification(NotificationService.this,"");
                    }
                });
            }
        };
    }

    public void showNotification(Context context, String message) {
        // Create a notification channel (required for Android Oreo and above)
        LoggerUtils.log(TAG, "showNotification is called in Notification Service");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "MyChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Build and show the notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Outlook for incoming days")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true);
        // .setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.katushka2));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        if (isSoundEnabled) {   // To play a sound
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.katushka2mp3);
            mediaPlayer.start();
        }
    }

    private BroadcastReceiver settingsChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieve the updated value of isSoundEnabled from SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            isSoundEnabled = preferences.getBoolean("soundEnabled", true);
        }
    };




}
