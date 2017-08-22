package com.example.toto.projertbutstop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class notifications_offline extends AppCompatActivity {
    private LocationManager locationManager;
    double latChanged;
    double lngChanged;
    double latStartADouble;
    double lngStartADouble;


    String Bus;
    int SumBus;
    String strSumBut;
    ArrayList<String> LatBus = new ArrayList<String>();
    ArrayList<String> LngBus = new ArrayList<String>();
    ArrayList<String> NameBus = new ArrayList<String>();
    int x =0;
    int p = 0;



    ArrayList<Double> TestLat = new ArrayList<Double>();
    ArrayList<Double> TestLng = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_offline);
        setupLocation();
        getValueIntent();
        forTestLatlng();
    }

    private void forTestLatlng() {
        for (int i = 0; i < LatBus.size(); i++) {
            TestLat.add(Double.parseDouble(LatBus.get(i)));
            Log.d("LocationListener", "TestLat " + TestLat);
        }
        for (int x = 0; x < LngBus.size(); x++) {
            TestLng.add(Double.parseDouble(LngBus.get(x)));
            Log.d("LocationListener", "TestLng " + TestLng);
        }
    }


    private void getValueIntent() {

        String tag = "getValueIntent";
        SumBus = getIntent().getIntExtra("SumBus", 0);
        LatBus = getIntent().getStringArrayListExtra("LatBus");
        LngBus = getIntent().getStringArrayListExtra("LngBus");
        NameBus = getIntent().getStringArrayListExtra("NameBus");
        Bus = getIntent().getStringExtra("Bus");
        strSumBut = Integer.toString(SumBus);
        //Log.d(tag,"SumBus ==>"+ SumBus);
        Log.d(tag, "LatBus ==>" + LatBus);
        Log.d(tag, "LngBus ==>" + LngBus);
        Log.d(tag, "NameBus ==>" + NameBus);
        Log.d(tag, "NameBus ==>" + Bus);

        TextView tg1 = (TextView) findViewById(R.id.NumberbusOff);
        TextView tg2 = (TextView) findViewById(R.id.Namebus1Off);
        TextView tg3 = (TextView) findViewById(R.id.Namebus2Off);
        TextView tg4 = (TextView) findViewById(R.id.NumberbusstopOff);

        tg1.setText("รถประจำทางสาย " + Bus);
        tg2.setText("ชื่อป้ายรถประจำทางปัจจุบัน : " + NameBus.get(0));
        tg3.setText("ชื่อป้ายรถประจำทางถัดไป : " + NameBus.get(1));
        tg4.setText("ต้องผ่านอีก " + strSumBut + " ป้าย");

    }

    private void setupLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;
        if (locationManager.isProviderEnabled(strProvider)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);
        }
        return location;
    }


    public LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            latChanged = location.getLatitude();
            lngChanged = location.getLongitude();
            ArrayList<Float> dis = new ArrayList<Float>();
            Toast.makeText(getApplicationContext(), "LatChang  " + latChanged + "\nlngChang " + lngChanged, Toast.LENGTH_SHORT).show();
            for (int a =0;a<LngBus.size();a++) {
                dis.add((float) distance(TestLat.get(a), TestLng.get(a), latChanged, lngChanged));
                Log.d("LocationListener", "dis " + dis);
            }
            if (dis.get(p) < 0.2 && dis.get(p) > 0.1) {
                if (x == 0) {
                    Toast.makeText(getApplicationContext(), "ใกล้ถึงแล้ว  "+NameBus.get(p), Toast.LENGTH_SHORT).show();
                    Log.d("Test19", "ใกล้ถึงป้าย." +NameBus.get(p)+"แล้ว");
                    x = 1;
                    Log.d("Test19", "x ==>" + x);
                }
                if (x == 2&&p!=dis.size()) {
                    if (p < dis.size()) {

                    } else {
                        Toast.makeText(getApplicationContext(), "ป้ายต่อไปคือ  " + NameBus.get(p + 1), Toast.LENGTH_SHORT).show();
                        Log.d("Test19", "ป้ายต่อไปคือ"+ NameBus.get(p + 1));
                    }

                    x = 0;

                    Log.d("Test19", "x ==>" + x);
                }
                if (x == 2&&p==dis.size()) {
                    Toast.makeText(getApplicationContext(), "เลยป้าย  "+NameBus.get(p), Toast.LENGTH_SHORT).show();
                    Log.d("Test19", "เลยป้าย"+NameBus.get(p));
                    Log.d("Test19", "x ==>" + x);
                }
            }
            if (dis.get(p) < 0.09) {
                if (x == 1) {
                    Toast.makeText(getApplicationContext(), "ถึงแล้วนะจ๊ะ  "+NameBus.get(p), Toast.LENGTH_SHORT).show();
                    Log.d("Test19", "ถึงแล้วนะจ๊ะ "+NameBus.get(p));
                    x = 2;
                    if (p <= dis.size()) {
                        p++;
                    }

                    Log.d("Test19", "p ==>" + p);
                }
            }



        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //for Network
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            latStartADouble = networkLocation.getLatitude();
            lngStartADouble = networkLocation.getLongitude();
        }
        //for GPS
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latStartADouble = gpsLocation.getLatitude();
            lngStartADouble = gpsLocation.getLongitude();
        }
        if (gpsLocation == null) {
            Toast.makeText(getApplicationContext(), "ไม่มี GPS", Toast.LENGTH_SHORT).show();

        }
    }

    //หาระยะทาง
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;

        return (dist);
    }

    private static double rad2deg(double rad) {

        return (rad * 180 / Math.PI);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public void OnclickEnd(View view) {
        finish();
    }
}

