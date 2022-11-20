package com.dam.spacereporter.spacereporter.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.auth.login.LoginActivity;
import com.dam.spacereporter.spacereporter.utils.Constants;
import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final MaterialButton start_btn_toLogin =
                findViewById(R.id.start_btn_toLogin);

        start_btn_toLogin.setOnClickListener(view -> {

            SharedPreferences sharedPreferences =
                    getSharedPreferences(Constants.PREF_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.PREF_SEEN_START, true);
            editor.apply();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}