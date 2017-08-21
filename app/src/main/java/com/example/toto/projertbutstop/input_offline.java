package com.example.toto.projertbutstop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class input_offline extends AppCompatActivity {

    private String startString, endString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_offline);
        Toast.makeText(input_offline.this, "กรุณาใส่ป้ายรถประจำทาง", Toast.LENGTH_SHORT).show();

        //Start Controller
        startController();


        //End Controller
        endController();

        //OK Controller
        OKController();


    }   // Main Method

    private void OKController() {
        Button button = (Button) findViewById(R.id.Ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("18AugV4", "Start ==> " + startString);
                Log.d("18AugV4", "End ==> " + endString);

                Intent intent = new Intent(input_offline.this, listbus_offline.class);
                intent.putExtra("Start", startString);
                intent.putExtra("End", endString);

                startActivity(intent);


            }   // onClick
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String tag = "18AugV2";

        try {
            Log.d(tag, "requestCode ==> " + requestCode);
            switch (requestCode) {
                case 1000:

                    TextView textView = (TextView) findViewById(R.id.RecommendinputbusStart);
                    startString = data.getStringExtra("BusStop");
                    textView.setText("ป้ายเริ่มต้น คือ " + data.getStringExtra("BusStop"));

                    break;
                case 1100:

                    TextView textView1 = (TextView) findViewById(R.id.RecommendinputbusEnd);
                    endString = data.getStringExtra("BusStop");
                    textView1.setText("ป้ายจุดหมาย คือ " + data.getStringExtra("BusStop"));

                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void endController() {
        Button button = (Button) findViewById(R.id.InputbusEnd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(input_offline.this, MySearchView.class);
                intent.putExtra("Key", 1100);
                startActivityForResult(intent, 1100);
            }
        });
    }

    private void startController() {
        Button button = (Button) findViewById(R.id.InputbusStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(input_offline.this, MySearchView.class);
                intent.putExtra("Key", 1000);
                startActivityForResult(intent, 1000);

            }   // onClick
        });
    }
    public void OnclickCancel (View view) {
        finish();
    }

}   // Main Class
