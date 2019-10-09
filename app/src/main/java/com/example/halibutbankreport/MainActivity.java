package com.example.halibutbankreport;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    TextView tvDescription;
    TextView tvDataDisplayed;
    TextView tvStripeChangingColors;
    TextView tvCrossibilitySituation;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String waveHigh = getCurrentDataFromHalibutBank();
        crossibilityStripOfGeorgeaStrait(waveHigh);
    }
        public void crossibilityStripOfGeorgeaStrait(String getwaveHigh) {

            String headerOfCrossingSituation = "This is current recomendation for you: ";
            tvCrossibilitySituation = findViewById(R.id.TVID4);
            TextView crossibilitySituationHeader = tvCrossibilitySituation;
            crossibilitySituationHeader.setText(headerOfCrossingSituation);

            tvStripeChangingColors = findViewById(R.id.TVId3);
            TextView crossingStripe = tvStripeChangingColors;
            if (getwaveHigh.equals("nope")) {
                crossingStripe.setText("Figure out where internet is gone. ");
            } else {

                double intWaveHigh = Double.parseDouble(getwaveHigh);
                if (intWaveHigh == 0.1) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#4CAF50"));
                    crossingStripe.setText("Perfect conditions for crossing. Flat calm.");
                } else if (intWaveHigh == 0.2) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#FFEB3B"));
                    crossingStripe.setText("Comfortable crossing.");
                } else if (intWaveHigh == 0.3) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#FBC02D"));
                    crossingStripe.setText("Possible but NOT Comfortable crossing.");
                } else if (intWaveHigh == 0.4) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#FF5722"));
                    crossingStripe.setText("It's possible but you won't be happy crossing it.");
                } else if (intWaveHigh == 0.5) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#D32F2F"));
                    crossingStripe.setText("Crossing possible but you better stay at home");
                } else if (intWaveHigh > 0.5 && intWaveHigh < 1.0) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#7B1FA2"));
                    crossingStripe.setText("Big water. Extremely dangerous for small vessel!!!");
                } else {
                    crossingStripe.setBackgroundColor(Color.parseColor("#512DA8"));
                    crossingStripe.setText("Don't even think about it.");
                }
            }

        }

    public String getCurrentDataFromHalibutBank() {
        String descriptionOfTheApplication = "This application displays real time information about marine situation at Halibut Bay Bank.";

        tvDescription = findViewById(R.id.tvDescripationID);
        tvDescription.setText(descriptionOfTheApplication);

        try {
            Document wave = Jsoup.connect("https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46146").get();

            Element one = wave.select("td").get(0);
            Element two = wave.select("td").get(2);// Wave height (m)
            Element three = wave.select("td").get(3);
            Element Fore = wave.select("td").get(4);
/*
            System.out.println("Wind: " + one.text());
            System.out.println("Wave height (m) : " + two.text());
            System.out.println("Temperature : " + three.text() + " degree Celsius");
            System.out.println("Wave Period : " + Fore.text() + " seconds");
  */
            String result = "Wind: " + one.text() + "\nWave height (m) : " + two.text()
                    + "\nTemperature : " + three.text() + " degree Celsius" + "\nWave Period : " + Fore.text() + " seconds";
            tvDataDisplayed = findViewById(R.id.tvDataDisplayedID);
            tvDataDisplayed.setText(result);

            return two.text();
        } catch (Exception e) {
            String badResult = "No Internet :(";
            tvDataDisplayed = findViewById(R.id.tvDataDisplayedID);
            tvDataDisplayed.setText(badResult);
            return "nope";
        }

    }

}
