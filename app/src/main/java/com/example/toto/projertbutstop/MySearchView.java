package com.example.toto.projertbutstop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class MySearchView extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener {

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    android.widget.SearchView editsearch;
    String[] BusStopNameList;
    ArrayList<BusStopName> arraylist = new ArrayList<BusStopName>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        BusStopNameList = getBusStopFromServer();

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < BusStopNameList.length; i++) {
            BusStopName BusStopNames = new BusStopName(BusStopNameList[i]);
            // Binds all strings into an array
            arraylist.add(BusStopNames);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("18AugV3", "You Click at i ==> " + i);
               // String s = (String) adapterView.getItemAtPosition(i);
                Log.d("18AugV3", "Item Click ==> " + (adapterView.getAdapter().getItem(i)).toString());

//                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
//                String busStop = cursor.getString(0);
//                Log.d("18AugV3", "busStop ==> " + busStop);

                BusStopName s = adapter.getItem(i);
                String strBusStop = s.getBusStopString();
                Log.d("18AugV3", "s ==> " + strBusStop);

                Intent intent = new Intent(MySearchView.this, input_offline.class);
                intent.putExtra("BusStop", strBusStop);
                setResult(getIntent().getIntExtra("Key", 1000), intent);
                finish();
            }
        });

        // Locate the EditText in listview_main.xml
        editsearch = (android.widget.SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(MySearchView.this);
    }

    private String[] getBusStopFromServer() {

        String tag = "18AugV2";
        String[] resultStrings = new String[0];

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM busstopTABLE", null);
            cursor.moveToFirst();

            resultStrings = new String[cursor.getCount()];

            for (int i=0;i<resultStrings.length; i+=1) {
                resultStrings[i] = cursor.getString(3);
                Log.d(tag, "result[" + i + "] ==> " + resultStrings[i]);
                cursor.moveToNext();
            }

            return resultStrings;
        } catch (Exception e) {
            Log.d(tag, "e ==> " + e.toString());

        }


        return resultStrings;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        Log.d("18AugV3", "text ==> " + text);
        adapter.filter(text);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}