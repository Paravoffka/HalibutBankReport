package com.Halibut.halibutbankreport;


import android.graphics.Color;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CrossabilityStrip extends MainActivity {


    public void crossibilityStripOfGeorgeaStrait(String getwaveHigh) {
      //  setContentView(R.layout.activity_main);

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
}
