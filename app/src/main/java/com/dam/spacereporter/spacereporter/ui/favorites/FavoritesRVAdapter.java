package com.dam.spacereporter.spacereporter.ui.favorites;

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
import com.dam.spacereporter.spacereporter.utils.NetworkConnection;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FavoritesRVAdapter extends RecyclerView.Adapter<FavoritesRVAdapter.FavoritesViewHolder> {

    private final Context context;
    private final ArrayList<Article> favArrayList;

    public FavoritesRVAdapter(Context context, ArrayList<Article> favArrayList) {
        this.context = context;
        this.favArrayList = favArrayList;
    }

    @NonNull
    @Override
    public FavoritesRVAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate view of the list
        return new FavoritesViewHolder(LayoutInflater.from(context).inflate(R.layout.news_rv_article, parent, false));
    }

    // FIXME Should be redone to solve photos loading and some other similar issues
    @Override
    public void onBindViewHolder(@NonNull FavoritesRVAdapter.FavoritesViewHolder holder, int position) {
        Article article = favArrayList.get(position);

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

            popupBtnFav.setImageResource(R.drawable.ic_baseline_favorite_24);

            popupTitle.setText(favArrayList.get(position).getTitle());
            popupSummary.setText(favArrayList.get(position).getSummary());

            // Listeners
            popupBtnFav.setOnClickListener(v_fav -> {
                // TODO Implement FAV logic
                Toast.makeText(context, "Favorites clickes", Toast.LENGTH_SHORT).show();
            });
            popupBtnReadLater.setOnClickListener(v_fav -> {
                // TODO Implement READLATER Logic
                Toast.makeText(context, "Read later clicked", Toast.LENGTH_SHORT).show();
            });
            popupBtnWebView.setOnClickListener(f_web -> {
                Toast.makeText(context, R.string.news_toast_webView, Toast.LENGTH_SHORT).show();
                Intent intentWebBrowser = new Intent(Intent.ACTION_VIEW);
                intentWebBrowser.setData(Uri.parse(favArrayList.get(position).getUrl()));
                context.startActivity(intentWebBrowser);
            });

            // Show PopUp on screen
            popupWindow.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of articles loaded
        return favArrayList.size();
    }

    /*
     * ViewHolder class
     */

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        final CardView articleCardView;
        final ShapeableImageView articlePicture;
        final TextView articleTitle;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            // Read the UI elements
            articleCardView = itemView.findViewById(R.id.news_card_article);
            articleTitle = itemView.findViewById(R.id.news_txt_articleTitle);
            articlePicture = itemView.findViewById(R.id.news_img_articlePicture);
        }
    }
}
