package com.example.toto.projertbutstop;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class input_online extends AppCompatActivity {

    private static final int REQUEST_VOIC_RECOGINITION = 100; // สร้างค่าเพื่อเช็คว่าส่งมาอันเดียวกันไม
    @Bind(R.id.Microphome)
    ImageButton Microphome;
    @Bind(R.id.InputbusEnd)
    EditText InputbusEnd;
    @Bind(R.id.Ok)
    Button Ok;
    @Bind(R.id.Cancel)
    Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_online);
        Toast.makeText(this, "ใส่ป้ายรถประจำทางที่ต้องการ", Toast.LENGTH_SHORT).show();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.Microphome, R.id.InputbusEnd, R.id.Ok, R.id.Cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Microphome:
                callVoiceRecognition();
                break;
            case R.id.InputbusEnd:
                break;
            case R.id.Ok:
                Checkinput();
                break;
            case R.id.Cancel:
                Intent intentt = new Intent(input_online.this, home.class);
                startActivity(intentt);
                //finish();
                break;
        }
    }

    private void Checkinput() {
        String textinput = InputbusEnd.getText().toString();
        if (textinput.matches("")) {
            Toast.makeText(this, "กรุณาใส่ป้ายรถประจำทางเป้าหมาย", Toast.LENGTH_SHORT).show();
        } else {
            Geocoder gc = new Geocoder(this);
            List<Address> list = null;
            try {
                list = gc.getFromLocationName(textinput, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = list.get(0);
            String locality = address.getLocality();

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            Toast.makeText(this, locality + "ละติจูด" + lat + "ลองติจูด" + lng, Toast.LENGTH_LONG).show();

            if (address != null) {
                Intent intent = new Intent(input_online.this, listbus_online.class);
                intent.putExtra("LatSearch", lat);
                intent.putExtra("LngSearch", lng);
                startActivity(intent);
                //finish();
            }

        }
    }

    private void callVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        startActivityForResult(intent, REQUEST_VOIC_RECOGINITION);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VOIC_RECOGINITION &&
                resultCode == RESULT_OK &&
                data != null) {
            ArrayList<String> resultList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            InputbusEnd.setText("");
            String name = resultList.get(0);
            InputbusEnd.setText(name);
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }
}