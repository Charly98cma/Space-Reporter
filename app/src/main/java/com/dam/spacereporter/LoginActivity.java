package com.dam.spacereporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import com.dam.spacereporter.utils.PwdManager;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.login";

    EditText login_txt_username, login_txt_password;
    TextView login_lbl_poweredby;
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

        // TEXT EDIT LISTENERS
        login_txt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login_txt_username.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        login_txt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login_txt_password.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // BUTTONS LISTENERS
        login_btn_login.setOnClickListener(view -> {

            // Read username and password
            final String username = Objects.requireNonNull(login_txt_username).getText().toString();
            String password_clr = Objects.requireNonNull(login_txt_password).getText().toString().trim();

            // Check required fields
            if (checkAllFields(username, password_clr)) {
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
            startActivity(new Intent(LoginActivity.this, ForgotPwd.class));
        });
        login_btn_signup.setOnClickListener(view -> {
            // Transition to SIGNUP window
            startActivity(new Intent(LoginActivity.this, SignUp.class));
        });
    }

    private boolean checkAllFields(String username, String password) {
        boolean bad_fields = false;

        if (username.isEmpty()) {
            login_txt_username.setError("Username required!");
            bad_fields = true;
        }

        if (password.isEmpty()) {
            login_txt_password.setError("Password required!");
            bad_fields = true;
        }

        return bad_fields;
    }
}