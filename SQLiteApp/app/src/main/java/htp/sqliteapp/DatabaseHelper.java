package htp.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

/**
 * Created by phuchtgc60244 on 2/25/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {



    public  static  final  String DATABASE_NAME ="Event.db";
    public  static  final  String TABLE_NAME="event_table";
    public  static  final  String COL_1 ="ID";
    public  static  final  String COL_2 ="NAME";
    public  static  final  String COL_3 ="LOCATION";
    public  static  final  String COL_4 ="DATE";
    public  static  final  String COL_5="TIME";
    public  static  final  String COL_6="ORGANIZE";


    public  DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "LOCATION TEXT," +
                "DATE DATETIME DEFAUL CURRENT_DATE," +
                "TIME DATETIME DEFAUL CURRENT_TIME," +
                "ORGANIZE TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String location, String date, String time, String org) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,location);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, time);
        contentValues.put(COL_6, org);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

}
