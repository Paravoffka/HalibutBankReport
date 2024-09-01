package com.Halibut.halibutbankreportweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;


public class GetDataFromHalibutBank {

    public static String getCurrentDataFromHalibutBank(String buoyLink) {
        try {
            Document wave = Jsoup.connect(buoyLink).get();

            Element one = wave.select("td").get(0); // wind
            Element two = wave.select("td").get(2); // Wave height (m)
            Element three = wave.select("td").get(3); // temp
            Element Fore = wave.select("td").get(4); // wave period

            String result = "Wind : " + one.text() + "\nWave height (m) : " + two.text()
                    + "\nTemperature : " + three.text() + " degree Celsius" + "\nWave Period : " + Fore.text() + " seconds";
            return result;
        } catch (Exception e) {
            System.out.printf(e.toString());
            String badResult = "The data for this buoy is not available at the moment. Please try another buoys.";
            return badResult;
        }
    }

    public static String[][] getPastDataFromTheBuoy(String pastbuoyLink) {
        try {
            Document doc = Jsoup.connect(pastbuoyLink).get();

            // Select the 'tbody' element
            Element tbody = doc.select("tbody").first();

            // Initialize an array to store the extracted values
            String[][] tableValues = new String[tbody.select("tr").size()][7];

            // Loop through each 'tr' element in the 'tbody'
            int rowIndex = 0;
            for (Element row : tbody.select("tr")) {
                // Skip the first row (header row)
                if (row.hasClass("active")) {
                    continue;
                }

                // Extract values from each 'td' element in the row
                Elements columns = row.select("td");
                int columnIndex = 0;
                for (Element column : columns) {
                    tableValues[rowIndex][columnIndex] = column.text();
                    columnIndex++;
                }

                rowIndex++;
            }

            // Print the extracted values
            for (int i = 0; i < rowIndex; i++) {
                for (int j = 0; j < 7; j++) {
                    System.out.print(tableValues[i][j] + " ");
                }
                System.out.println();
            }
            return tableValues;
        } catch (Exception e) {
            String[][] badResult = new String[24][7];
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 7; j++) {
                    badResult[i][j] = "N/A";
                }
            }
            return badResult;
        }
    }

    public static String extractCurrentWindFromBuoy(String buoyLink) {
        try {
            Document wave = Jsoup.connect(buoyLink).get();
            Element one = wave.select("td").get(0); // wind
            String wind = one.text();
            return wind;
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String extractCurrentWaveHighFromBuoy(String buoyLink) {
        try {
            Document wave = Jsoup.connect(buoyLink).get();
            Element two = wave.select("td").get(2); // Wave height (m)
            return two.text();
        } catch (Exception e) {

            return "N/A";
        }
    }

    public static String extractCurrentTemperatureFromBuoy(String buoyLink) {
        try {
            Document wave = Jsoup.connect(buoyLink).get();
            Element three = wave.select("td").get(3); // temp
            String temperature = three.text();
            return temperature;
        } catch (Exception e) {
            return "N/A";
        }
    }

public LinkedHashMap<String, String> todayTonightTomorrow(String buoyLink) {
    String winds = "Something wrong in StraitOfGeorgiaForecast method";

    LinkedHashMap<String, String> weatherMap = new LinkedHashMap<>();
    try {
        Document weatherForecast = Jsoup.connect(buoyLink).get();
        Element periodElement = weatherForecast.select("p.periodOfCoverage").first();

        if (periodElement != null) {
            winds = periodElement.text();
            Elements textSummaryElements = weatherForecast.select("p.textSummary");

            if (textSummaryElements != null && !textSummaryElements.isEmpty()) {
                String textSummary = textSummaryElements.get(0).text();
                String weatherAndVisibility = "";

                try {
                    weatherAndVisibility = textSummaryElements.get(1).text();
                } catch (Exception e) {
                    android.util.Log.d("StraitOfGeorgiaForecast", "Error: " + e);
                }

                String combinedSummary = textSummary + " " + weatherAndVisibility;
                weatherMap.put(winds, combinedSummary);
            }
        }
    } catch (Exception e) {
        android.util.Log.d("StraitOfGeorgiaForecast", "Error: " + e);
    }

    return weatherMap;
}
    public static LinkedHashMap<String, String> incomingDaysForecast(String buoyLink) {
        LinkedHashMap<String, String> weatherMap = new LinkedHashMap<>();

        try {
            Document document = Jsoup.connect(buoyLink).get();

            Element table = document.select("#forecast-content").first();
            // Assuming the extended forecast section is within a panel with class "panel-info"
            Elements extendedForecastPanel = table.select(".panel-info .ef-date");

            for (Element dayDiv : extendedForecastPanel) {
                // Extract the day
                String day = dayDiv.text();

                // Extract the text summary
                String textSummary = dayDiv.nextElementSibling().select(".textSummary").text();

                // Put the information into the map
                weatherMap.put(day, textSummary);
            }
            for (Map.Entry<String, String> entry : weatherMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherMap;
    }
}