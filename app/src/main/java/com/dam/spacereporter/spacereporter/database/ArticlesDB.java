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

    /**
     * Saves the given article in the ArticlesDB, in the "favorites" table
     *
     * @param dbHelper Articles database helper
     * @param article  Article to be stored in the DB
     */
    public static void saveArticleToFavorites(@NonNull ArticlesDatabaseHelper dbHelper, @NonNull Article article) {
        saveArticle(dbHelper, article, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    /**
     * Saves the given article in the ArticlesDB, in the "read later" table
     *
     * @param dbHelper Articles database helper
     * @param article  Article to be stored in the DB
     */
    public static void saveArticleToReadLater(@NonNull ArticlesDatabaseHelper dbHelper, @NonNull Article article) {
        saveArticle(dbHelper, article, ArticlesDatabaseHelper.TABLE_NAME_RL);
    }

    /**
     * Saves the given article in the given table
     *
     * @param dbHelper Articles database helper
     * @param article  Article to be stored in the DB
     * @param table    Table in which the article will be stored
     */
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

    /**
     * Returns a list of, at most 'limit' articles (skipping the first 'offset' articles) from the "favorites" table in the ArticlesDB
     *
     * @param dbHelper Articles database helper
     * @param limit    Number of articles to return
     * @param offset   Number of articles to skip from the DB
     * @return List of articles
     */
    @NonNull
    public static List<Article> getFavoritesArticles(@NonNull ArticlesDatabaseHelper dbHelper, int limit, int offset) {
        return get(dbHelper, limit, offset, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    /**
     * Returns a list of, at most 'limit' articles (skipping the first 'offset' articles) from the "read later" table in the ArticlesDB
     *
     * @param dbHelper Articles database helper
     * @param limit    Number of articles to return
     * @param offset   Number of articles to skip from the DB
     * @return List of articles
     */
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
            while (!c.isAfterLast()) {
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

    /**
     * Returns whether the article is in the "favorites" table in the ArticlesDB
     *
     * @param dbHelper  Articles database helper
     * @param articleId Id of the article to check
     * @return Whether the article is in the DB
     */
    public static boolean isArticleInFavorites(ArticlesDatabaseHelper dbHelper, int articleId) {
        return isArticleIn(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    /**
     * Returns whether the article is in the "read later" table in the ArticlesDB
     *
     * @param dbHelper  Articles database helper
     * @param articleId Id of the article to check
     * @return Whether the article is in the DB
     */
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

    /**
     * Deletes the given article from the "favorites" table in the ArticlesDB
     *
     * @param dbHelper  Articles database helper
     * @param articleId Id of the article to delete
     */
    public static void deleteArticleFromFavorites(ArticlesDatabaseHelper dbHelper, int articleId) {
        deleteArticle(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_FAV);
    }

    /**
     * Deletes the given article from the "read later" table in the ArticlesDB
     *
     * @param dbHelper  Articles database helper
     * @param articleId Id of the article to delete
     */
    public static void deleteArticleFromReadLater(ArticlesDatabaseHelper dbHelper, int articleId) {
        deleteArticle(dbHelper, articleId, ArticlesDatabaseHelper.TABLE_NAME_RL);
    }

    @SuppressLint("DefaultLocale")
    private static void deleteArticle(@NonNull ArticlesDatabaseHelper dbHelper, int articleId, String table) {
        dbHelper.getWritableDatabase().delete(
                table,
                "id = ?",
                new String[]{Integer.toString(articleId)});
    }
}
