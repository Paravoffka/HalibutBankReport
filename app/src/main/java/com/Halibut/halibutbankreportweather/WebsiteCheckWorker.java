package com.Halibut.halibutbankreportweather;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.annotation.NonNull;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gson.Gson;

public class WebsiteCheckWorker extends Worker {
    String message;
//    Map<String, String>  forecastThreedaysData=  new HashMap<>();
  //  Map<String, String> todayTonightTomorrow;
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final int INITIAL_DELAY = 2000; // 2 seconds
   // private static final int INTERVAL = 60000; // 30 minutes in milliseconds

    public WebsiteCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
              Runnable task = new Runnable() {
                @Override
                public void run() {
                    // Replace the URL with your desired website URL
                    GetDataFromHalibutBank getFforecast = new GetDataFromHalibutBank();
                    String url = "https://weather.gc.ca/marine/forecast_e.html?mapID=02&siteID=14305";

                    // Get the forecast data for the next three days
                    LinkedHashMap<String, String> forecastThreedaysData = getFforecast.incomingDaysForecast(url);
                    // Get the forecast data for today, tonight, and tomorrow
                    LinkedHashMap<String, String> todayTonightTomorrow = getFforecast.todayTonightTomorrow(url);

                    // Combine both maps
                    LinkedHashMap<String, String> combinedForecastData = new LinkedHashMap<>();
                    combinedForecastData.putAll(todayTonightTomorrow);
                    combinedForecastData.putAll(forecastThreedaysData);
//***************************TEMP FIX with JSON
                    // Create an Intent for NotificationService
                    Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(combinedForecastData);
                //    intent.putExtra("forecastData", new LinkedHashMap<>(combinedForecastData));  This was initial solution
                    intent.putExtra("forecastData", jsonString);
//**********************************************************************************************************

                    // Start the service to send the notification
                    getApplicationContext().startService(intent);

                    // Start the foreground service
                    Intent serviceIntent = new Intent(getApplicationContext(), WebsiteCheckForegroundService.class);
                    ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);

                }
            };

            handler.postDelayed(task, INITIAL_DELAY);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
