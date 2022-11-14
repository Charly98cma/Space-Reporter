package com.dam.spacereporter.spacereporter.ui.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.data.models.Article;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// TODO URGENT Add scroll to load more news
public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder> {

    private final Context context;
    private final ArrayList<Article> newsArrayList;

    public NewsRVAdapter(Context context, ArrayList<Article> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public NewsRVAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate view of the list
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_rv_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.NewsViewHolder holder, int position) {
        Article article = newsArrayList.get(position);
        // Load picture from URL
        Picasso.get().load(article.getImageUrl()).into(holder.articlePicture);
        // Load title of the article
        holder.articleTitle.setText(article.getTitle());
        // Apply listener to load the WebView to the entire card
        holder.articleCardView.setOnClickListener(v -> {
            Toast.makeText(context, R.string.news_toast_webView, Toast.LENGTH_SHORT).show();
            // Pass URL and show WebView activity
            Intent intentToWebView = new Intent(context, NewsWebViewActivity.class);
            intentToWebView.putExtra(context.getString(R.string.news_intent_urlKey), article.getUrl());
            context.startActivity(intentToWebView);
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of articles loaded
        return newsArrayList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        final CardView articleCardView;
        final ShapeableImageView articlePicture;
        final TextView articleTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Read the UI elements
            articleCardView = itemView.findViewById(R.id.news_card_article);
            articlePicture = itemView.findViewById(R.id.news_img_articlePicture);
            articleTitle = itemView.findViewById(R.id.news_txt_articleTitle);
        }
    }
}
