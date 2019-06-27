package com.example.mynovelties;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONSTANT = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_DATE = "date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONSTANT + "(" + KEY_ID +
                " integer primary key," + KEY_TITLE +
                " text," + KEY_DESCRIPTION +
                " text," + KEY_URL +
                " text," + KEY_URLTOIMAGE +
                " text," + KEY_DATE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONSTANT);

        onCreate(db);
    }
}
