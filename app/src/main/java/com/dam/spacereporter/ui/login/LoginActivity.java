package com.dam.spacereporter.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.spacereporter.R;
import com.dam.spacereporter.ui.forgotpwd.ForgotPwdActivity;
import com.dam.spacereporter.ui.MainActivity;
import com.dam.spacereporter.ui.SignUpActivity;
import com.google.android.material.textfield.TextInputLayout;

import com.dam.spacereporter.utils.PwdManager;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * UI ELEMENTS
         */

        final EditText login_txt_username = ((TextInputLayout) findViewById(R.id.login_txt_username)).getEditText();
        final EditText login_txt_password = ((TextInputLayout) findViewById(R.id.login_txt_password)).getEditText();

        final Button login_btn_login = findViewById(R.id.login_btn_login);
        final Button login_btn_forgotpwd = findViewById(R.id.login_btn_forgotpwd);
        final Button login_btn_signup = findViewById(R.id.login_btn_signup);

        ((TextView) findViewById(R.id.login_lbl_poweredby)).setMovementMethod(LinkMovementMethod.getInstance());

        /*
         * LISTENERS
         */

        login_btn_login.setOnClickListener(view -> {

            // TODO Read username and pwd
            //      Check != null and != ""

            // TODO Hash password (PwdManager.getHash(pwd))

            // TODO Implement real login

            Toast.makeText(LoginActivity.this, "Login not implemented", Toast.LENGTH_SHORT).show();

            // Transition to MAIN window
            goToMain();
        });
        login_btn_forgotpwd.setOnClickListener(view -> {
            // Transition to FORGOTPWD window
            startActivity(new Intent(LoginActivity.this, ForgotPwdActivity.class));
        });
        login_btn_signup.setOnClickListener(view -> {
            // Transition to SIGNUP window
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        /*
         * USER LOGIN CHECK
         */

        // TODO Check user already logged and if so, move to MAIN screen
        boolean userLogged = false;
        if (userLogged) {
            goToMain();
        }
    }

    // Function to transition to the MAIN activity
    // (used if LogIn successful or user already logged)
    private void goToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}