package com.dam.spacereporter.spacereporter.ui.news;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.data.models.Article;
import com.dam.spacereporter.spacereporter.database.ArticlesDatabaseHelper;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    // Number of articles on each requests and articles to skip
    private final int limit = 20;
    private int skip = 0;

    private int count = 0;

    private NavigationView navigationView;
    private ArrayList<Article> newsArrayList;
    private RecyclerView newsRV;
    private ProgressBar newsProgressBar;

    private NotificationManagerCompat notificationManager;
    private SharedPreferences sharedPreferences;

    private ArticlesDatabaseHelper dbHelper;

    // Required empty public constructor
    public NewsFragment() {
    }

    @Override
    public void onStart() {
        navigationView.setCheckedItem(R.id.nav_none);
        super.onStart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView;

        /*---------- INIT ----------*/

        notificationManager = NotificationManagerCompat.from(requireContext());
        sharedPreferences = requireContext().getSharedPreferences(
                getString(R.string.pref), Context.MODE_PRIVATE);
        dbHelper = new ArticlesDatabaseHelper(requireContext());

        /*---------- UI ELEMENTS ----------*/

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_news, container, false);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        /*---------- ADDITIONAL OPERATIONS ----------*/

        createNotificationChannel();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NestedScrollView newsNestedScrollView = view.findViewById(R.id.news_nestedScrollView);
        newsRV = view.findViewById(R.id.news_rv);
        newsProgressBar = view.findViewById(R.id.news_pb);

        // Init array list of articles
        newsArrayList = new ArrayList<>();
        getData();

        newsRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        newsNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                count++;
                newsProgressBar.setVisibility(View.VISIBLE);
                if (count < 20) getData();
            }
        });
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                getString(R.string.notif_channel_newArticle_id),
                getString(R.string.notif_channel_newArticle_name),
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(getString(R.string.notif_channel_newArticle_desc));
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setAllowBubbles(true);
        notificationManager.createNotificationChannel(channel);
    }

    private void notification() {
        // Notify user of new articles available to read
        int newestArticleId = newsArrayList.get(0).getId();
        int oldestArticleId = lastSeenArticle();
        if (oldestArticleId < newestArticleId) {
            // Create notification and notify user
            NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), getString(R.string.notif_channel_newArticle_id))
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(getString(R.string.notif_txt_newArticle_title))
                    .setContentText(getString(R.string.notif_txt_newArticle_body))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            notificationManager.notify(0, builder.build());
            // Save ID pf the newest article shown
            saveLastSeenArticle(newestArticleId);
        }
    }

    private Integer lastSeenArticle() {
        return sharedPreferences.getInt(getString(R.string.pref_article_lastSeen), -1);
    }

    private void saveLastSeenArticle(Integer articleId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.pref_article_lastSeen), articleId);
        editor.apply();
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                String.format(getString(R.string.api_url), limit, skip),
                null,
                response -> {
                    try {
                        // Add the retrieved articles to the skip
                        skip += limit;
                        // Read the new articles
                        newsRV.setVisibility(View.VISIBLE);
                        // FIXME Change to serialize the objects
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject responseObj = response.getJSONObject(i);
                            newsArrayList.add(new Article(
                                    responseObj.getInt("id"),
                                    responseObj.getString("title"),
                                    responseObj.getString("url"),
                                    responseObj.getString("imageUrl"),
                                    responseObj.getString("newsSite"),
                                    responseObj.getString("summary")));
                            newsRV.setAdapter(new NewsRVAdapter(requireContext(), newsArrayList, dbHelper));
                            newsProgressBar.setVisibility(View.INVISIBLE);
                        }

                        notification();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(requireContext(), R.string.news_toast_errorApi, Toast.LENGTH_SHORT).show());
        queue.add(jsonArrayRequest);
    }
}