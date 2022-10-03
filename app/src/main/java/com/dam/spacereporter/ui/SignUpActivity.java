package com.dam.spacereporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.spacereporter.R;
import com.dam.spacereporter.utils.EmailValidator;
import com.dam.spacereporter.utils.PwdManager;

import com.dam.spacereporter.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*
         * UI ELEMENTS
         */

        final EditText signup_et_username =
                ((TextInputLayout) findViewById(R.id.signup_et_username)).getEditText();
        final EditText signup_et_fullname =
                ((TextInputLayout) findViewById(R.id.signup_et_fullname)).getEditText();
        final EditText signup_et_password =
                ((TextInputLayout) findViewById(R.id.signup_et_password)).getEditText();
        final EditText signup_et_repeatpassword =
                ((TextInputLayout) findViewById(R.id.signup_et_repeatpassword)).getEditText();
        final EditText signup_et_email =
                ((TextInputLayout) findViewById(R.id.signup_et_email)).getEditText();

        /*
         * LISTENERS
         */

        findViewById(R.id.signup_btn_signup).setOnClickListener(view -> {

            // Validate the fields of the form
            if (!isFormValid(signup_et_fullname, signup_et_username) |
                    (!(isEmailValid(signup_et_email) & isPwdValid(signup_et_password, signup_et_repeatpassword))))
                return;

            // TODO Register new user if available email and usernames
            Toast.makeText(SignUpActivity.this, "Sign Up not implemented", Toast.LENGTH_SHORT).show();

            // Once registered, back to login
            finish();
        });
    }

    private boolean isFormValid(EditText signup_et_fullname, EditText signup_et_username) {

        // Check fields are not null
        if (signup_et_fullname == null || signup_et_username == null) {
            Utils.toastException(SignUpActivity.this);
            return false;
        }

        // Read fields content
        final String fullname = signup_et_fullname.getText().toString();
        final String username = signup_et_username.getText().toString();

        // Validate the fields content (valid name and username)
        boolean valid = true;
        if (fullname.trim().isEmpty()) {
            signup_et_fullname.setError(getResources().getString(R.string.signup_error_name));
            valid = false;
        }
        if (username.trim().isEmpty()) {
            signup_et_username.setError(getResources().getString(R.string.signup_error_username));
            valid = false;
        }
        return valid;
    }

    private boolean isEmailValid(EditText signup_et_email) {

        if (signup_et_email == null) {
            Utils.toastException(SignUpActivity.this);
            return false;
        }

        if (!EmailValidator.isValidEmail(signup_et_email.getText().toString().trim())) {
            signup_et_email.setError(getResources().getString(R.string.signup_error_invalidemail));
            return false;
        }
        return true;
    }

    private boolean isPwdValid(EditText signup_et_password, EditText signup_et_repeatpassword) {

        if (signup_et_repeatpassword == null || signup_et_password == null) {
            Utils.toastException(SignUpActivity.this);
            return false;
        }

        final String pwd = signup_et_password.getText().toString().trim();
        final String rpwd = signup_et_repeatpassword.getText().toString().trim();

        if (!PwdManager.validatePassword(pwd, rpwd)) {
            signup_et_password.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            signup_et_repeatpassword.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            return false;
        }

        if (!pwd.equals(rpwd)) {
            signup_et_repeatpassword.setError(getResources().getString(R.string.signup_error_nomatchpwd));
            return false;
        }
        return true;
    }
}