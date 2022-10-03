package com.dam.spacereporter.ui;

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
import com.google.android.material.textfield.TextInputLayout;

import com.dam.spacereporter.utils.PwdManager;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.login";

    private EditText login_txt_username, login_txt_password;
    private TextView login_lbl_poweredby;
    Button login_btn_login, login_btn_forgotpwd, login_btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * UI ELEMENTS
         */

        // USER INPUT
        login_txt_username = ((TextInputLayout) findViewById(R.id.login_txt_username)).getEditText();
        login_txt_password = ((TextInputLayout) findViewById(R.id.login_txt_password)).getEditText();

        // BUTTONS
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_forgotpwd = findViewById(R.id.login_btn_forgotpwd);
        login_btn_signup = findViewById(R.id.login_btn_signup);

        // LABEL with hyperlinks
        login_lbl_poweredby = findViewById(R.id.login_lbl_poweredby);
        login_lbl_poweredby.setMovementMethod(LinkMovementMethod.getInstance());

        // TODO Check user already logged and if so, move to MAIN screen

        /*
         * LISTENERS
         */

        // BUTTONS LISTENERS
        login_btn_login.setOnClickListener(view -> {

            // Read username and password
            final String username = Objects.requireNonNull(login_txt_username).getText().toString().trim();
            String password_clr = Objects.requireNonNull(login_txt_password).getText().toString().trim();

            // Check required fields
            if (!validateFields(username, password_clr)) {
                return;
            }

            // Hash password to login
            String hash_password = "";
            try {
                hash_password = PwdManager.getSHA(password_clr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TODO Implement real user login
            Toast.makeText(LoginActivity.this, "Login not implemented", Toast.LENGTH_SHORT).show();

            // Transition to MAIN window
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
        login_btn_forgotpwd.setOnClickListener(view -> {
            // Transition to FORGOTPWD window
            startActivity(new Intent(LoginActivity.this, ForgotPwdActivity.class));
        });
        login_btn_signup.setOnClickListener(view -> {
            // Transition to SIGNUP window
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

     private boolean validateFields(@NonNull String username, @NonNull String password) {
        boolean valid = true;

        if (username.isEmpty()) {
            login_txt_username.setError(getResources().getText(R.string.login_error_username));
            valid = false;
        }

        if (password.isEmpty()) {
            login_txt_password.setError(getResources().getText(R.string.login_error_password));
            valid = false;
        }

        return valid;
    }
}