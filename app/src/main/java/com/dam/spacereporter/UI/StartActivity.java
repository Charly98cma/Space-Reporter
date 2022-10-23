package com.dam.spacereporter.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.start";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_screen);

        Log.d(TAG, "onCreate: Activity created");

        // Preferences (flag of already seen SPLASH)
        sharedPreferences = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);

        /*
         * LISTENERS
         */

        findViewById(R.id.splash_btn_login).setOnClickListener(view -> {

            Log.d(TAG, "splash_btn_login:pressed");

            // Mark SPLASH screen as seen and go to LOGIN
            editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.start_screen_seen), true);
            editor.apply();

            // Transition to Login window
            goToSignIn();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // SPLASH ALREADY SEEN (go to Login directly)
        if (sharedPreferences.getBoolean(getString(R.string.start_screen_seen), false)) {
            Log.d(TAG, "sharedPreferences:start_screen_seen:true");
            goToSignIn();
        }
    }

    private void goToSignIn() {
        Log.d(TAG, "goToSignIn");
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}