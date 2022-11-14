package com.dam.spacereporter.spacereporter.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dam.spacereporter.R;
import com.google.android.material.navigation.NavigationView;

public class ReadLaterFragment extends Fragment {

    private NavigationView navigationView;

    // Required empty public constructor
    public ReadLaterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        navigationView.setCheckedItem(R.id.nav_readLater);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView;

        /*---------- UI ELEMENTS ----------*/

        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_read_later, container, false);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        /*---------- UI ELEMENTS LISTENERS ----------*/
        // TODO Implement UI and features

        return fragmentView;
    }
}