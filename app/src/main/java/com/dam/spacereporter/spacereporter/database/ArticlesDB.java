package com.dam.spacereporter.spacereporter.database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.dam.spacereporter.spacereporter.data.models.Article;

import java.util.LinkedList;
import java.util.List;

public class ArticlesDB {

    /*
     * STORE methods
     */

    public static void saveArticleToFavorites(@NonNull ArticlesDatabaseHelper dbHelper, @NonNull Article article) {
        saveArticle(dbHelper, article, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    public static void saveArticleToReadLater(@NonNull ArticlesDatabaseHelper dbHelper, @NonNull Article article) {
        saveArticle(dbHelper, article, ArticlesDatabaseHelper.TABLE_NAME_RL);
    }

    @SuppressLint("DefaultLocale")
    private static void saveArticle(@NonNull ArticlesDatabaseHelper dbHelper, @NonNull Article article, String table) {
        SQLiteDatabase conn = dbHelper.getWritableDatabase();
        conn.execSQL(String.format(
                "INSERT INTO %s VALUES (%d, '%s', '%s', '%s', '%s', '%s')",
                table, article.getId(), article.getTitle(), article.getUrl(), article.getImageUrl(), article.getNewsSite(), article.getSummary()
        ));
    }

    /*
     * SELECT functions
     */

    @NonNull
    public static List<Article> getFavoritesArticles(@NonNull ArticlesDatabaseHelper dbHelper, int limit, int offset) {
        return get(dbHelper, limit, offset, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    @NonNull
    public static List<Article> getReadLaterArticles(@NonNull ArticlesDatabaseHelper dbHelper, int limit, int offset) {
        return get(dbHelper, limit, offset, ArticlesDatabaseHelper.TABLE_NAME_RL);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    private static List<Article> get(@NonNull ArticlesDatabaseHelper dbHelper, int limit, int offset, String table) {
        List<Article> result = new LinkedList<>();
        try (Cursor c = dbHelper.getReadableDatabase().rawQuery(
                String.format("SELECT * FROM %s ORDER BY id DESC LIMIT %d OFFSET %d ;", table, limit, offset),
                null)
        ) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                result.add(new Article(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5)
                ));
                c.moveToNext();
            }
            return result;
        }
    }

    /*
     * ASK functions
     */

    public static boolean isArticleInFavorites(ArticlesDatabaseHelper dbHelper, int articleId) {
        return isArticleIn(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    public static boolean isArticleInReadLater(ArticlesDatabaseHelper dbHelper, int articleId) {
        return isArticleIn(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_RL);
    }

    @SuppressLint("DefaultLocale")
    private static boolean isArticleIn(@NonNull ArticlesDatabaseHelper dbHelper, int articleId, String table) {
        try (Cursor c = dbHelper.getReadableDatabase().rawQuery(
                String.format("SELECT id FROM %s WHERE id = %d;", table, articleId),
                null)
        ) {
            return c.getCount() > 0;
        }
    }

    /*
     * DELETE methods
     */

    public static void deleteArticleFromFavorites(ArticlesDatabaseHelper dbHelper, int articleId) {
        deleteArticle(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    public static void deleteArticleFromReadLater(ArticlesDatabaseHelper dbHelper, int articleId) {
        deleteArticle(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_RL);
    }

    @SuppressLint("DefaultLocale")
    private static void deleteArticle(@NonNull ArticlesDatabaseHelper dbHelper, int articleId, String table) {
        dbHelper.getWritableDatabase().delete(
                table,
                "id = ?",
                new String[] {Integer.toString(articleId)});
    }
}
