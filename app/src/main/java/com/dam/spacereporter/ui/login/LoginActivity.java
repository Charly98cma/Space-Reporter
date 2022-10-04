package com.dam.spacereporter.ui.login;

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
import com.dam.spacereporter.utils.PwdManager;
import com.dam.spacereporter.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * UI ELEMENTS
         */

        final EditText login_et_username =
                ((TextInputLayout) findViewById(R.id.login_et_username)).getEditText();
        final EditText login_et_password =
                ((TextInputLayout) findViewById(R.id.login_et_password)).getEditText();

        final Button login_btn_login = findViewById(R.id.login_btn_login);
        final Button login_btn_forgotpwd = findViewById(R.id.login_btn_forgotpwd);
        final Button login_btn_signup = findViewById(R.id.login_btn_signup);

        ((TextView) findViewById(R.id.login_txt_poweredby)).setMovementMethod(LinkMovementMethod.getInstance());

        /*
         * LISTENERS
         */

        login_btn_login.setOnClickListener(view -> {

            // Check fields are not null or empty
            if (!isFormValid(login_et_username, login_et_password)) return;

            final String username = login_et_username.getText().toString().trim();
            final String pwd_clr = login_et_password.getText().toString().trim();

            // Hash password
            final String pwd_hash;
            try {
                pwd_hash = PwdManager.getPwdHash(pwd_clr, username);
                Toast.makeText(LoginActivity.this, pwd_hash, Toast.LENGTH_SHORT).show();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, getResources().getText(R.string.login_error_hash), Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO Implement real login
            boolean loginSuccessful = true;
            if (loginSuccessful) {
                goToMain();
            }
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

    private boolean isFormValid(EditText username, EditText password) {

        if (username == null || password == null) {
            Utils.toastException(LoginActivity.this);
            return false;
        }

        final String username_str = username.getText().toString().trim();
        final String password_str = password.getText().toString().trim();

        boolean valid = true;
        if (username_str.isEmpty()) {
            username.setError(getResources().getString(R.string.login_error_username));
            valid = false;
        }
        if (password_str.isEmpty()) {
            password.setError(getResources().getString(R.string.login_error_password));
            valid = false;
        }
        return valid;
    }
}