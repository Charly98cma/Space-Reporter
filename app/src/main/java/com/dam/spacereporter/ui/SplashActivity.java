package com.dam.spacereporter.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;
import com.dam.spacereporter.ui.login.LoginActivity;

// TODO Redo the check (user already seen Splash screen)
public class SplashActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Preferences (flag of already seen SPLASH)
        final SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
        );

        /*
         * LISTENERS
         */

        findViewById(R.id.splash_btn_login).setOnClickListener(view -> {

            // Mark SPLASH screen as seen and go to LOGIN
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.splash_executed), true);
            editor.apply();

            // Transition to Login window
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        });

        /*
         * SPLASH ALREADY SEEN (go to Login directly)
         */

        if (sharedPreferences.getBoolean(getString(R.string.splash_executed), false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}