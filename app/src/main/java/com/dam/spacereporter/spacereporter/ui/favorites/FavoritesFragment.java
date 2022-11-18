package com.dam.spacereporter.spacereporter.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.data.models.Article;
import com.dam.spacereporter.spacereporter.database.ArticlesDatabaseHelper;
import com.dam.spacereporter.spacereporter.database.UpdateUIFavoritesFromDBThread;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    // Number of articles on each requests and articles to skip
    private final int limit = 20;
    private int offset = 0;

    private int count = 0;

    private NavigationView navigationView;
    private ArrayList<Article> favArrayList;
    private RecyclerView favRV;
    private ProgressBar favProgressBar;

    private ArticlesDatabaseHelper dbHelper;

    // Required empty public constructor
    public FavoritesFragment() {
    }

    @Override
    public void onStart() {
        navigationView.setCheckedItem(R.id.nav_fav);
        super.onStart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView;
        dbHelper = new ArticlesDatabaseHelper(requireContext());

        /*---------- UI ELEMENTS ----------*/

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_favorites, container, false);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NestedScrollView favNestedScrollView = view.findViewById(R.id.fav_nestedScrollView);
        favRV = view.findViewById(R.id.fav_rv);
        favProgressBar = view.findViewById(R.id.fav_pb);

        favArrayList = new ArrayList<>();
        new Thread(new UpdateUIFavoritesFromDBThread(this, dbHelper, limit, offset)).start();

        favRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        favNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                offset += limit;
                count++;
                favProgressBar.setVisibility(View.VISIBLE);
                if (count < 20) new Thread(new UpdateUIFavoritesFromDBThread(this, dbHelper, limit, offset)).start();
            }
        });
    }

    public void updateList(List<Article> favArticles) {
        favRV.setVisibility(View.VISIBLE);
        // Concat articles to the list
        favArrayList.addAll(favArticles);
        favRV.setAdapter(new FavoritesRVAdapter(requireContext(), favArrayList, dbHelper));
        favProgressBar.setVisibility(View.INVISIBLE);
    }
}