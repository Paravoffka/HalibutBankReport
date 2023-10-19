package com.Halibut.halibutbankreport;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Adding back button. Don't forget to change manifest.
        setContentView(R.layout.activity_2);
        String[][] pastDataFromTheBuoy;
        String pastDataLink = getIntent().getStringExtra("pastDataLink");

        // Determine the header text based on pastDataLink
        String headerText;
        if (pastDataLink != null) {
            if (pastDataLink.contains("46146")) {
                headerText = "Past data for Halibut Bank";
            } else if (pastDataLink.contains("46304")) {
                headerText = "Past data for English Bay";
            }  else if (pastDataLink.contains("46303")) {
                headerText = "Past data for Georgia Straight";
            }  else if (pastDataLink.contains("46131")) {
                headerText = "Past data for Sentry Shoal";
            }  else {
                headerText = "Past Data"; // Default header text if pastDataLink doesn't match any specific case
            }
        } else {
            headerText = "Past Data"; // Default header text if pastDataLink is null
        }

        setTitle(headerText); // Set the title of the activity

        GetDataFromHalibutBank pastData = new GetDataFromHalibutBank();
        pastDataFromTheBuoy = pastData.getPastDataFromTheBuoy(pastDataLink);
        init(pastDataFromTheBuoy);


        // Adding some Add:

        // Initialize and load the ad
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewActivity2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }


public void init(String[][] pastDataFromTheBuoy) {
    TableLayout stk = (TableLayout) findViewById(R.id.table_main);

    // Create the header row
    TableRow headerRow = new TableRow(this);
    String[] headerLabels = {
            "Date / Time (PDT) ",
            " Wind (knots) ",
            " Wave height (m) ",
            " Wave period (s) ",
            " Pressure (kPa) ",
            " Air temp (°C) ",
            " Water temp (°C)"
    };
    for (String label : headerLabels) {
        TextView headerTextView = new TextView(this);
        headerTextView.setText(label);
        headerTextView.setTextColor(Color.WHITE);
        headerRow.addView(headerTextView);
    }
    stk.addView(headerRow);

    for (String[] rowData : pastDataFromTheBuoy) {
        boolean hasData = false;
        TableRow dataRow = new TableRow(this);
        for (String cellValue : rowData) {
            if (cellValue != null && !cellValue.isEmpty()) {
                hasData = true;
                TextView dataTextView = new TextView(this);
                dataTextView.setText(cellValue);
                dataTextView.setTextColor(Color.WHITE);
                dataTextView.setGravity(Gravity.CENTER);
                dataRow.addView(dataTextView);
            }
        }
        if (hasData) {
            stk.addView(dataRow);
        }
    }
}


}
