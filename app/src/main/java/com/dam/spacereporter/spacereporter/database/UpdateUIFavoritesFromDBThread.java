package com.dam.spacereporter.spacereporter.database;

import com.dam.spacereporter.spacereporter.data.models.Article;
import com.dam.spacereporter.spacereporter.ui.favorites.FavoritesFragment;

import java.util.List;
import java.util.Objects;

public class UpdateUIFavoritesFromDBThread implements Runnable {

    private final FavoritesFragment favoritesFragment;
    private final ArticlesDatabaseHelper dbHelper;
    private final int limit;
    private final int offset;

    public UpdateUIFavoritesFromDBThread(FavoritesFragment favoritesFragment, ArticlesDatabaseHelper dbHelper, int limit, int offset) {
        this.favoritesFragment = favoritesFragment;
        this.dbHelper = dbHelper;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public void run() {
        List<Article> readLaterArticles = ArticlesDB.getFavoritesArticles(dbHelper, limit, offset);
        Objects.requireNonNull(favoritesFragment.requireActivity()).runOnUiThread((Runnable) () ->
                favoritesFragment.updateList(readLaterArticles));
    }
}
