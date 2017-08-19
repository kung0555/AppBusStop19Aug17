package com.example.toto.projertbutstop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterung on 8/17/2017 AD.
 */

public class MyManage {

    private Context context;

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;


    //For BusStop
    public static final String TABLE_BUSSTOP = "busstopTABLE"; //ชื่อเทเบิล
    public static final String COLUMN_ID_BUSSTOP = "_id";
    public static final String COLUMN_Lat = "Lat";
    public static final String COLUMN_Lng = "Lng";
    public static final String COLUMN_Namebusstop = "Namebusstop";

    //For Bus
    public static final String TABLE_BUS = "busTABLE";
    public static final String COLUMN_ID_BUS = "id_bus";
    public static final String COLUMN_bus = "bus";
    public static final String COLUMN_bus_details = "bus_details";

    //For BusRoute
    public static final String TABLE_BUSROUTE = "busrouteTABLE";
    public static final String COLUMN_ID_BUSROUTE = "id_busroute";
    public static final String COLUMN_direction = "direction";

    public MyManage(Context context) {
        this.context = context;

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   // Constructor

    //Add Value to BusStop
    public long addBusStop(String strCOLUMN_ID_BUSSTOP,
                           String strCOLUMN_Lat,
                           String strCOLUMN_Lng,
                           String strCOLUMN_Namebusstop) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_BUSSTOP, strCOLUMN_ID_BUSSTOP);
        contentValues.put(COLUMN_Lat, strCOLUMN_Lat);
        contentValues.put(COLUMN_Lng, strCOLUMN_Lng);
        contentValues.put(COLUMN_Namebusstop, strCOLUMN_Namebusstop);

        return sqLiteDatabase.insert(TABLE_BUSSTOP, null, contentValues);
    }

    //Add Value to Bus
    public long addBus(String strCOLUMN_ID_BUS,
                       String strCOLUMN_bus,
                       String strCOLUMN_bus_details) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_BUS, strCOLUMN_ID_BUS);
        contentValues.put(COLUMN_bus, strCOLUMN_bus);
        contentValues.put(COLUMN_bus_details, strCOLUMN_bus_details);

        return sqLiteDatabase.insert(TABLE_BUS, null, contentValues);
    }

    //Add Value to BusRoute
    public long addBusRoute(String strCOLUMN_id_busroute,
                            String strCOLUMN_direction,
                            String strCOLUMN_bus,
                            String strCOLUMN_bus_details,
                            String strCOLUMN_Namebusstop,
                            String strCOLUMN_Lat,
                            String strCOLUMN_Lng) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_BUSROUTE, strCOLUMN_id_busroute);
        contentValues.put(COLUMN_direction, strCOLUMN_direction);
        contentValues.put(COLUMN_bus, strCOLUMN_bus);
        contentValues.put(COLUMN_bus_details, strCOLUMN_bus_details);
        contentValues.put(COLUMN_Namebusstop, strCOLUMN_Namebusstop);
        contentValues.put(COLUMN_Lat, strCOLUMN_Lat);
        contentValues.put(COLUMN_Lng, strCOLUMN_Lng);

        return sqLiteDatabase.insert(TABLE_BUSROUTE, null, contentValues);

    }


}   // Main Class
