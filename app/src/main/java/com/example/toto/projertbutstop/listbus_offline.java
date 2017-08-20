package com.example.toto.projertbutstop;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbus_offline);

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

        for (int i=0;i<myTrueNumberBusStartStringArrayListinTown.size();i++) {
            for (int h =0 ;h<myTrueNumberBusEndStringArrayListinTown.size();h++) {
                if (myTrueNumberBusStartStringArrayListinTown.get(i).equals(myTrueNumberBusEndStringArrayListinTown.get(h))) {
                    BusPast.add(myTrueNumberBusStartStringArrayListinTown.get(i));
                }
            }

        }
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

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE direction = 'เข้าเมือง' AND bus = " + numberBusString, null);
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

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busrouteTABLE WHERE direction = 'เข้าเมือง' AND bus = " + numberBusString, null);
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

}   // Main Class