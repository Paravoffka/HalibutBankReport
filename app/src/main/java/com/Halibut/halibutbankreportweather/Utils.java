package com.Halibut.halibutbankreportweather;

import android.app.ActivityManager;
import android.content.Context;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Map<String, String> splitFirstEntry1(Map<String, String> forecastData) {
        // Extract the first entry
        String firstValue = "";
        String secondValue = "";
        Map.Entry<String, String> firstEntry = forecastData.entrySet().iterator().next();
        String originalKey = firstEntry.getKey();
        String originalValue = firstEntry.getValue();

        // Split the key
        String[] partsKey = originalKey.split(" and ");
        String firstKey = partsKey[0].trim();
        String secondKey = partsKey[1].trim();

        // Split the value based on the day of the week
        String[] sentences = originalValue.split("\\.\\s+");

        // Check if there are two sentences
        if (sentences.length >= 2 && originalKey.contains("and")) {
            firstValue = sentences[0].trim() + ".";
            secondValue = sentences[1].trim() + ".";


            // Create a new LinkedHashMap to maintain order
            Map<String, String> updatedForecastData = new LinkedHashMap<>();

            // Insert the new entries as the first and second elements
            updatedForecastData.put(firstKey, firstValue);
            updatedForecastData.put(secondKey, secondValue);

            // Add the rest of the entries from the original map
            forecastData.remove(originalKey);
            updatedForecastData.putAll(forecastData);

            return updatedForecastData;
        }
        else return forecastData;
    }

    public static Map<String, String> splitFirstEntry(Map<String, String> forecastData) {
        if (forecastData.isEmpty()) return forecastData; // Return early if the map is empty

        Map.Entry<String, String> firstEntry = forecastData.entrySet().iterator().next();
        String originalKey = firstEntry.getKey();
        String originalValue = firstEntry.getValue();

        // Split the key into two parts
        String[] partsKey = originalKey.split(" and ");
        if (partsKey.length != 2) return forecastData; // Return early if the key doesn't match expected format

        String firstKey = partsKey[0].trim();
        String secondKey = partsKey[1].trim();

        // Split the value into sentences
        String[] sentences = originalValue.split("\\.\\s+");

        // Create a new LinkedHashMap to maintain order
        Map<String, String> updatedForecastData = new LinkedHashMap<>();

        if (sentences.length >= 2 && sentences.length <= 3) {
            String firstValue = sentences[0].trim() + ".";
            String secondValue = sentences[1].trim() + ".";
            String thirdValue = (sentences.length == 3) ? sentences[2].trim() + "." : "";

            updatedForecastData.put(firstKey, firstValue);
            updatedForecastData.put(secondKey, (thirdValue.isEmpty() ? secondValue : secondValue + " " + thirdValue));

            // Remove the original entry and add the rest of the entries from the original map
            forecastData.remove(originalKey);
            updatedForecastData.putAll(forecastData);
        } else {
            // If sentences don't match expected length, return original map
            return forecastData;
        }

        return updatedForecastData;
    }

}

