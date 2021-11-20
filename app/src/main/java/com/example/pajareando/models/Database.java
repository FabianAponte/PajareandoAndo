package com.example.pajareando.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pajareando.db";
    private static final String TABLE_BIRDS = "CREATE TABLE birds (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name TEXT NOT NULL," +
            " type TEXT," +
            " size TEXT," +
            " color1 TEXT," +
            " color2 TEXT," +
            " color3 TEXT," +
            " color4 TEXT," +
            " moreColors TEXT," +
            " review TEXT," +
            " imagePath TEXT)";

    private static final String TABLE_USERS = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name TEXT NOT NULL," +
            " email TEXT NOT NULL, " +
            " password TEXT NOT NULL, " +
            " role TEXT);";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_BIRDS);
        sqLiteDatabase.execSQL(TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TABLE_USERS);
    }
}
