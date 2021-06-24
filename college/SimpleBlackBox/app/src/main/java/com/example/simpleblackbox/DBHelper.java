package com.example.simpleblackbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyVideos.db";
    public static final String VIDEOS_TABLE_NAME = "videos";
    public static final String VIDEOS_COLUMN_ID = "id";
    public static final String VIDEOS_COLUMN_NAME = "name";
    public static final String VIDEOS_COLUMN_START_LOCATION = "start_location";
    public static final String VIDEOS_COLUMN_END_LOCATION = "end_location";
    public static final String VIDEOS_COLUMN_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table videos " +
                "(id integer primary key,name text, start_location text, date text, end_location text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS videos");
        onCreate(sqLiteDatabase);
    }

    public boolean insertVideo(String name, String start_location, String date, String end_location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("start_location", start_location);
        contentValues.put("date", date);
        contentValues.put("end_location", end_location);

        db.insert("videos", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from videos where id = " + id + "", null);
        return res;
    }
    public Cursor getLastData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from videos", null);
        res.moveToLast();
        return res;
    }

    public boolean updateVideo(Integer id, String name, String start_location, String date, String end_location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("start_location", start_location);
        contentValues.put("date", date);
        contentValues.put("end_location", end_location);

        db.update("videos", contentValues,"id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteVideo(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("videos","id = ? ", new String[]{Integer.toString(id)});
    }

    public ArrayList getAllVideos(){
        Log.i("DBhelper", "getAllMovies in");
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from videos", null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            arrayList.add(res.getString(res.getColumnIndex(VIDEOS_COLUMN_ID)) + " " +
                    res.getString(res.getColumnIndex(VIDEOS_COLUMN_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
}
