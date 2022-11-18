package com.dam.spacereporter.spacereporter.database;

import com.dam.spacereporter.spacereporter.data.models.Article;
import com.dam.spacereporter.spacereporter.ui.readlater.ReadLaterFragment;

import java.util.List;
import java.util.Objects;

public class UpdateUIReadLaterFromDBThread implements Runnable {

    private final ReadLaterFragment readLaterFragment;
    private final ArticlesDatabaseHelper dbHelper;
    private final int limit;
    private final int offset;

    public UpdateUIReadLaterFromDBThread(ReadLaterFragment readLaterFragment, ArticlesDatabaseHelper dbHelper, int limit, int offset) {
        this.readLaterFragment = readLaterFragment;
        this.dbHelper = dbHelper;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public void run() {
        List<Article> readLaterArticles = ArticlesDB.getReadLaterArticles(dbHelper, limit, offset);
        Objects.requireNonNull(readLaterFragment.requireActivity()).runOnUiThread((Runnable) () ->
                readLaterFragment.updateList(readLaterArticles));
    }
}
