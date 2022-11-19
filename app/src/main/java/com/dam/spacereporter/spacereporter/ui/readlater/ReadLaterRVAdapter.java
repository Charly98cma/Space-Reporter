package com.dam.spacereporter.spacereporter.ui.readlater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.data.models.Article;
import com.dam.spacereporter.spacereporter.database.ArticlesDB;
import com.dam.spacereporter.spacereporter.database.ArticlesDatabaseHelper;
import com.dam.spacereporter.spacereporter.utils.NetworkConnection;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReadLaterRVAdapter extends RecyclerView.Adapter<ReadLaterRVAdapter.ReadLaterViewHolder> {

    private final Context context;
    private final ArrayList<Article> readLaterArrayList;
    private final ArticlesDatabaseHelper dbHelper;

    public ReadLaterRVAdapter(Context context, ArrayList<Article> readLaterArrayList, ArticlesDatabaseHelper dbHelper) {
        this.context = context;
        this.readLaterArrayList = readLaterArrayList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ReadLaterRVAdapter.ReadLaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReadLaterRVAdapter.ReadLaterViewHolder(LayoutInflater.from(context).inflate(R.layout.news_rv_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReadLaterRVAdapter.ReadLaterViewHolder holder, int position) {
        Article article = readLaterArrayList.get(position);

        holder.articleTitle.setText(article.getTitle());
        if (NetworkConnection.isNetworkConnected(context))
            Picasso.get().load(article.getImageUrl()).into(holder.articlePicture);

        holder.articleCardView.setOnClickListener(v -> {

            // Setup PopUp window
            @SuppressLint("InflateParams")
            View popupView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.popupwindow_article, null);
            final PopupWindow popupWindow = new PopupWindow(
                    popupView, // View
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Height
                    true // Focusable
            );

            // Set PopUP UI elements
            TextView popupTitle = popupView.findViewById(R.id.popup_txt_title);
            TextView popupSummary = popupView.findViewById(R.id.popup_txt_summary);
            ImageButton popupBtnFav = popupView.findViewById(R.id.popup_btn_fav);
            ImageButton popupBtnReadLater = popupView.findViewById(R.id.popup_btn_readLater);
            ImageButton popupBtnWebView = popupView.findViewById(R.id.popup_btn_openWeb);

            popupTitle.setText(article.getTitle());
            popupSummary.setText(article.getSummary());

            // Change icons based on the article being (or not) in Fav/RL database
            if (ArticlesDB.isArticleInFavorites(dbHelper, article.getId()))
                popupBtnFav.setImageResource(R.drawable.favorite_icon_filled);
            if (ArticlesDB.isArticleInReadLater(dbHelper, article.getId()))
                popupBtnReadLater.setImageResource(R.drawable.readlater_icon_filled);

            // Listeners
            popupBtnFav.setOnClickListener(v_fav -> {
                if (ArticlesDB.isArticleInFavorites(dbHelper, article.getId())) {
                    Toast.makeText(context, "Article removed from favorites", Toast.LENGTH_SHORT).show();
                    ArticlesDB.deleteArticleFromFavorites(dbHelper, article.getId());
                    popupBtnFav.setImageResource(R.drawable.favorite_icon_outline);
                }else {
                    Toast.makeText(context, "Article added to favorites", Toast.LENGTH_SHORT).show();
                    ArticlesDB.saveArticleToFavorites(dbHelper, article);
                    popupBtnFav.setImageResource(R.drawable.favorite_icon_filled);
                }
            });
            popupBtnReadLater.setOnClickListener(v_fav -> {
                if (ArticlesDB.isArticleInReadLater(dbHelper, article.getId())) {
                    Toast.makeText(context, "Article removed from read later", Toast.LENGTH_SHORT).show();
                    ArticlesDB.deleteArticleFromReadLater(dbHelper, article.getId());
                    popupBtnReadLater.setImageResource(R.drawable.readlater_icon_outline);
                }else {
                    Toast.makeText(context, "Article added to reaq later", Toast.LENGTH_SHORT).show();
                    ArticlesDB.saveArticleToReadLater(dbHelper, article);
                    popupBtnReadLater.setImageResource(R.drawable.readlater_icon_filled);
                }
            });
            popupBtnWebView.setOnClickListener(f_web -> {
                Toast.makeText(context, R.string.news_toast_webView, Toast.LENGTH_SHORT).show();
                Intent intentWebBrowser = new Intent(Intent.ACTION_VIEW);
                intentWebBrowser.setData(article.getUrl());
                context.startActivity(intentWebBrowser);
            });

            // Show PopUp on screen
            popupWindow.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);
        });

    }

    @Override
    public int getItemCount() {
        return readLaterArrayList.size();
    }

    public static class ReadLaterViewHolder extends RecyclerView.ViewHolder {

        final CardView articleCardView;
        final ShapeableImageView articlePicture;
        final TextView articleTitle;

        public ReadLaterViewHolder(@NonNull View itemView) {
            super(itemView);
            // Read the UI elements
            articleCardView = itemView.findViewById(R.id.news_card_article);
            articleTitle = itemView.findViewById(R.id.news_txt_articleTitle);
            articlePicture = itemView.findViewById(R.id.news_img_articlePicture);
        }
    }
}
