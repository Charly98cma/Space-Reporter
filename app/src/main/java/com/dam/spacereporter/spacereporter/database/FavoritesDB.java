package com.dam.spacereporter.spacereporter.database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.dam.spacereporter.spacereporter.data.models.Article;

import java.util.LinkedList;
import java.util.List;

public class FavoritesDB {

    // TODO Implement
    public static boolean isArticleSaved(FavoritesDatabaseHelper dbHelper, int articleId) {
        return false;
    }

    @SuppressLint("DefaultLocale")
    public static void save(FavoritesDatabaseHelper dbHelper, Article article) {
        SQLiteDatabase conn = dbHelper.getWritableDatabase();
        conn.execSQL(String.format(
                "INSERT INTO %s VALUES (%d, '%s', '%s', '%s', '%s', '%s')",
                FavoritesDatabaseHelper.TABLE_NAME, article.getId(), article.getTitle(), article.getUrl(), article.getImageUrl(), article.getNewsSite(), article.getSummary()
        ));
    }

    @NonNull
    public static List<Article> get(FavoritesDatabaseHelper dbHelper, int limit, int offset) {
        List<Article> result = new LinkedList<>();
        // Run SQL query and point cursor to result
        Cursor c = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + FavoritesDatabaseHelper.TABLE_NAME +
                        " ORDER BY 'id' DESC LIMIT " + limit + " OFFSET " + offset + ";",
                null);
        c.moveToFirst();
        // Serialize all articles
        while(!c.isAfterLast()) {
            result.add(new Article(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5)));
            c.moveToNext();
        }
        c.close();
        return result;
    }
}
