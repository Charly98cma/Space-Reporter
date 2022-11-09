package com.dam.spacereporter.spacereporter.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.fragments.FavoritesFragment;
import com.dam.spacereporter.spacereporter.ui.fragments.HomeFragment;
import com.dam.spacereporter.spacereporter.ui.fragments.ReadLaterFragment;
import com.dam.spacereporter.spacereporter.ui.fragments.SettingsFragment;
import com.dam.spacereporter.spacereporter.ui.auth.login.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;

    private MaterialToolbar materialToolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

        /*---------- UI ELEMENTS ----------*/

        materialToolbar = findViewById(R.id.mat_toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.nav_home);

        /*---------- UI ELEMENTS LISTENERS ----------*/

        materialToolbar.setNavigationOnClickListener(view ->
                drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_fav:
                    replaceFragment(new FavoritesFragment());
                    break;
                case R.id.nav_readLater:
                    replaceFragment(new ReadLaterFragment());
                    break;
                case R.id.nav_settings:
                    replaceFragment(new SettingsFragment());
                    break;
                case R.id.nav_share:
                    shareApp();
                    break;
                case R.id.nav_rate:
                    askRatings();
                    break;
                case R.id.nav_logout:
                    firebaseAuth.signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
            }
            return true;
        });

        // Place the HOME fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new HomeFragment())
                .commit();
    }

    @Override
    protected void onStart() {

        String fullName = sharedPreferences.getString(getString(R.string.pref_fullName), "NAME");
        String username = sharedPreferences.getString(getString(R.string.pref_username), "USERNAME");
        String email = sharedPreferences.getString(getString(R.string.pref_email), "EMAIL");

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_txt_fullName)).setText(fullName);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_txt_username)).setText(username);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_txt_email)).setText(email);

        super.onStart();
    }

    @Override
    protected void onDestroy() {

        if (!sharedPreferences.getBoolean(getString(R.string.pref_save_login), false))
            firebaseAuth.signOut();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isOpen())
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    /*
     * AUX METHODS / FUNCTIONS
     */

    private void replaceFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void shareApp() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.global_app_name));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_body));
        startActivity(Intent.createChooser(intent, "Choose one"));
    }

    private void askRatings() {

        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Task<Void> flow = manager.launchReviewFlow(this, task.getResult());
                flow.addOnCompleteListener(task2 -> {
                });
            }
        });
    }
}