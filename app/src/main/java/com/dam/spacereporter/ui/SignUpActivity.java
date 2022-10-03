package com.dam.spacereporter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.spacereporter.R;
import com.dam.spacereporter.utils.EmailValidator;
import com.dam.spacereporter.utils.PwdManager;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.signup";

    EditText signup_txt_fullname, signup_txt_username, signup_txt_password, signup_txt_repeatpassword, signup_txt_email;
    Button signup_btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*
         * UI ELEMENTS
         */

        signup_txt_fullname = getEditText(R.id.signup_txt_fullname);
        signup_txt_username = getEditText(R.id.signup_txt_username);
        signup_txt_password = getEditText(R.id.signup_txt_password);
        signup_txt_repeatpassword = getEditText(R.id.signup_txt_repeatpassword);
        signup_txt_email = getEditText(R.id.signup_txt_email);

        signup_btn_signup = findViewById(R.id.signup_btn_signup);

        /*
         * LISTENERS
         */

        signup_btn_signup.setOnClickListener(view -> {

            // Read fields
            final String fullname, username, password, repeatpassword, email;
            fullname = readEditText(signup_txt_fullname);
            username = readEditText(signup_txt_username);
            password = readEditText(signup_txt_password);
            repeatpassword = readEditText(signup_txt_repeatpassword);
            email = readEditText(signup_txt_email);

            // Check all fields are valid
            if (!validateFields(fullname, username, password, repeatpassword, email)) return;
            if (!(validateEmail(email) & validatePassword(password, repeatpassword))) return;

            // TODO Check user is not already registered (username and email)
            // TODO Register new user if available email and usernames
            Toast.makeText(SignUpActivity.this, "Sign Up not implemented", Toast.LENGTH_SHORT).show();

            // Once registered, back to login
            finish();
        });
    }

    @NonNull
    private EditText getEditText(int ID) {
        return ((TextInputLayout) findViewById(ID)).getEditText();
    }

    @NonNull
    private String readEditText(EditText field) {
        return Objects.requireNonNull(field).getText().toString().trim();
    }

    private boolean validateFields(
            String fullname, String username, String pwd, String rpwd, String email
    ) {
        boolean valid = true;

        if (fullname.isEmpty()) {
            signup_txt_fullname.setError(getResources().getString(R.string.signup_error_name));
            valid = false;
        }

        if (username.isEmpty()) {
            signup_txt_username.setError(getResources().getString(R.string.signup_error_username));
            valid = false;
        }

        if (email.isEmpty()) {
            signup_txt_email.setError(getResources().getString(R.string.signup_error_email));
            valid = false;
        }

        if (pwd.isEmpty()) {
            signup_txt_password.setError(getResources().getString(R.string.signup_error_pwd));
            valid = false;
        }

        if (rpwd.isEmpty()) {
            signup_txt_repeatpassword.setError(getResources().getString(R.string.signup_error_pwd));
            valid = false;
        }

        return valid;
    }

    private boolean validateEmail(@NonNull String email) {

        if (!EmailValidator.isValidEmail(email)) {
            signup_txt_email.setError(getResources().getText(R.string.signup_error_invalidemail));
            return false;
        }
        return true;
    }

    private boolean validatePassword(String pwd, String rpwd) {

        if (!PwdManager.validatePassword(pwd, rpwd)) {
            signup_txt_password.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            signup_txt_repeatpassword.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            return false;
        }

        if (!pwd.equals(rpwd)) {
            signup_txt_repeatpassword.setError(getResources().getString(R.string.signup_error_nomatchpwd));
            return false;
        }
        return true;
    }
}