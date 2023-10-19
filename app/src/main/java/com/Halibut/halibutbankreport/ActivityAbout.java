package com.Halibut.halibutbankreport;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAbout extends AppCompatActivity {

    TextView about;
    String infoAboutTheApp = "This application is designed specifically for Vancouver saltwater fishermen to help them choose the right seaworthy conditions and enjoy their time on the water. \n" +
            "\n" +
            "Disclaimer for Halibut Bank: \n" +
                    "\n" +
            "We are doing our best to prepare the content of this app. However, Halibut Bank cannot warrant the expressions and suggestions in the contents, as well as their accuracy." +
            " In addition, to the extent permitted by the law, Halibut Bank shall not be responsible for any losses and/or damages due to the usage of the information on our app. \n"
            + "\n" +
            "By using our app, you hereby consent to our disclaimer and agree to its terms. \n"
            + "\n" +
            "Any links contained in our app that may lead to external sites are provided for convenience only. Any information or statements that appear " +
            "on these sites or the app are not sponsored, endorsed, or otherwise approved by Halibut Bank. For these external sites, Halibut Bank cannot be held liable for the availability of the content located on or" +
            " through them, or for any losses or damages that occur from using Halibut Bank or these contents or the internet in general."
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Adding back button. Don't forget to change manifest.

        about = findViewById(R.id.AppInfo);
        about.setText(infoAboutTheApp);
    }
}
