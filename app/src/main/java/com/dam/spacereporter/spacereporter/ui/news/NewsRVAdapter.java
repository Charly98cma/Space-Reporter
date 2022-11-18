package com.dam.spacereporter.spacereporter.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// TODO Cache newsArrayList (do not delete all elements when exit fragment)
public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder> {

    private final Context context;
    private final ArrayList<Article> newsArrayList;
    private final ArticlesDatabaseHelper dbHelper;

    public NewsRVAdapter(Context context, ArrayList<Article> newsArrayList, ArticlesDatabaseHelper dbHelper) {
        this.context = context;
        this.newsArrayList = newsArrayList;
        this.dbHelper = dbHelper;
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

        // Set title and picture
        Picasso.get().load(article.getImageUrl()).into(holder.articlePicture);
        holder.articleTitle.setText(article.getTitle());

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
                popupBtnFav.setImageResource(R.drawable.ic_baseline_favorite_24);
            if (ArticlesDB.isArticleInReadLater(dbHelper, article.getId()))
                popupBtnReadLater.setImageResource(R.drawable.ic_baseline_read_later_24);

            // Listeners
            popupBtnFav.setOnClickListener(v_fav -> {
                if (ArticlesDB.isArticleInFavorites(dbHelper, article.getId())) {
                    Toast.makeText(context, "Article removed from favorites", Toast.LENGTH_SHORT).show();
                    ArticlesDB.deleteArticleFromFavorites(dbHelper, article.getId());
                    popupBtnFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }else {
                    Toast.makeText(context, "Article added to favorites", Toast.LENGTH_SHORT).show();
                    ArticlesDB.saveArticleToFavorites(dbHelper, article);
                    popupBtnFav.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
            });
            popupBtnReadLater.setOnClickListener(v_rl -> {
                if (ArticlesDB.isArticleInReadLater(dbHelper, article.getId())) {
                    Toast.makeText(context, "Article removed from read later", Toast.LENGTH_SHORT).show();
                    ArticlesDB.deleteArticleFromReadLater(dbHelper, article.getId());
                    popupBtnReadLater.setImageResource(R.drawable.ic_outline_watch_later_24);
                }else {
                    Toast.makeText(context, "Article added to reaq later", Toast.LENGTH_SHORT).show();
                    ArticlesDB.saveArticleToReadLater(dbHelper, article);
                    popupBtnReadLater.setImageResource(R.drawable.ic_baseline_read_later_24);
                }
            });
            popupBtnWebView.setOnClickListener(v_web -> {
                Toast.makeText(context, R.string.news_toast_webView, Toast.LENGTH_SHORT).show();
                Intent intentWebBrowser = new Intent(Intent.ACTION_VIEW);
                intentWebBrowser.setData(Uri.parse(newsArrayList.get(position).getUrl()));
                context.startActivity(intentWebBrowser);
            });

            // Show PopUp on screen
            popupWindow.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of articles loaded
        return newsArrayList.size();
    }

    /*
     * ViewHolder class
     */

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        final CardView articleCardView;
        final ShapeableImageView articlePicture;
        final TextView articleTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Read the UI elements
            articleCardView = itemView.findViewById(R.id.news_card_article);
            articleTitle = itemView.findViewById(R.id.news_txt_articleTitle);
            articlePicture = itemView.findViewById(R.id.news_img_articlePicture);
        }
    }
}
