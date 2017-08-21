package com.example.toto.projertbutstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class loaddata extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide(); // ลบแทบด้านบนของแอปพลิเคชั่น
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);

        //Create SQLite or Connected
        myManage = new MyManage(loaddata.this);

        //Check Database
        String tag = "18AugV1";
        if (checkDatabase()) {
            //Have Database
            Log.d(tag, "Have Database");
            checkNetAndUpdateSQLite(true);
        } else {
            //No Database
            Log.d(tag, "No Database");
            checkNetAndUpdateSQLite(false);
        }

    }//Main method
    private void deleteAllData() {

        SQLiteDatabase objSQLite = openOrCreateDatabase("Busstop.db", MODE_PRIVATE, null);
        objSQLite.delete("busstopTABLE", null, null);
        objSQLite.delete("busTABLE", null, null);
        objSQLite.delete("busrouteTABLE",null,null);

    }


    private void checkNetAndUpdateSQLite(boolean statusHaveDatabase) {
        String tag = "18AugV1";
        if (checkInternet()) {
            //Connected Internet OK
            Log.d(tag, "Connected Internet OK");
            deleteAllData();
            refreshSQLite();
        } else {
            //Cannot Connected Internet Intent ==> Home.java
            Log.d(tag, "Cannot Connected Internet Intent ==> Home.java");
            if (statusHaveDatabase) {
                //Have Old Data
                Log.d(tag, "Have Old Data");
                myIntent();
            } else {
                //Empty Data and No Internet ==> Load Data from Server
                Log.d(tag, "Empty Data and No Internet ==> Load Data from Server");
                Toast.makeText(loaddata.this, "ไม่สามารถทำงานได้", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void myIntent() {
        Intent intent = new Intent(loaddata.this, home.class);
        startActivity(intent);
        finish();
    }

    private void refreshSQLite() {

        try {

            MyConstant myConstant = new MyConstant();
            String[] urlStrings = new String[]{myConstant.getUrlJSON_BusStop(),
                    myConstant.getUrlJSON_Bus(), myConstant.getUrlJSON_BusRoute()};

            for (int i = 0; i < urlStrings.length; i += 1) {

                GetAllData getAllData = new GetAllData(loaddata.this);
                getAllData.execute(urlStrings[i]);
                String strJSON = getAllData.get();
                Log.d("17AugV1", "JSoN ==> " + strJSON);

                myUpdateSQLite(i, strJSON);

            }   // for

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myIntent();
                }
            }, 3000);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void myUpdateSQLite(int index, String strJSON) {

        try {

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MyManage myManage = new MyManage(loaddata.this);

                switch (index) {

                    case 0: // for BusStop

                        String strCOLUMN_ID_BUSSTOP = jsonObject.getString("id");
                        String strCOLUMN_Lat = jsonObject.getString("X");
                        String strCOLUMN_Lng = jsonObject.getString("Y");
                        String strCOLUMN_Namebusstop = jsonObject.getString("Namebusstop");

                        myManage.addBusStop(strCOLUMN_ID_BUSSTOP, strCOLUMN_Lat,
                                strCOLUMN_Lng, strCOLUMN_Namebusstop);

                        break;

                    case 1: // for Bus

                        String strCOLUMN_ID_BUS = jsonObject.getString("id_bus");
                        String strCOLUMN_bus = jsonObject.getString("bus");
                        String strCOLUMN_bus_details = jsonObject.getString("bus_details");

                        myManage.addBus(strCOLUMN_ID_BUS, strCOLUMN_bus, strCOLUMN_bus_details);

                        break;

                    case 2: // for BusRoute

                        String strCOLUMN_id_busroute  = jsonObject.getString("id_busroute");
                        String strCOLUMN_direction = jsonObject.getString("direction");
                        String strCOLUMN_bus1 = jsonObject.getString("bus");
                        String strCOLUMN_bus_details1 = jsonObject.getString("bus_details");
                        String strCOLUMN_Namebusstop1 = jsonObject.getString("Namebusstop");
                        String strCOLUMN_Lat1 = jsonObject.getString("X");
                        String strCOLUMN_Lng1 = jsonObject.getString("Y");

                        myManage.addBusRoute(strCOLUMN_id_busroute, strCOLUMN_direction,
                                strCOLUMN_bus1, strCOLUMN_bus_details1, strCOLUMN_Namebusstop1,
                                strCOLUMN_Lat1, strCOLUMN_Lng1);

                        break;

                }   // switch


            }   //for

        } catch (Exception e) {
            Log.d("17AugV1", "e ==> " + e.toString());
        }

    }



    private boolean checkInternet() {
        boolean result = false; // No Internet
        ConnectivityManager connectivityManager = (ConnectivityManager) loaddata.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo != null) && (networkInfo.isConnected())) {
            result  = true; // Have Internet
        }
        return result;
    }

    private boolean checkDatabase() {
        boolean result = true; // Have Database
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busstopTABLE", null);
        cursor.moveToFirst();
        Log.d("18AugV1", "cusor.count ==> " + cursor.getCount());
        if (cursor.getCount() == 0) {
            result = false; // No Database
        }
        return result;
    }





}   // Main Class