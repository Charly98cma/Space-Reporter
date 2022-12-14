package com.dam.spacereporter.spacereporter.ui;

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
import com.dam.spacereporter.spacereporter.ui.auth.login.LoginActivity;
import com.dam.spacereporter.spacereporter.ui.favorites.FavoritesFragment;
import com.dam.spacereporter.spacereporter.ui.home.HomeFragment;
import com.dam.spacereporter.spacereporter.ui.settings.SettingsFragment;
import com.dam.spacereporter.spacereporter.utils.Constants;
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

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(Constants.PREF_KEY, MODE_PRIVATE);

        /*---------- UI ELEMENTS ----------*/

        MaterialToolbar materialToolbar = findViewById(R.id.mat_toolbar);
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
                    replaceFragment(new com.dam.spacereporter.spacereporter.ui.readlater.ReadLaterFragment());
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

        String fullName = sharedPreferences.getString(Constants.PREF_USER_FULLNAME, "NAME");
        String username = sharedPreferences.getString(Constants.PREF_USER_USERNAME, "USERNAME");
        String email = sharedPreferences.getString(Constants.PREF_USER_EMAIL, "EMAIL");

        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.nav_txt_fullName)).setText(fullName);
        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.nav_txt_username)).setText(username);
        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.nav_txt_email)).setText(email);

        super.onStart();
    }

    @Override
    protected void onDestroy() {

        if (!sharedPreferences.getBoolean(Constants.PREF_SAVE_LOGIN, false)) {
            clearCache();
            firebaseAuth.signOut();
        }
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

    private void clearCache() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.PREF_USER_FULLNAME);
        editor.remove(Constants.PREF_USER_USERNAME);
        editor.remove(Constants.PREF_USER_EMAIL);
        editor.remove(Constants.PREF_SAVE_LOGIN);
        editor.apply();
    }

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