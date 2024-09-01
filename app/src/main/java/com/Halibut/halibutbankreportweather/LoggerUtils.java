package com.Halibut.halibutbankreportweather;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggerUtils {
    private static final String LOG_TAG = "HalibutBankReport";
    private static final String LOG_FILE_NAME = "app_log.txt";
    private static File logFile;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static void initialize(Context context) {
        File logDir = context.getExternalFilesDir(null);
        if (logDir != null && logDir.exists() && logDir.canWrite()) {
            logFile = new File(logDir, LOG_FILE_NAME);
        } else {
            Log.e(LOG_TAG, "Log directory is not writable or does not exist");
        }
    }

    public static void log(final String tag, final String message) {
        String timestampedMessage = getTimestamp() + " " + tag + ": " + message;
        Log.d(tag, message);  // Log to Logcat
        if (logFile != null) {
            executorService.execute(() -> {
                try (FileWriter fw = new FileWriter(logFile, true);
                     PrintWriter pw = new PrintWriter(fw)) {
                    pw.println(timestampedMessage);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error writing to log file", e);
                }
            });
        }
    }

    public static void logError(final String tag, final String message, final Throwable throwable) {
        String timestampedMessage = getTimestamp() + " " + tag + ": " + message;
        Log.e(tag, message, throwable);  // Log to Logcat
        if (logFile != null) {
            executorService.execute(() -> {
                try (FileWriter fw = new FileWriter(logFile, true);
                     PrintWriter pw = new PrintWriter(fw)) {
                    pw.println(timestampedMessage);
                    throwable.printStackTrace(pw);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error writing to log file", e);
                }
            });
        }
    }

    private static String getTimestamp() {
        return dateFormat.format(new Date());
    }
}
