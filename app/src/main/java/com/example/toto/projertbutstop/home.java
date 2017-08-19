package com.example.toto.projertbutstop;

import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class home extends AppCompatActivity {
    @Bind(R.id.Onlinemode)
    Button Onlinemode;
    @Bind(R.id.Offlinemode)
    Button Offlinemode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toast.makeText(this, "เลือกระบบการใช้งาน ออนไลน์ กดด้านซ้ายของหน้าจอ ออฟไลน์ กดด้านขวาของหน้าจอ", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.Onlinemode, R.id.Offlinemode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Onlinemode: {
                CheckGPS();
                Log.d("toto", "gg" + CheckGPS());
                checkInternet();
                Log.d("toto", "hh" + checkInternet());
                if (CheckGPS() == false) {
                    Toast.makeText(this, "คุณไม่ได้เปิดจีพีเอส กรุณาเปิดจีพีเอส", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else if (CheckGPS() == true) {
                    Toast.makeText(this, "คุณกำลังเปิดจีพีเอส", Toast.LENGTH_SHORT).show();
                    if (checkInternet() == true) {
                        Toast.makeText(this, "คุณเชื่อมต่อจีพีเอสและอินเตอร์เน็ต", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, input_online.class);
                        startActivity(intent);
                    } else if (checkInternet() == false) {
                        Toast.makeText(this, "คุณไม่ได้เชื่อมต่ออินเตอร์เน็ต กรุณาเปิดอินเตอร์เน็ต", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                }
            }
            break;
            case R.id.Offlinemode: {
                CheckGPS();
                if (CheckGPS() == false) {
                    Toast.makeText(this, "คุณไม่ได้เปิดจีพีเอส กรุณาเปิดจีพีเอส", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else if (CheckGPS() == true) {
                    Toast.makeText(this, "คุณกำลังเปิดจีพีเอส", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, input_offline.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    private boolean checkInternet() {
        boolean result = false; // No Internet
        ConnectivityManager connectivityManager = (ConnectivityManager) home.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo != null) && (networkInfo.isConnected())) {
            result = true; // Have Internet
        }
        return result;
    }

    private boolean CheckGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean bolGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return bolGPS;
    }
}
