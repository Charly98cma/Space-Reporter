package com.dam.spacereporter.spacereporter.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.news.NewsFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private NavigationView navigationView;

    // Required empty public constructor
    public HomeFragment() {
    }

    @Override
    public void onStart() {

        // NOTE: Passing through HOME resets the backstack
        //       Pressing "<" from HOME always closes the app
        requireActivity().getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE );
        navigationView.setCheckedItem(R.id.nav_home);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView;
        final ImageButton home_btn_news, home_btn_launches;

        /*---------- UI ELEMENTS ----------*/

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        home_btn_launches = fragmentView.findViewById(R.id.home_btn_launches);
        home_btn_news = fragmentView.findViewById(R.id.home_btn_news);

        /*---------- UI ELEMENTS LISTENERS ----------*/

        home_btn_news.setOnClickListener(this);
        home_btn_launches.setOnClickListener(this);

        return fragmentView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(@NonNull View v) {

        switch (v.getId()) {
            case R.id.home_btn_news:
                replaceFragment(new NewsFragment());
                break;
            case R.id.home_btn_launches:
                replaceFragment(new LaunchesFragment());
                break;
        }
    }

    /*
     * AUX FUNCTIONS
     */

    private void replaceFragment(Fragment fragment) {

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}