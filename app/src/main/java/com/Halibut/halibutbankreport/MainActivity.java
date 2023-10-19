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
    public TextView tvCrossabilitySituation;
    private AdView mAdView;
    private Button historicalButton;
    private Spinner spinnerDropBuoy;
    String waveHigh;
    String buoyInfo;
    private String HalibutBank = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46146";
    private String EnglishBay = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46304";
    private String GeorgiaStrait = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46303";
    private String SentryShoal = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46131";

    private String pastDataLink = "";

    private String pastDataLinkHalibutBank = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46146";
    private String pastDataLinkGeorgiaStraight = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46303";
    private String pastDataLinkEnglishBay = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46304";
    private String pastDataLinkSentryShoal = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46131";

    @Override // Working with Action Bar:
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapButton:
                openActivity3();
                break;
            case R.id.aboutTheApp:
                openActivity4();
                break;
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
        historicalButton = findViewById(R.id.button);
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
                Toast.makeText(getApplicationContext(), "You selected: " + selectedItem, Toast.LENGTH_LONG).show();   //SystemOutPrint at the bottom of the page.
                String descriptionOfTheApplication = "This application displays real time information about marine situation at "+ selectedItem; // Adjusting header to selected item.

                tvDescription = findViewById(R.id.tvDescripationID);
                tvDescription.setText(descriptionOfTheApplication);

                switch (i){
                    case 0:
                        GetDataFromHalibutBank halibutB = new GetDataFromHalibutBank();
                        buoyInfo = halibutB.getCurrentDataFromHalibutBank(HalibutBank);
                        waveHigh = halibutB.extractCurrentWaveHighFromBuoy(HalibutBank);
                        coloringCrossabilityStripOfGeorgiaStrait(waveHigh);
                        fillCrossabilityStripData(buoyInfo);
                        historicalButton.setText("Past 24 Hour Conditions for Halibut Bank");
                        pastDataLink = pastDataLinkHalibutBank;
                        break;

                        case 1:
                            GetDataFromHalibutBank englishB = new GetDataFromHalibutBank();
                            buoyInfo = englishB.getCurrentDataFromHalibutBank(EnglishBay);
                            waveHigh = englishB.extractCurrentWaveHighFromBuoy(EnglishBay);
                            coloringCrossabilityStripOfGeorgiaStrait(waveHigh);
                            fillCrossabilityStripData(buoyInfo);
                            historicalButton.setText("Past 24 Hour Conditions for English Bay");
                            pastDataLink = pastDataLinkEnglishBay;
                            break;

                            case 2:
                                GetDataFromHalibutBank georgiaS = new GetDataFromHalibutBank();
                                buoyInfo = georgiaS.getCurrentDataFromHalibutBank(GeorgiaStrait);
                                waveHigh = georgiaS.extractCurrentWaveHighFromBuoy(GeorgiaStrait);
                                coloringCrossabilityStripOfGeorgiaStrait(waveHigh);
                                fillCrossabilityStripData(buoyInfo);
                                historicalButton.setText("Past 24 Hour Conditions for Georgia Strait");
                                pastDataLink = pastDataLinkGeorgiaStraight;
                                break;

                                case 3:
                                    GetDataFromHalibutBank sentryS = new GetDataFromHalibutBank();
                                    buoyInfo = sentryS.getCurrentDataFromHalibutBank(SentryShoal);
                                    waveHigh = sentryS.extractCurrentWaveHighFromBuoy(SentryShoal);
                                    coloringCrossabilityStripOfGeorgiaStrait(waveHigh);
                                    fillCrossabilityStripData(buoyInfo);
                                    historicalButton.setText("Past 24 Hour Conditions for Sentry Shoal");
                                    pastDataLink = pastDataLinkSentryShoal;
                                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));

        //********************************** Historical part is not working experiment yet
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
       // Intent intent = new Intent(this, Activity2.class);
     //   startActivity(intent);
  //  getPastData();
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("pastDataLink", pastDataLink);
        startActivity(intent);
}

public void openActivity3(){
    Intent intent = new Intent(this, PictureMapButNotMap.class);
    startActivity(intent);
}

public void openActivity4(){
    Intent intent = new Intent(this, ActivityAbout.class);
    startActivity(intent);
}

// Methods working with data:
    public void coloringCrossabilityStripOfGeorgiaStrait(String getWaveHigh) {

        String headerOfCrossingSituation = "This is current recommendation for you: ";
        tvCrossabilitySituation = findViewById(R.id.TVID4);
        TextView crossabilitySituationHeader = tvCrossabilitySituation;
        crossabilitySituationHeader.setText(headerOfCrossingSituation);

        tvStripeChangingColors = findViewById(R.id.TVId3);
        TextView crossingStripe = tvStripeChangingColors;
        if (getWaveHigh.equals("nope")) {
            crossingStripe.setText("Figure out where internet is gone. ");
        } else {
            try {
                double intWaveHigh = Double.parseDouble(getWaveHigh);
                if (intWaveHigh <= 0.1) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#4CAF50"));
                    crossingStripe.setText("Perfect conditions for crossing. Flat calm.");
                } else if (intWaveHigh <= 0.2) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#FFEB3B"));
                    crossingStripe.setText("Comfortable crossing.");
                } else if (intWaveHigh <= 0.3) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#FBC02D"));
                    crossingStripe.setText("Possible but NOT Comfortable crossing.");
                } else if (intWaveHigh <= 0.4) {
                    crossingStripe.setBackgroundColor(Color.parseColor("#FF5722"));
                    crossingStripe.setText("It's possible but you won't be happy crossing it.");
                } else if (intWaveHigh <= 0.5) {
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

    public void fillCrossabilityStripData(String waveHigh){
        tvDataDisplayed = findViewById(R.id.tvDataDisplayedID);
        tvDataDisplayed.setText(waveHigh);
    }

}
