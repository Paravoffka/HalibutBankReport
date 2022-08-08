package com.Halibut.halibutbankreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
    TextView tvCelltest6;
    TextView tvCell_1;
    TextView tvCell_2;
    TextView tvCell_3;
    TextView tvCell_4;
    TextView tvCell_5;
    TextView tvCell_6;
    TextView tvCell_7;
    TextView tvCell_8;
    TextView tvCell_9;
    TextView tvCell_10;
    TextView tvCell_11;
    TextView tvCell_12;
    TextView tvCell_13;
    TextView tvCell_14;
    TextView tvCell_15;
    TextView tvCell_16;
    TextView tvCell_17;
    TextView tvCell_18;
    TextView tvCell_19;
    TextView tvCell_20;
    TextView tvCell_21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        historyTableContent();
    }

    public void historyTableContent(){
        tvCell_1 = findViewById(R.id.cell_1);
        tvCell_2 = findViewById(R.id.cell_2);
        tvCell_3 = findViewById(R.id.cell_3);
        tvCell_4 = findViewById(R.id.cell_4);
        tvCell_5 = findViewById(R.id.cell_5);
        tvCell_6 = findViewById(R.id.cell_6);
        tvCell_7 = findViewById(R.id.cell_7);
        tvCell_8 = findViewById(R.id.cell_8);
        tvCell_9 = findViewById(R.id.cell_9);
        tvCell_10 = findViewById(R.id.cell_10);
        tvCell_11 = findViewById(R.id.cell_11);
        tvCell_12 = findViewById(R.id.cell_12);
        tvCell_13 = findViewById(R.id.cell_13);
        tvCell_14 = findViewById(R.id.cell_14);
        tvCell_15 = findViewById(R.id.cell_15);
        ArrayList<TextView> arraylisttextiew = new ArrayList<TextView>();
        arraylisttextiew.add(tvCell_1);
        arraylisttextiew.add(tvCell_2);
        arraylisttextiew.add(tvCell_3);
        arraylisttextiew.add(tvCell_4);
        arraylisttextiew.add(tvCell_5);
        arraylisttextiew.add(tvCell_6);
        arraylisttextiew.add(tvCell_7);
        arraylisttextiew.add(tvCell_8);
        arraylisttextiew.add(tvCell_9);
        arraylisttextiew.add(tvCell_10);
        arraylisttextiew.add(tvCell_11);
        arraylisttextiew.add(tvCell_12);
        arraylisttextiew.add(tvCell_13);
        arraylisttextiew.add(tvCell_14);
        arraylisttextiew.add(tvCell_15);
        arraylisttextiew.add(tvCell_16);
        arraylisttextiew.add(tvCell_17);
        arraylisttextiew.add(tvCell_18);
        arraylisttextiew.add(tvCell_19);
        arraylisttextiew.add(tvCell_20);
        arraylisttextiew.add(tvCell_21);


        tvCelltest6 = findViewById(R.id.cell6);

        ArrayList<String> glist = new ArrayList<String>();
      /*  try {
            Document wave = Jsoup.connect("https://weather.gc.ca/marine/weatherConditions-24hrObsHistory_e.html?mapID=02&siteID=14305&stationID=46146").get();
            Elements elem = wave.select("table.table"); //tbody
            String yacheika;
            for (Element e :elem.select("tr td")){

                yacheika =  e.select("td.t-center").text().trim();
                glist.add(yacheika);
            }

            System.out.println("Size is:  " + glist.size());

            TextView toprint;
            for (int i = 0; i < 21; i++) {
               TextView  = findViewById(R.id.cell_+(i));
                toprint = arraylisttextiew.get(i);
                toprint.setText(glist.get(i));
            }
        }
        catch (Exception e) {
            System.out.println("No Internet :(");

        }*/
    }

}
