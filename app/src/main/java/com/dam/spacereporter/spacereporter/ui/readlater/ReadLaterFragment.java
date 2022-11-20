package com.dam.spacereporter.spacereporter.ui.readlater;

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
import com.dam.spacereporter.spacereporter.database.UpdateUIReadLaterFromDBThread;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ReadLaterFragment extends Fragment {

    // Number of articles on each requests and articles to skip
    private final int limit = 20;
    private int offset = 0;

    private int count = 0;

    private NavigationView navigationView;
    private ArrayList<Article> readLaterArrayList;
    private RecyclerView readLaterRV;
    private ProgressBar readLaterProgressBar;

    private ArticlesDatabaseHelper dbHelper;

    // Required empty public constructor
    public ReadLaterFragment() {
    }

    @Override
    public void onStart() {
        navigationView.setCheckedItem(R.id.nav_readLater);
        super.onStart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView;
        dbHelper = new ArticlesDatabaseHelper(requireContext());

        /*---------- UI ELEMENTS ----------*/

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_read_later, container, false);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NestedScrollView readLaterNestedScrollView = view.findViewById(R.id.readLater_nestedScrollView);
        readLaterRV = view.findViewById(R.id.readLater_rv);
        readLaterProgressBar = view.findViewById(R.id.readLater_pb);

        readLaterArrayList = new ArrayList<>();
        new Thread(new UpdateUIReadLaterFromDBThread(this, dbHelper, limit, offset)).start();

        readLaterRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        readLaterNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                offset += limit;
                count++;
                readLaterProgressBar.setVisibility(View.VISIBLE);
                if (count < 20)
                    new Thread(new UpdateUIReadLaterFromDBThread(this, dbHelper, limit, offset)).start();
            }
        });
    }

    public void updateList(List<Article> readLaterArticles) {
        readLaterRV.setVisibility(View.VISIBLE);
        // Concat articles to the list
        readLaterArrayList.addAll(readLaterArticles);
        readLaterRV.setAdapter(new ReadLaterRVAdapter(requireContext(), readLaterArrayList, dbHelper));
        readLaterProgressBar.setVisibility(View.INVISIBLE);
    }
}