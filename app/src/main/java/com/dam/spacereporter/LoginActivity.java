package com.dam.spacereporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private String tag = "LOGIN_SCREEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Do not show SPLASH screen if seen once
        // FIXME Splash screen is not showing up
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("splash_executed", false)) {
            SharedPreferences.Editor edt = pref.edit();
            edt.putBoolean("splash_executed", true);
            edt.apply();
        }

        // TODO Check user already logued and if so, move to MAIN screen

        // Access BUTTONS
        // TODO Implement login buttons listeners
        Button login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_btn_login.setOnClickListener(view -> {

        });

        Button login_btn_forgotpwd = (Button) findViewById(R.id.login_btn_forgotpwd);
        login_btn_forgotpwd.setOnClickListener(view -> {

        });

        Button login_btn_signup = (Button) findViewById(R.id.login_btn_signup);
        login_btn_signup.setOnClickListener(view -> {

        });
    }
}