package com.dam.spacereporter.spacereporter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArticlesDatabaseHelper extends SQLiteOpenHelper {

    // Database version (update whenever the DBs are changed)
    private static final int DB_VERSION = 1;

    // DB name and tables
    private static final String DB_NAME = "db_articles";
    public static final String TABLE_NAME_FAV = "favorites";
    public static final String TABLE_NAME_RL = "read_later";

    public ArticlesDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        // Table with the FAVORITES articles
        db.execSQL("CREATE TABLE " + TABLE_NAME_FAV + "(" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "url TEXT," +
                "imageUrl TEXT," +
                "newsSite TEXT," +
                "summary TEXT" +
                ");");

        // Table with the READ LATER articles
        db.execSQL("CREATE TABLE " + TABLE_NAME_RL + " (" +
                "id INT PRIMARY KEY," +
                "title TEXT," +
                "url TEXT," +
                "imageUrl TEXT," +
                "newsSite TEXT," +
                "summary TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Insert operations for each update
        switch(oldVersion) {
            // example "case 1: ops. to update from version 1 to next; break;"
            default: break;
        }
    }
}
