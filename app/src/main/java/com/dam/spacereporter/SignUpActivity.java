package com.dam.spacereporter;

import static com.dam.spacereporter.utils.PwdManager.validatePassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            if (!validFields(fullname, username, password, repeatpassword, email)) return;
            if (!(validEmail(email) & validPassword(password, repeatpassword))) return;

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

    private boolean validFields(
            String fullname, String username, String pwd, String rpwd, String email
    ) {
        boolean valid = true;

        if (fullname.isEmpty()) {
            signup_txt_fullname.setError("Name required");
            valid = false;
        }

        if (username.isEmpty()) {
            signup_txt_username.setError("Username required");
            valid = false;
        }

        if (email.isEmpty()) {
            signup_txt_email.setError("Email required");
            valid = false;
        }

        if (pwd.isEmpty()) {
            signup_txt_password.setError("Password required");
            valid = false;
        }

        if (rpwd.isEmpty()) {
            signup_txt_repeatpassword.setError("Password required");
            valid = false;
        }

        return valid;
    }

    private boolean validEmail(@NonNull String email) {
        boolean valid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!valid)
            signup_txt_email.setError("Invalid email address");
        return valid;
    }

    private boolean validPassword(String pwd, String rpwd) {

        if (!validatePassword(pwd)) {
            signup_txt_password.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            return false;
        }

        if (!pwd.equals(rpwd)) {
            signup_txt_repeatpassword.setError(getResources().getString(R.string.signup_error_nomatchpwd));
            return false;
        }
        return true;
    }
}