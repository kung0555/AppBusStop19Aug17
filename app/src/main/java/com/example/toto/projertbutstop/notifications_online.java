package com.example.toto.projertbutstop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class notifications_online extends AppCompatActivity {
    String Busnum;
    String Sum;
    String StartEnd;
    String KM;
    String StopFirst;
    String StopEnd;
    double LatBusStopEnd;
    double LngBusStopEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_online);
        intentlistonline();
    }

    private void intentlistonline() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Busnum = bundle.getString("รถประจำทางสาย");
            StartEnd = bundle.getString("วิ่งจาก");
            StopFirst = bundle.getString("ป้ายรถประจำทางเริ่มต้น");
            StopEnd = bundle.getString("ป้ายรถประจำทางปลายทาง");
            Sum = bundle.getString("ต้องผ่านทั้งหมด");
            KM = bundle.getString("ระยะทาง");
            LatBusStopEnd = bundle.getDouble("LatBusStopEnd");
            LngBusStopEnd = bundle.getDouble("LngBusStopEnd");


            TextView tg1 = (TextView) findViewById(R.id.Numberbus);
            TextView tg2 = (TextView) findViewById(R.id.Nowbus);
            TextView tg3 = (TextView) findViewById(R.id.NameStopFirst);
            TextView tg4 = (TextView) findViewById(R.id.NextStopEnd);
            TextView tg5 = (TextView) findViewById(R.id.SumBusStop);
            TextView tg6 = (TextView) findViewById(R.id.Distance);



            tg1.setText("รถประจำทางสาย " + Busnum);
            tg2.setText("เส้นทางเดินรถ " + StartEnd);
            tg3.setText("ป้ายรถประจำทางเริ่มต้น " + StopFirst);
            tg4.setText("ป้ายรถประจำทางปลายทาง " + StopEnd);
            tg5.setText("ต้องผ่านทั้งหมด " + Sum + " ป้าย");
            tg6.setText("ระยะทาง " + KM);

        }
    }
}