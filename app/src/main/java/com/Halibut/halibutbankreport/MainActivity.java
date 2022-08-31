package com.Halibut.halibutbankreport;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    public TextView tvStripeChangingColors;
    public TextView tvCrossibilitySituation;
    private AdView mAdView;
    private Button historicalButton;
    private Spinner spinnerDropBuoy;
    String waveHigh;
    public String HalibutBank = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46146";
    public String EnglishBay = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46304";
    public String GeorgiaStrait = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46303";
    public String SentryShoal = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46131";

    @Override // Working with Action Bar:
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapButton:oprnActivity3();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/////------------ ADS:
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//////------------- Main Body
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new
                                StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }

        //****** Trying to Add Spinner
        spinnerDropBuoy = findViewById(R.id.spinnerSelectYourBouy);
        String[] buoy = getResources().getStringArray(R.array.list_of_buys);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, buoy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDropBuoy.setAdapter(adapter);

        spinnerDropBuoy.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override //Here we're listening on spinner
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = spinnerDropBuoy.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "You selected: " + selectedItem, Toast.LENGTH_LONG).show();   //SYstemOutPrint at the buttom of the page.
                String descriptionOfTheApplication = "This application displays real time information about marine situation at "+ selectedItem; // Adjusting header to selected item.

                tvDescription = findViewById(R.id.tvDescripationID);
                tvDescription.setText(descriptionOfTheApplication);

                switch (i){
                    case 0:
                        waveHigh = getCurrentDataFromHalibutBank(HalibutBank);
                        crossibilityStripOfGeorgeaStrait(waveHigh);
                        break;

                        case 1:
                            waveHigh = getCurrentDataFromHalibutBank(EnglishBay);
                            crossibilityStripOfGeorgeaStrait(waveHigh);
                            break;

                            case 2:
                                waveHigh = getCurrentDataFromHalibutBank(GeorgiaStrait);
                                crossibilityStripOfGeorgeaStrait(waveHigh);
                                break;

                                case 3:
                                    waveHigh = getCurrentDataFromHalibutBank(SentryShoal);
                                    crossibilityStripOfGeorgeaStrait(waveHigh);
                                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));

        //********************************** Historical part is not working experiment yet

        historicalButton = findViewById(R.id.button);
        historicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openActivity2();

            }
        });
    }

    @Override //Adding custom buttons like View Map, Settings and so on
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buttons, menu);
        return true;
    }


    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    getPastData();
}

public void oprnActivity3(){
    Intent intent = new Intent(this, PictureMapButNotMap.class);
    startActivity(intent);
}



// Methods working with data:
    public void crossibilityStripOfGeorgeaStrait(String getwaveHigh) {

        String headerOfCrossingSituation = "This is current recommendation for you: ";
        tvCrossibilitySituation = findViewById(R.id.TVID4);
        TextView crossibilitySituationHeader = tvCrossibilitySituation;
        crossibilitySituationHeader.setText(headerOfCrossingSituation);

        tvStripeChangingColors = findViewById(R.id.TVId3);
        TextView crossingStripe = tvStripeChangingColors;
        if (getwaveHigh.equals("nope")) {
            crossingStripe.setText("Figure out where internet is gone. ");
        } else {
            try {
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
            }catch (Exception e) {
                crossingStripe.setBackgroundColor(Color.parseColor("#CCCCCC"));
                crossingStripe.setText("Wave height is N/A, check other parameters.");
            }
        }

    }
    public String getCurrentDataFromHalibutBank(String buoiLink) {

        try {
            Document wave = Jsoup.connect(buoiLink).get();

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
    public String getPastData() {
        String descriptionOfTheApplication = "This application displays real time information about marine situation at Halibut Buoy Bank.";

        tvDescription = findViewById(R.id.tvDescripationID);
        tvDescription.setText(descriptionOfTheApplication);

        try {
            Document wave = Jsoup.connect("https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46146").get();

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
            tvDataDisplayed = findViewById(R.id.tvDataDisplayedID);
            tvDataDisplayed.setText(result);

            return two.text();
        } catch (Exception e) {
            String badResult = "No Internet :(";
            tvDataDisplayed = findViewById(R.id.tvDataDisplayedID);
            tvDataDisplayed.setText(badResult);
            tvStripeChangingColors = findViewById(R.id.TVId3);
            tvStripeChangingColors.setBackgroundColor(Color.parseColor("#c3c4c7"));
            return "nope";
        }

    }

}
