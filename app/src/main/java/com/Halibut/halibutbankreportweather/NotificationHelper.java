package com.Halibut.halibutbankreportweather;
import android.content.Context;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class NotificationHelper {
    private static boolean notificationSent = false;
    private static final String WORK_NAME = "WebsiteCheckWork";
    public static void scheduleNotification(Context context) {
        //LoggerUtils.log("Notification Helper", "Running...");

        if (!notificationSent) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            PeriodicWorkRequest websiteCheckWorkRequest = new PeriodicWorkRequest.Builder(
                    WebsiteCheckWorker.class,
                    12, TimeUnit.HOURS
            )
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    WORK_NAME,
                    ExistingPeriodicWorkPolicy.REPLACE, // This ensures that any existing work is cancelled and a new one is enqueued
                    websiteCheckWorkRequest);
        }
    }
}