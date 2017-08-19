package com.example.toto.projertbutstop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ByDiamon on 6/6/2560.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MyOpenHelper.class.getSimpleName();
    //สร้างฐานข้อมูล
    public static final String DATABASE_NAME = "Busstop.db";
    //เวอร์ชั่นฐานข้อมูล
    private static final int DATABASE_VERSION = 1;

    //สร้างเทเบิล
    private static final String BUSSTOP_TABLE = "create table busstopTABLE (_id integer primary key, " +
            "Lat text," +
            "Lng text," +
            "Namebusstop text );";

    private static final String BUS_TABLE = "create table busTABLE (id_bus integer primary key, " +
            "bus text ," +
            "bus_details text);";

    private static final String BUSROUTE_TABLE = "create table busrouteTABLE (id_busroute integer primary key, " +
            "direction text , " +
            "bus text, " +
            "bus_details text ," +
            "Namebusstop text, " +
            "Lat text, " +
            "Lng text);";

    public MyOpenHelper(Context context){
        //เรียกไปยังคอนสตรัคเตอร์ของ SQLiteOpenHelper โดยระบุชื่อและเวอร์ชั่นฐานข้อมูล
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"Creating Table");
        //สร่้างเทเบิลในฐานข้อมูล
        db.execSQL(BUSSTOP_TABLE);
        db.execSQL(BUS_TABLE);
        db.execSQL(BUSROUTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //อัพเกรดฐานข้อมูล
        String msg = "Upgrading Database From Version" + oldVersion + " to " + newVersion;
        Log.d(TAG,msg);
    }
} //main class