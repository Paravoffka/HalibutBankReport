package com.Halibut.halibutbankreportweather;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
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

import com.Halibut.halibutbankreport.R;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

//mport com.google.android.play.core.tasks.Task;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;


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
    // Declare isSoundEnabled here
    private boolean isSoundEnabled;

    private boolean notificationSent = false;
    private String HalibutBank = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46146";
    private String EnglishBay = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46304";
    private String GeorgiaStrait = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46303";
    private String SentryShoal = "https://weather.gc.ca/marine/weatherConditions-currentConditions_e.html?mapID=02&siteID=14305&stationID=46131";

    private String pastDataLink = "";

    private String pastDataLinkHalibutBank = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46146";
    private String pastDataLinkGeorgiaStraight = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46303";
    private String pastDataLinkEnglishBay = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46304";
    private String pastDataLinkSentryShoal = "https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46131";

    // Declare your BroadcastReceiver instance
    private BroadcastReceiver settingsChangedReceiver = new SettingsChangedReceiver();

    private static final String PREF_NOTIFICATION_SCHEDULED = "notificationScheduled";
    private SharedPreferences sharedPreferences;

    private static final int REQUEST_CODE_UPDATE = 123;
    private AppUpdateManager appUpdateManager;

    @SuppressLint("NonConstantResourceId")
    @Override // Working with Action Bar:
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapButton: // Here we attache 2 buttons for one activity
            case R.id.regionSummary:
                openActivity3();
                break;
            case R.id.aboutTheApp:
                openActivity4();
                break;
            case R.id.alarmSettings:
                openAlarmSettingsActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize the SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // Check if notification has been scheduled
        //     boolean isNotificationScheduled = sharedPreferences.getBoolean(PREF_NOTIFICATION_SCHEDULED, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the LoggerUtils
        LoggerUtils.initialize(this);

        // Example log
        LoggerUtils.log("MainActivity", "onCreate called");

/////------------ ADS:
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
          AdRequest adRequest = new AdRequest.Builder().build(); // This is for real ADS!!!!!!
        //***************The block below is for the test ADS    ------------------ Just comment block below and uncomment the line above to go back to real ADS!!!!
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)       // All emulators are considered test devices
//                .addTestDevice("560BA81F3D7D2DF60F8CCB7A87F55D32") // Add your device hash
//                .build();

        //**********This is Working ADS**************************
//        AdRequest adRequest = new AdRequest.Builder()
//                .setRequestAgent("my_app_version")
//                        .build();

        mAdView.loadAd(adRequest);
        LoggerUtils.log("adRequest","AD loaded...");

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

                // Schedule notification
                if (!Utils.isServiceRunning(getApplicationContext(), WebsiteCheckForegroundService.class)) {
                    NotificationHelper.scheduleNotification(getApplicationContext());
                }

                // Initialize the update manager
                appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this); // Useful video - https://www.youtube.com/watch?v=jHj_x1GwU7Q

                // Check for updates
                checkForUpdates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));

        //********************************** Historical table for past 24 hours
        historicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });


    }



    @Override //Adding custom buttons like View Map, Settings and so on
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buttons, menu);
        return true;
    }



    public void openActivity2(){
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
        //Testing hasmap:
        GetDataFromHalibutBank da = new GetDataFromHalibutBank();
        da.incomingDaysForecast("https://weather.gc.ca/marine/forecast_e.html?mapID=02&siteID=14305");
    }

    public void openAlarmSettingsActivity(){
        Intent intent = new Intent(this, AlarmSettingsActivity.class);
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


    @Override
    protected void onStop() {
        super.onStop();
        // startService(new Intent(this, NotificationService.class));
    }

//    private void checkForAppUpdates() {
//        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
//
//// Returns an intent object that you use to check for an update.
//        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//// Checks that the platform will allow the specified type of update.
//        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    // This example applies an immediate update. To apply a flexible update
//                    // instead, pass in AppUpdateType.FLEXIBLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//                // Request the update.
//                appUpdateManager.startUpdateFlowForResult(
//                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
//                        appUpdateInfo,
//                        // an activity result launcher registered via registerForActivityResult
//                      //  activityResultLauncher,
//                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
//                        AppUpdateType.IMMEDIATE,
//                        // flexible updates.
//                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build();
//            }
//        });
//    }
private void checkForUpdates() {
    Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

    appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
            // Request the update
            try {
                appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE_UPDATE);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode != RESULT_OK) {
                // Update failed, notify the user
                Toast.makeText(this, "Update failed! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}








