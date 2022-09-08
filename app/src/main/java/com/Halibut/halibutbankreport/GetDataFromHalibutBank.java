package com.Halibut.halibutbankreport;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetDataFromHalibutBank {

    private String HalibutBank = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46146";
    private String EnglishBay = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46304";
    private String GeorgiaStrait = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46303";
    private String SentryShoal = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46131";

    public static String getCurrentDataFromHalibutBank(String buoyLink) {
        try {
            Document wave = Jsoup.connect(buoyLink).get();

            Element one = wave.select("td").get(0); // wind
            Element two = wave.select("td").get(2); // Wave height (m)
            Element three = wave.select("td").get(3); // temp
            Element Fore = wave.select("td").get(4); // wave period
/*
            System.out.println("Wind: " + one.text());
            System.out.println("Wave height (m) : " + two.text());
            System.out.println("Temperature : " + three.text() + " degree Celsius");
            System.out.println("Wave Period : " + Fore.text() + " seconds");
  */
            String result = "Wind : " + one.text() + "\nWave height (m) : " + two.text()
                    + "\nTemperature : " + three.text() + " degree Celsius" + "\nWave Period : " + Fore.text() + " seconds";
            return result;
        } catch (Exception e) {
            String badResult = "The data for this buoy is not available at the moment. Please try another buoys.";
           return "nope";
        }
    }

    public static String extractCurrentWaveHighFromHalibutBank(String buoyLink){
        try {
            Document wave = Jsoup.connect(buoyLink).get();

            Element one = wave.select("td").get(0); // wind
            Element two = wave.select("td").get(2); // Wave height (m)
            Element three = wave.select("td").get(3); // temp
            Element Fore = wave.select("td").get(4); // wave period

             return two.text();
        }catch (Exception e) {

                return "nope";
            }
    }
}