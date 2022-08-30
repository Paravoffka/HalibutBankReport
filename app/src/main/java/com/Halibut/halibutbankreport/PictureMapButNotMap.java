package com.Halibut.halibutbankreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

public class PictureMapButNotMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_map_but_not_map);

        getSupportActionBar().setTitle("Buoys location in Georgia Straight"); //Creating title of the activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Adding Back button. Don't forget change manifest.

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.halibutpicenglwithmarkers);
    }
}
