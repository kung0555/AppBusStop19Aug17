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
import android.widget.Toast;

public class listbus_online extends AppCompatActivity {
    double latSearch;
    double lngSearch;
    double latStartADouble;
    double lngStartADouble;
    private LocationManager locationManager;


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
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latChanged = location.getLatitude();
            double lngChanged = location.getLongitude();
            Toast.makeText(getApplicationContext(), "LatChang  " + latChanged + "\nlngChang " + lngChanged, Toast.LENGTH_SHORT).show();

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

    private void intentBusStop() {
        Bundle LatLng = getIntent().getExtras();
        if (LatLng != null) {
            latSearch = LatLng.getDouble("LatSearch");
            lngSearch = LatLng.getDouble("LngSearch");
            Toast.makeText(getApplicationContext(), "รับLatSearch  " + latSearch + "\nรับLngSearch " + lngSearch, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        //for GPS
        if (gpsLocation != null) {
            latStartADouble = gpsLocation.getLatitude();
            lngStartADouble = gpsLocation.getLongitude();
            Toast.makeText(getApplicationContext(), "MyLocationLat  "+latStartADouble +"\nMyLocationLng "+lngStartADouble, Toast.LENGTH_SHORT).show();

        }//for network
        else if (networkLocation != null) {
            latStartADouble = networkLocation.getLatitude();
            lngStartADouble = networkLocation.getLongitude();
        }
        else if (gpsLocation == null) {
            Toast.makeText(getApplicationContext(), "ไม่มี GPS", Toast.LENGTH_SHORT).show();
        }

    }
}