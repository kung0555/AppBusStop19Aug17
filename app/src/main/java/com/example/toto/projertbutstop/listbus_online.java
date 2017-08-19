package com.example.toto.projertbutstop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class listbus_online extends AppCompatActivity {
    double latSearch;
    double lngSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbus_online);

        //รับข้อมูล
        intentBusStop();
        //setup Location
        setupLocation();
    }

    private void setupLocation() {
    }

    private void intentBusStop() {
        Bundle LatLng = getIntent().getExtras();
        if (LatLng != null) {
            latSearch = LatLng.getDouble("LatSearch");
            lngSearch = LatLng.getDouble("LngSearch");
            Toast.makeText(getApplicationContext(), "รับLatSearch  " + latSearch + "\nรับLngSearch " + lngSearch, Toast.LENGTH_SHORT).show();

        }
    }
}