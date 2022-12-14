package com.dam.spacereporter.spacereporter.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.auth.login.LoginActivity;
import com.dam.spacereporter.spacereporter.utils.Constants;
import com.dam.spacereporter.spacereporter.utils.NetworkConnection;
import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(Constants.PREF_KEY, MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!sharedPreferences.getBoolean(Constants.PREF_SEEN_START, false)) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        } else if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            if (NetworkConnection.isNetworkConnected(this)) {
                // Update user credentials beforehand
                firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        startActivity(new Intent(this, MainActivity.class));
                    else
                        startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });
            } else {
                // No connection, assume user credentials are valid
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }
}