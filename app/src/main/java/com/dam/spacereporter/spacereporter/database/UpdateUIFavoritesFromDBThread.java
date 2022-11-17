package com.dam.spacereporter.spacereporter.database;

import android.app.Activity;

import com.dam.spacereporter.spacereporter.data.models.Article;
import com.dam.spacereporter.spacereporter.ui.favorites.FavoritesFragment;

import java.util.List;

public class UpdateUIFavoritesFromDBThread implements Runnable {

    private final FavoritesFragment favoritesFragment;
    private final FavoritesDatabaseHelper dbHelper;
    private final int limit;
    private final int offset;

    public UpdateUIFavoritesFromDBThread(FavoritesFragment favoritesFragment, FavoritesDatabaseHelper dbHelper, int limit, int offset) {
        this.favoritesFragment = favoritesFragment;
        this.dbHelper = dbHelper;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public void run() {
        List<Article> allFavArticles = FavoritesDB.get(dbHelper, limit, offset);
        favoritesFragment.getActivity().runOnUiThread((Runnable) () -> favoritesFragment.updateList(allFavArticles));
    }
}
