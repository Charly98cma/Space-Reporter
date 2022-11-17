package com.dam.spacereporter.spacereporter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// FIXME Should be redone to use a Factory (proper implementation)
public class FavoritesDatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "db_articles";
    public static final String TABLE_NAME = "favorites";

    public FavoritesDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
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
