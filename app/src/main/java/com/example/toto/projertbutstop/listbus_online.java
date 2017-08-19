package com.example.toto.projertbutstop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class listbus_online extends AppCompatActivity implements OnMapReadyCallback,DirectionCallback {
    double latSearch;
    double lngSearch;
    double latStartADouble;
    double lngStartADouble;
    private LocationManager locationManager;
    private GoogleMap googleMap;
    private String serverKey = "AIzaSyANztP01h4SRxFBJjiKLrxm5uWP1yCQr4E";
    String x1;
    String x2;
    String x3;
    String x4;
    String x5;
    String x6;
    double x7;
    double x8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbus_online);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTest)).getMapAsync(this);


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
            //Toast.makeText(getApplicationContext(), "รับLatSearch  " + latSearch + "\nรับLngSearch " + lngSearch, Toast.LENGTH_SHORT).show();

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
        }
        //for network
//        else if (networkLocation != null) {
//            latStartADouble = networkLocation.getLatitude();
//            lngStartADouble = networkLocation.getLongitude();
//        }
        else if (gpsLocation == null) {
            Toast.makeText(getApplicationContext(), "ไม่มี GPS", Toast.LENGTH_SHORT).show();
        }
        requestDirection();

    }
    private void requestDirection() {
        LatLng origin = new LatLng(13.778512, 100.507915);
        LatLng destination = new LatLng(13.787855, 100.490267);
        GoogleDirection.withServerKey(serverKey)
                .from(new LatLng(latStartADouble,lngStartADouble))
                //.from(origin)
                //.to(destination)
                .to(new LatLng(latSearch, lngSearch))
                .language(Language.THAI)
                .transportMode(TransportMode.TRANSIT)
                .unit(Unit.METRIC)
                .transitMode(TransitMode.BUS)
                .execute(this);

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        String status = direction.getStatus();
        if (status.equals(RequestResult.OK)) {
            Toast toast = Toast.makeText(this, "ค้นหาเส้นทางสำเร็จ", Toast.LENGTH_LONG);
            toast.show();


            ArrayList<LatLng> sectionPositionList = direction.getRouteList().get(0).getLegList().get(0).getSectionPoint();
            for (LatLng position : sectionPositionList) {
                googleMap.addMarker(new MarkerOptions().position(position));
            }

            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, Color.RED, 3, Color.BLUE);
            for (PolylineOptions polylineOption : polylineOptionList) {
                googleMap.addPolyline(polylineOption);
            }

            Button b1 = (Button) findViewById(R.id.buttOnline);
            String z = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(0).getTravelMode();
            String a = "WALKING";
            boolean gg = z.equals(a);
            int i = 0;
            if (gg==true) {
                i=1;
                x1 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getLine().getShortName();
                x2 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getStopNumber();
                x3 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getLine().getName();
                x4 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getDistance().getText();
                x5 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getDepartureStopPoint().getName();
                x6 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getArrivalStopPoint().getName();
                x7 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getArrivalStopPoint().getLocation().getLatitude();
                x8 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getArrivalStopPoint().getLocation().getLongitude();
                b1.setText("รถประจำทางสาย " + x1);
            }
            else {
                x1 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getLine().getShortName();
                x2 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getStopNumber();
                x3 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getLine().getName();
                x4 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getDistance().getText();
                x5 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getDepartureStopPoint().getName();
                x6 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getArrivalStopPoint().getName();
                x7 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getArrivalStopPoint().getLocation().getLatitude();
                x8 = direction.getRouteList().get(0).getLegList().get(0).getStepList().get(i).getTransitDetail().getArrivalStopPoint().getLocation().getLongitude();
                b1.setText("รถประจำทางสาย " + x1);

            }
        }
        else if (status.equals(RequestResult.NOT_FOUND)) {
            Toast toast = Toast.makeText(this, "NOT_FOUND", Toast.LENGTH_LONG);
            toast.show();
        } else if (status.equals(RequestResult.ZERO_RESULTS)) {
            Toast toast = Toast.makeText(this, "ไม่มีเส้นทาง", Toast.LENGTH_LONG);
            toast.show();
        } else if (status.equals(RequestResult.MAX_WAYPOINTS_EXCEEDED)) {
            Toast toast = Toast.makeText(this, "MAX_WAYPOINTS_EXCEEDE", Toast.LENGTH_LONG);
            toast.show();
        } else if (status.equals(RequestResult.REQUEST_DENIED)) {
            Toast toast = Toast.makeText(this, "REQUEST_DENIED", Toast.LENGTH_LONG);
            toast.show();
        } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {
            Toast toast = Toast.makeText(this, "UNKNOWN_ERROR", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast toast = Toast.makeText(this, "ไม่สามารถค้นหาได้", Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latSearch, lngSearch), 16));

    }
    public void Onclick1(View view) {
        Intent intentt = new Intent(listbus_online.this, notifications_online.class);
        intentt.putExtra("รถประจำทางสาย", x1);
        intentt.putExtra("ต้องผ่านทั้งหมด", x2);
        intentt.putExtra("วิ่งจาก", x3);
        intentt.putExtra("ระยะทาง", x4);
        intentt.putExtra("ป้ายรถประจำทางเริ่มต้น", x5);
        intentt.putExtra("ป้ายรถประจำทางปลายทาง", x6);
        intentt.putExtra("LatBusStopEnd", x7);
        intentt.putExtra("LngBusStopEnd", x8);

        startActivity(intentt);
    }
}