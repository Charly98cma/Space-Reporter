package com.dam.spacereporter.spacereporter.ui.news;

import android.annotation.SuppressLint;
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
import com.dam.spacereporter.spacereporter.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    // Number of articles on each requests and articles to skip
    private final int limit = 20;
    private int skip = 0;

    // Counter for scrolling
    private int count = 0;

    // UI elements
    private NavigationView navigationView;
    private ArrayList<Article> newsArrayList;
    private RecyclerView newsRV;
    private ProgressBar newsProgressBar;

    // Helpers and services
    private Context context;
    private SharedPreferences sharedPreferences;
    private ArticlesDatabaseHelper dbHelper;
    private NotificationManagerCompat notificationManager;

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

        /*---------- INIT HELPERS AND SERVICES ----------*/

        context = requireContext();
        sharedPreferences = context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE);
        dbHelper = new ArticlesDatabaseHelper(context);
        notificationManager = NotificationManagerCompat.from(context);

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

        // Create UI elements of the fragment
        NestedScrollView newsNestedScrollView = view.findViewById(R.id.news_nestedScrollView);
        newsRV = view.findViewById(R.id.news_rv);
        newsProgressBar = view.findViewById(R.id.news_pb);

        // Init array list of articles
        newsArrayList = new ArrayList<>();
        getData();

        // Setup RecyclerView
        newsRV.setLayoutManager(new LinearLayoutManager(context));
        newsNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                count++;
                newsProgressBar.setVisibility(View.VISIBLE);
                if (count < 20) getData();
            }
        });
    }

    /**
     * Creates the notification channel for the unread articles notifications
     */
    private void createNotificationChannel() {

        // Create channel for notifications about unread articles
        NotificationChannel channel = new NotificationChannel(
                Constants.NOTIF_CHANNELID_NEWARTICLE,
                getString(R.string.notif_channel_newArticle_name),
                NotificationManager.IMPORTANCE_DEFAULT);

        // Setup the channel
        channel.setDescription(getString(R.string.notif_channel_newArticle_desc));
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setAllowBubbles(true);

        // Confirm the creation of the channel
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Creates and shows a notification if there are unread/new articles in the user news feed
     */
    private void notifyIfUnreadArticles() {

        // Read IDs of articles (if no internet connection, no notification)
        int newestArticleId = (newsArrayList.size() > 0) ? newsArrayList.get(0).getId() : -1;
        int oldestArticleId = lastSeenArticle();

        // Notify user if new articles available to read
        if (oldestArticleId < newestArticleId) {
            // Create notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.NOTIF_CHANNELID_NEWARTICLE)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(getString(R.string.notif_txt_newArticle_title))
                    .setContentText(getString(R.string.notif_txt_newArticle_body))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            // Notify the user
            notificationManager.notify(0, builder.build());

            // Save ID of the newest article shown
            saveLastSeenArticle(newestArticleId);
        }
    }

    /**
     * Returns the ID of the last seen/read article
     *
     * @return Id of the article
     */
    private Integer lastSeenArticle() {
        return sharedPreferences.getInt(Constants.PREF_ARTICLE_LASTSEEN, -1);
    }

    /**
     * Save the last seen/read article to notify the user if there are unread articles the next
     * time the news fragment is inflated
     *
     * @param articleId ID of the last seen/read article
     */
    private void saveLastSeenArticle(Integer articleId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.PREF_ARTICLE_LASTSEEN, articleId);
        editor.apply();
    }

    /**
     * Retrieves a batch of articles from the Spaceflight News API, serializes and stores them
     * in an ArrayList to be shown to the user later
     */
    @SuppressLint("DefaultLocale")
    private void getData() {
        // GSON object to serialize the articles
        Gson gson = new Gson();
        // Create queue for the JSON request
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                String.format(Constants.SNAPI_URL, limit, skip),
                null,
                response -> {
                    try {
                        newsRV.setVisibility(View.VISIBLE);
                        // Move skip value for next request
                        skip += limit;
                        // Read the new articles
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject responseObj = response.getJSONObject(i);
                            newsArrayList.add(gson.fromJson(responseObj.toString(), Article.class));
                            newsRV.setAdapter(new NewsRVAdapter(context, newsArrayList, dbHelper));
                            newsProgressBar.setVisibility(View.INVISIBLE);
                        }
                        // Check if unread articles and produce notification
                        notifyIfUnreadArticles();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(context, R.string.news_toast_errorApi, Toast.LENGTH_SHORT).show());
        queue.add(jsonArrayRequest);
    }
}