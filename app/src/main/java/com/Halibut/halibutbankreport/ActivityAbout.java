package com.Halibut.halibutbankreport;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAbout extends AppCompatActivity {

    TextView about;
    String infoAboutTheApp = "This application is designed specifically for Vancouver Saltwater fisherman in order to help to pick right seaworthy conditions and enjoy time on the water.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Adding back button. Don't forget to change manifest.

        about = findViewById(R.id.AppInfo);
        about.setText(infoAboutTheApp);
    }
}
