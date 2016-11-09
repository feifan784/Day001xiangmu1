package com.example.xu.day001xiangmu1.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Bean.Bzjx;
import ImgCache.ListLoadData;

/**
 * Created by xu on 2016/10/31.
 */
public class MyHelper extends SQLiteOpenHelper {


    private SQLiteDatabase db;
    private List<Bzjx>list = new ArrayList<>();
    public MyHelper(Context context) {
        super(context, "bizhi.db", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists bzjx(_id integer primary key autoincrement,img blob)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(Bzjx bz){

        ContentValues values = new ContentValues();
        values.put("img",bz.getImg());
        db.insert("bzjx","img",values);
    }
    public List<Bzjx> query(){

        Cursor cursor=db.query("bzjx",null,null,null,null,null,null,null);

        while ((cursor.moveToNext())){
            Bzjx bzjx = new Bzjx(cursor.getBlob(cursor.getColumnIndex("img")));
            list.add(bzjx);
        }

        return list;
    }


}
