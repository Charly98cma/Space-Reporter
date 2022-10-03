package com.dam.spacereporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import com.dam.spacereporter.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.splash";

    SharedPreferences sharedPreferences;
    ImageButton splash_btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Preferences (flag of already seen SPLASH)
        sharedPreferences = getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
        );

        /*
         * UI ELEMENTS
         */

        // BUTTONS
        splash_btn_login = (ImageButton) findViewById(R.id.splash_btn_login);


        /*
         * EARLY EXIT (user already logged in)
         */

        // Go to LOGIN directly if SPLASH already seen
        if (sharedPreferences.getBoolean(getString(R.string.splash_executed), false)) {
            // Transition to Login window
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        /*
         * LISTENERS
         */

        // Go to LOGIN button
        splash_btn_login.setOnClickListener(view -> {

            // Mark SPLASH screen as seen and go to LOGIN
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.splash_executed), true);
            editor.apply();

            // Transition to Login window
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        });
    }
}