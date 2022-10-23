package com.dam.spacereporter.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;
import com.dam.spacereporter.Utils.DataValidator;
import com.dam.spacereporter.Utils.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

// TODO Implement process
public class ForgotPwdActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.forgotpwd";
    private FirebaseAuth mAuth;
    private EditText forgotpwd_et_email;
    private ProgressBar forgotpwd_progbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_forgot_pwd);

        Log.d(TAG, "onCreate: Activity created");

        mAuth = FirebaseAuth.getInstance();

        /*
         * UI ELEMENTS
         */

        forgotpwd_et_email = ((TextInputLayout) findViewById(R.id.forgotpwd_et_email)).getEditText();
        forgotpwd_progbar = findViewById(R.id.forgotpwd_progbar);

        /*
         * LISTENERS
         */

        findViewById(R.id.forgotpwd_btn_sendCode).setOnClickListener(view -> sendEmail());
    }

    /*
     * AUXILIAR FUNCTIONS
     */

    private void sendEmail() {

        if (!isEmailValid()) return;

        Log.d(TAG, "sendEmail: Valid email");

        // Check internet connectivity
        if (!Utils.isNetworkAvailable(ForgotPwdActivity.this)) {
            Log.w(TAG, "signUpUser:isNetworkAvailable:false");
            Toast.makeText(this, R.string.global_noInternet, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "signUpUser:isNetworkAvailable:true");

        forgotpwd_progbar.setVisibility(View.VISIBLE);

        String email = forgotpwd_et_email.getText().toString().trim();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            forgotpwd_progbar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Log.d(TAG, "sendPasswordResetEmail:success");
                Toast.makeText(ForgotPwdActivity.this, R.string.forgotpwd_toast_sendEmail, Toast.LENGTH_SHORT).show();

                // If email successful, go bak to Sign In
                finish();
            }else {
                Log.w(TAG, "signInUser:sendPasswordResetEmail:failure", task.getException());
                Toast.makeText(ForgotPwdActivity.this, R.string.forgotpwd_error_invalidEmail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid() {

        if (forgotpwd_et_email == null) {
            Log.e(TAG, "isEmailValid: Email EditText is null");
            Utils.toastException(ForgotPwdActivity.this);
            return false;
        }

        String email = forgotpwd_et_email.getText().toString().trim();

        // Check email format is valid
        if (!DataValidator.isValidEmail(email)) {
            Log.w(TAG, "isEmailValid: Invalid email");
            forgotpwd_et_email.setError(getResources().getString(R.string.signup_error_invalidemail));
            return false;
        }
        return true;
    }
}