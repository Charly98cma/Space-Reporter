package com.dam.spacereporter.spacereporter.ui.fragments;

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
import com.dam.spacereporter.spacereporter.ui.news.NewsRVAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private int count = 0;

    private NavigationView navigationView;
    private ArrayList<Article> newsArrayList;

    private RecyclerView newsRV;
    private ProgressBar newsProgressBar;

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

        /*---------- UI ELEMENTS ----------*/

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_news, container, false);
        navigationView = requireActivity().findViewById(R.id.nav_view);

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

        // TODO Replace with a call to retrieve initial data from the API
        initData();

        newsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        newsNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                count++;
                newsProgressBar.setVisibility(View.VISIBLE);
                // TODO Replace with a call to retrive more data from API
                if (count < 20) initData();
            }
        });
    }

    private void initData() {

        String[] titles = {
                "Delayed NASA Venus mission looks for a reprieve",
                "ESA seeks funding for navigation technology programs at ministerial",
                "X-37B space plane completes its sixth mission, lands after nearly 30 months in orbit",
                "SpaceX launches Falcon 9 booster into retirement on Intelsat mission",
                "Falcon 9 B1051 makes final flight on Galaxy-31 & 32 mission",
                "NASA moving ahead with Nov. 16 Artemis 1 launch attempt",
                "NASA Sets New Coverage for Artemis I Moon Mission Launch",
                "NASA says its SLS rocket is good to go for a launch attempt next Wednesday",
                "Starfish books launch for in-orbit satellite docking mission next fall",
                "Space Force orders new weather satellite from Ball Aerospace"
        };

        String[] imgUrl = {
                "https://spacenews.com/wp-content/uploads/2022/11/veritas-2022.jpg",
                "https://spacenews.com/wp-content/uploads/2022/11/galileo-cx.jpg",
                "https://spacenews.com/wp-content/uploads/2022/11/221111-F-XX000-0002-scaled.jpg",
                "https://spaceflightnow.com/wp-content/uploads/2022/11/DSC_8650.jpg",
                "https://www.nasaspaceflight.com/wp-content/uploads/2022/11/FhYGiOYWAAAp-RE-1170x780.jpeg",
                "https://spacenews.com/wp-content/uploads/2022/11/art1-221111.jpg",
                "https://www.nasa.gov/sites/default/files/thumbnails/image/nhq202211110003.jpg?itok=GBXe0GR2",
                "https://cdn.arstechnica.net/wp-content/uploads/2022/09/Artemis-I-LA1-Aug-2022-3042.jpg",
                "https://spacenews.com/wp-content/uploads/2022/11/Starfish-Otter-Pup-scaled.jpg",
                "https://spacenews.com/wp-content/uploads/2021/05/WSF-M-Mission_1018_sm.jpg"
        };

        String[] urls = {
                "https://spacenews.com/delayed-nasa-venus-mission-looks-for-a-reprieve/",
                "https://spacenews.com/esa-seeks-funding-for-navigation-technology-programs-at-ministerial/",
                "https://spacenews.com/x-37b-space-plane-completes-its-sixth-mission-lands-after-nearly-30-months-in-orbit/",
                "https://spaceflightnow.com/2022/11/12/falcon-9-galaxy-31-32-live-coverage/",
                "https://www.nasaspaceflight.com/2022/11/galaxy-31-32/",
                "https://spacenews.com/nasa-moving-ahead-with-nov-16-artemis-1-launch-attempt/",
                "http://www.nasa.gov/press-release/nasa-sets-new-coverage-for-artemis-i-moon-mission-launch",
                "https://arstechnica.com/science/2022/11/nasa-says-its-sls-rocket-is-good-to-go-for-a-launch-attempt-next-wednesday/",
                "https://spacenews.com/starfish-books-launch-for-in-orbit-satellite-docking-mission-next-fall/",
                "https://spacenews.com/space-force-orders-new-weather-satellite-from-ball-aerospace/"
        };

        for (int i = 0; i < titles.length; i++) {
            newsArrayList.add(new Article(i, titles[i], urls[i], imgUrl[i], "", ""));
            newsRV.setAdapter(new NewsRVAdapter(getContext(), newsArrayList));
        }
    }
}