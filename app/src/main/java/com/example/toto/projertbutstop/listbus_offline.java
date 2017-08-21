package com.example.toto.projertbutstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class listbus_offline extends AppCompatActivity {

    private String startString, endString;
    private String[] numberBusStrings;
    private String[] numberBusEndStrings;
    private ArrayList<String> myTrueNumberBusStartStringArrayListinTown;
    private ArrayList<String> myTrueNumberBusStartStringArrayListoutTown;
    private ArrayList<String> myTrueNumberBusEndStringArrayListinTown;
    private ArrayList<String> myTrueNumberBusEndStringArrayListoutTown;
    private ArrayList<String> BusPast;
    String strNumBus;
    ListView listView;
    int test;
    ArrayList<String> LstringArrayList = new ArrayList<String>();
    ArrayList<String> LstringArrayList2 = new ArrayList<String>();
    ArrayList<String> LstringArrayList3 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbus_offline);
        listView = (ListView) findViewById(R.id.Listbus);
        myTrueNumberBusStartStringArrayListinTown = new ArrayList<String>();
        myTrueNumberBusStartStringArrayListoutTown = new ArrayList<String>();
        myTrueNumberBusEndStringArrayListinTown = new ArrayList<String>();
        myTrueNumberBusEndStringArrayListoutTown = new ArrayList<String>();
        BusPast = new ArrayList<String>();





        //GetValue Intent
        getValueIntent();

        //Find NumberBus
        findNumberBusStart();
        findNumberBusEnd();
        findBusPast();




    }   // Main Method

    private void findBusPast() {
        if (myTrueNumberBusStartStringArrayListinTown.size()!=0&&myTrueNumberBusEndStringArrayListinTown.size()!=0) {
            for (int i = 0; i < myTrueNumberBusStartStringArrayListinTown.size(); i++) {
                for (int h = 0; h < myTrueNumberBusEndStringArrayListinTown.size(); h++) {
                    if (myTrueNumberBusStartStringArrayListinTown.get(i).equals(myTrueNumberBusEndStringArrayListinTown.get(h))) {
                        BusPast.add(myTrueNumberBusStartStringArrayListinTown.get(i));

                    }
                }
            }
        } else if (myTrueNumberBusEndStringArrayListinTown.size() != 0 && myTrueNumberBusEndStringArrayListoutTown.size() != 0) {
            for (int i = 0; i < myTrueNumberBusEndStringArrayListinTown.size(); i++) {
                for (int h = 0; h < myTrueNumberBusEndStringArrayListoutTown.size(); h++) {
                    if (myTrueNumberBusEndStringArrayListinTown.get(i).equals(myTrueNumberBusEndStringArrayListoutTown.get(h))) {
                        BusPast.add(myTrueNumberBusEndStringArrayListinTown.get(i));

                    }
                }
            }
        } else {
            BusPast.isEmpty();
            Toast.makeText(listbus_offline.this, "ไม่มีสายรถประจำทางผ่าน กดเลือกเพื่อใส่ป้ายรถประจำทางอีกครั้ง", Toast.LENGTH_SHORT).show();
        }
        //createAdapter
        BusPart_Adapter adapter = new BusPart_Adapter(listbus_offline.this,BusPast);
        listView.setAdapter(adapter);

        //Active Click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (BusPast.isEmpty()) {
                    Log.d("21Aug1", "BusPastStart  " + BusPast);
                    Intent intent = new Intent(listbus_offline.this, input_offline.class);
                    startActivity(intent);

                } else {
                    strNumBus = BusPast.get(i);
                    Log.d("21Aug1", " strNumBus " + strNumBus);
                    if (strNumBus != null) {
                        getRouteBus();
                        Intent intent = new Intent(listbus_offline.this, notifications_offline.class);
                        intent.putExtra("SumBus", test);
                        intent.putExtra("LatBus", LstringArrayList);
                        intent.putExtra("LngBus", LstringArrayList2);
                        intent.putExtra("NameBus", LstringArrayList3);
                        intent.putExtra("Bus", strNumBus);
                        startActivity(intent);
                    }

                }

            }
        });


        Log.d("20AugV2", "BusPastStart  " + BusPast);
        Log.d("20AugV2", "รถที่วิ่งผ่านป้าย Startไป ==> " + myTrueNumberBusStartStringArrayListinTown);
        Log.d("20AugV2", "รถที่วิ่งผ่านป้าย Endไป ==> " + myTrueNumberBusEndStringArrayListinTown);
    }



    private void findNumberBusEnd() {
        String tag = "18AugV4";
        ArrayList<String> stringArrayList = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE", null);
        cursor.moveToFirst();
        String[] strNumberBusManyStrings = new String[cursor.getCount()];
        for (int i=0;i<strNumberBusManyStrings.length; i++) {
            strNumberBusManyStrings[i] = cursor.getString(2);
            Log.d(tag, "bus[" + i + "] ==> " + strNumberBusManyStrings[i]);
            stringArrayList.add(strNumberBusManyStrings[i]);
            cursor.moveToNext();
        }   //for
        cursor.close();
        Log.d(tag, "stringArrayList ==> " + stringArrayList);
        Object[] objects = stringArrayList.toArray();
        for (Object object : objects) {
            if (stringArrayList.indexOf(object) != stringArrayList.lastIndexOf(object)) {
                stringArrayList.remove(stringArrayList.lastIndexOf(object));
            }
        }
        Log.d(tag, "last StringArrayList ==> " + stringArrayList);
        numberBusEndStrings = new String[stringArrayList.size()];
        numberBusEndStrings = stringArrayList.toArray(new String[0]);
        for (int i=0;i<numberBusEndStrings.length;i++) {
            Log.d(tag, "numbutBus[" + i + "] ==> " + numberBusEndStrings[i]);

            inTownCheckBusEnd(numberBusEndStrings[i], sqLiteDatabase);
            outTownCheckBusEnd(numberBusEndStrings[i], sqLiteDatabase);

        }   // for
        Log.d("20AugV1", "รถที่วิ่งผ่านป้าย Endไป ==> " + myTrueNumberBusEndStringArrayListinTown);
        Log.d("20AugV1", "รถที่วิ่งผ่านป้าย Endกลับ ==> " + myTrueNumberBusEndStringArrayListoutTown);
    }// findNumberBusEnd

    private void outTownCheckBusEnd(String numberBusString, SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE direction = 'ออกเมือง' AND bus = " + numberBusString, null);
        cursor.moveToFirst();

        for (int i=0;i<cursor.getCount();i++) {

            if (startString.equals(cursor.getString(4))) {
                myTrueNumberBusEndStringArrayListoutTown.add(numberBusString);
            }
            cursor.moveToNext();

        }   // for
    }

    private void inTownCheckBusEnd(String numberBusString, SQLiteDatabase sqLiteDatabase) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE  direction = 'เข้าเมือง' AND bus = " + numberBusString, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {

            if (endString.equals(cursor.getString(4))) {
                myTrueNumberBusEndStringArrayListinTown.add(numberBusString);
            }
            cursor.moveToNext();

        }   // for

    }

    private void findNumberBusStart() {
        String tag = "18AugV4";
        ArrayList<String> stringArrayList = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE", null);
        cursor.moveToFirst();
        String[] strNumberBusManyStrings = new String[cursor.getCount()];
        for (int i=0;i<strNumberBusManyStrings.length; i++) {
            strNumberBusManyStrings[i] = cursor.getString(2);
            Log.d(tag, "bus[" + i + "] ==> " + strNumberBusManyStrings[i]);
            stringArrayList.add(strNumberBusManyStrings[i]);
            cursor.moveToNext();
        }   //for
        cursor.close();

        Log.d(tag, "stringArrayList ==> " + stringArrayList);
        Object[] objects = stringArrayList.toArray();
        for (Object object : objects) {
            if (stringArrayList.indexOf(object) != stringArrayList.lastIndexOf(object)) {
                stringArrayList.remove(stringArrayList.lastIndexOf(object));
            }
        }

        Log.d(tag, "last StringArrayList ==> " + stringArrayList);
        numberBusStrings = new String[stringArrayList.size()];
        numberBusStrings = stringArrayList.toArray(new String[0]);
        for (int i=0;i<numberBusStrings.length;i++) {
            Log.d(tag, "numbutBus[" + i + "] ==> " + numberBusStrings[i]);

            inTownCheckBus(numberBusStrings[i], sqLiteDatabase);
            outTownCheckBus(numberBusStrings[i], sqLiteDatabase);

        }   // for




    }   // findNumberBusSart




    private void outTownCheckBus(String numberBusString, SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE direction = 'ออกเมือง' AND bus = " + numberBusString, null);
        cursor.moveToFirst();

        for (int i=0;i<cursor.getCount();i++) {

            if (startString.equals(cursor.getString(4))) {
                myTrueNumberBusStartStringArrayListoutTown.add(numberBusString);
            }
            cursor.moveToNext();

        }   // for
    }

    private void inTownCheckBus(String numberBusString, SQLiteDatabase sqLiteDatabase) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE bus = " + numberBusString, null);
        cursor.moveToFirst();

        for (int i=0;i<cursor.getCount();i++) {

            if (startString.equals(cursor.getString(4))) {
                myTrueNumberBusStartStringArrayListinTown.add(numberBusString);
            }
            cursor.moveToNext();

        }   // for


    }


    private void getValueIntent() {
        String tag = "18AugV4";
        startString = getIntent().getStringExtra("Start");
        endString = getIntent().getStringExtra("End");
        Log.d(tag, "rev Start ==> " + startString);
        Log.d(tag, "rev End ==> " + endString);


    }

    private void getRouteBus() {
        String Bus = strNumBus;
        String Busname = startString;
        ArrayList<String> RstringArrayList = new ArrayList<String>();
        ArrayList<String> RstringArrayList2 = new ArrayList<String>();
        ArrayList<String> RstringArrayList3 = new ArrayList<String>();
        ArrayList<String> RstringArrayList4 = new ArrayList<String>();
        String tag = "getRouteBus";
        Log.d(tag, "strNumBus ==> " + Bus);
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE bus = " + Bus, null);
        cursor.moveToFirst();
        String[] RStrings = new String[cursor.getCount()];
        String[] RStrings2 = new String[cursor.getCount()];
        String[] RStrings3 = new String[cursor.getCount()];
        for (int i = 0; i < RStrings.length; i++) {
            RStrings[i] = cursor.getString(4);
            RStrings2[i] = cursor.getString(0);
            RStrings3[i] = cursor.getString(1);
            Log.d(tag, "bus[" + i + "] ==> " + RStrings[i]);
            RstringArrayList.add(RStrings[i]);
            if (Busname.equals(cursor.getString(4))) {
                RstringArrayList2.add(RstringArrayList.get(i));
                RstringArrayList3.add(RStrings2[i]);
                RstringArrayList4.add(RStrings3[i]);
                Busname = endString;
                if (Busname.equals(cursor.getString(4))) {
                    Busname = "x";
                }
            }
            Log.d(tag, "ID[[" + RstringArrayList3 + "]] ==>" + "Direction ==>" + RstringArrayList4 + "Bus==>" + RstringArrayList2 );
            cursor.moveToNext();
        } //for
        cursor.close();
        try {
            String GBus = strNumBus;
            String tag2 = "GetLatLug";
            Log.d(tag2, "strNumBus ==> " + GBus);
            int IDStrat = Integer.parseInt(RstringArrayList3.get(0));
            int IDEnd = Integer.parseInt(RstringArrayList3.get(1));
            Log.d(tag2, "ID[[" + IDStrat + " - " + IDEnd + "]]");
            SQLiteDatabase sqLiteDatabase2 = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                    MODE_PRIVATE, null);
            Cursor cursor2 = sqLiteDatabase2.rawQuery("SELECT * FROM busrouteTABLE WHERE bus = " + GBus, null);
            cursor2.moveToFirst();
            String[] Lat = new String[cursor2.getCount()];
            String[] Lug = new String[cursor2.getCount()];
            String[] Name = new String[cursor2.getCount()];
            test = (IDEnd-IDStrat)+1;
            for (int i = 0; i <= Lat.length; i++) {
                if (RstringArrayList3.get(0).equals(cursor2.getString(0))) {
                    int x =i;
                    for(int c =IDStrat;c<=IDEnd;c++){
                        x=x+1;
                        Log.d(tag, "Bus ==>" + x);
                        Lat[x] = cursor2.getString(5);
                        Lug[x] = cursor2.getString(6);
                        Name[x] = cursor2.getString(4);
                        LstringArrayList.add(Lat[x]);
                        LstringArrayList2.add(Lug[x]);
                        LstringArrayList3.add(Name[x]);
                        cursor2.moveToNext();
                        //Log.d(tag, "ป้ายทั้งหมดที่ต้องถาม ==>"+test+" ==>" + "Lat ==>" + LstringArrayList + "Lug ==>" + LstringArrayList2);
                    }
                }
                Log.d(tag2, "ป้ายทั้งหมดที่ต้องถาม ==>"+test+" ==>" + "Lat ==>" + LstringArrayList + "Lug ==>" + LstringArrayList2);
                cursor2.moveToNext();
            } //for
            cursor2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}   // Main Class