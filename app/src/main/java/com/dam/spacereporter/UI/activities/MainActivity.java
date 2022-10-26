package com.dam.spacereporter.UI.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dam.spacereporter.R;
import com.dam.spacereporter.Utils.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.main";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FirebaseAuth mAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        /*
         * UI ELEMENTS
         */

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        /*
         * LISTENERS
         */

        ((MaterialToolbar) findViewById(R.id.matToolbar)).setNavigationOnClickListener(view ->
                drawerLayout.openDrawer(GravityCompat.START));

        // TODO Implement the rest of the menus
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {
                case R.id.nav_home:
                    return true;
                case R.id.nav_fav:
                    return true;
                case R.id.nav_readLater:
                    return true;
                case R.id.nav_settings:
                    return true;
                case R.id.nav_share:
                    return true;
                case R.id.nav_logout:
                    // Log out and back to SIGNIN
                    mAuth.signOut();
                    Toast.makeText(MainActivity.this, R.string.main_toast_logout, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                    finish();
                default:
                    return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        View view = navigationView.getHeaderView(0);
        // TODO Read DB and set fullName and username
        ((TextView) view.findViewById(R.id.nav_email)).setText(currentUser.getEmail());

    }
}