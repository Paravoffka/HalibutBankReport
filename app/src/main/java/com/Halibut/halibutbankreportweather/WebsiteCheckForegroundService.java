package com.Halibut.halibutbankreportweather;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.Halibut.halibutbankreport.R;

//public class WebsiteCheckForegroundService extends Service {
//    private Handler handler = new Handler(Looper.getMainLooper());
//    //private static final int INTERVAL = 30000; // 30 minutes in milliseconds
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // Create a notification for the foreground service
//        createNotificationChannel();
//
//        Notification notification = new NotificationCompat.Builder(this, "your_channel_id")
//                .setContentTitle("Halibut Bank is running in the background")
//                .setContentText("Checking weather conditions for incoming days so you won't miss it")
//                .setSmallIcon(R.mipmap.launcher)
//                .setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.katushka2mp3)) // This is IMPORTANT
//                .build();
//
//        startForeground(1, notification);
//        return START_STICKY;
//    }
//
//    private Runnable task = new Runnable() {
//        @Override
//        public void run() {
//            // Put your website checking logic and notification sending here
//            sendNotification();
//
//        }
//    };
//
//    private void sendNotification() {
//        // Your notification logic here
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Stop the periodic task when the service is destroyed
//        handler.removeCallbacks(task);
//    }
//
//    private void createNotificationChannel() {
//        NotificationChannel channel = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            channel = new NotificationChannel("your_channel_id", "Your Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
//        }
//        NotificationManager manager = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            manager = getSystemService(NotificationManager.class);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            manager.createNotificationChannel(channel);
//        }
//    }
//
//}
public class WebsiteCheckForegroundService extends Service {
    private static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Halibut Bank is running in the background")
                .setContentText("Checking weather conditions for incoming days so you won't miss it")
                .setSmallIcon(R.mipmap.launcher)
                .build();

        startForeground(1, notification);

        // Do your work here
        LoggerUtils.log("TAG", "Halibut Bank is running in the background");
        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

