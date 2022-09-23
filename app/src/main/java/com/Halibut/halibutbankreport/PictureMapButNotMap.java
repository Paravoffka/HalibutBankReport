package com.Halibut.halibutbankreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class PictureMapButNotMap extends AppCompatActivity {
    // Links used. In future put in to separate resource file:
    private String HalibutBank = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46146";
    private String EnglishBay = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46304";
    private String GeorgiaStrait = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46303";
    private String SentryShoal = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46131";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_map_but_not_map);

        getSupportActionBar().setTitle("Buoys location in Georgia Straight"); //Creating title of the activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Adding Back button. Don't forget change manifest.

        // Adding the picture, simulating map.
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.halibutpicenglwithmarkersr);

        // Filling Wind Column:
        GetDataFromHalibutBank getWind = new GetDataFromHalibutBank();
        String sentryShoalWind  = getWind.extractCurrentWindFromBuoy(SentryShoal);
        String halibutBankWind = getWind.extractCurrentWindFromBuoy(HalibutBank);
        String englishBayWind = getWind.extractCurrentWindFromBuoy(EnglishBay);
        String georgiaStraitWind = getWind.extractCurrentWindFromBuoy(GeorgiaStrait);

        TextView tableSentryShoalWind = findViewById(R.id.sentryShoalWind);
        tableSentryShoalWind.setText(sentryShoalWind);
        TextView tableHalibutBankWind = findViewById(R.id.halibutBankWind);
        tableHalibutBankWind.setText(halibutBankWind);
        TextView tableEnglishBayWind = findViewById(R.id.englishBayWind);
        tableEnglishBayWind.setText(englishBayWind);
        TextView tableGeorgiaStraitWind = findViewById(R.id.georgiaStraitWind);
        tableGeorgiaStraitWind.setText(georgiaStraitWind);

                //Filling WaveHigh Column:
            GetDataFromHalibutBank getWaveHigh = new GetDataFromHalibutBank();
            String sentryShoalWaveHigh = getWaveHigh.extractCurrentWaveHighFromBuoy(SentryShoal);
            String halibutBankWaveHigh = getWaveHigh.extractCurrentWaveHighFromBuoy(HalibutBank);
            String englishBayWaveHigh = getWaveHigh.extractCurrentWaveHighFromBuoy(EnglishBay);
            String georgiaStraitWaveHigh = getWaveHigh.extractCurrentWaveHighFromBuoy(GeorgiaStrait);

            TextView tableSentryShoalWaveHigh = findViewById(R.id.sentryShoalWaveHigh);
            tableSentryShoalWaveHigh.setText(sentryShoalWaveHigh);
            TextView tableHalibutBankWaveHigh = findViewById(R.id.halibutBankWaveHeight);
            tableHalibutBankWaveHigh.setText(halibutBankWaveHigh);
            TextView tableEnglishBayWaveHigh = findViewById(R.id.englishBayWaveHigh);
            tableEnglishBayWaveHigh.setText(englishBayWaveHigh);
            TextView tableGeorgiaStraitWaveHigh = findViewById(R.id.georgiaStraitWaveHeight);
            tableGeorgiaStraitWaveHigh.setText(georgiaStraitWaveHigh);

                    //Filling Temperature Column:
                GetDataFromHalibutBank getTemperature = new GetDataFromHalibutBank();
                String sentryShoalTemperature = getTemperature.extractCurrentTemperatureFromBuoy(SentryShoal);
                String halibutBankTemperature = getTemperature.extractCurrentTemperatureFromBuoy(HalibutBank);
                String englishBayTemperature = getTemperature.extractCurrentTemperatureFromBuoy(EnglishBay);
                String georgiaStraitTemperature = getTemperature.extractCurrentTemperatureFromBuoy(GeorgiaStrait);

                TextView tableSentryShoalTemperature = findViewById(R.id.sentryShoalTemp);
                tableSentryShoalTemperature.setText(sentryShoalTemperature);
                TextView tableHalibutBankTemperature = findViewById(R.id.halibutBankTemperature);
                tableHalibutBankTemperature.setText(halibutBankTemperature);
                TextView tableEnglishBayTemperature = findViewById(R.id.englishBayTemperature);
                tableEnglishBayTemperature.setText(englishBayTemperature);
                TextView tableGeorgiaStraitTemperature = findViewById(R.id.georgiaStraitTemperature);
                tableGeorgiaStraitTemperature.setText(georgiaStraitTemperature);





    }
}
