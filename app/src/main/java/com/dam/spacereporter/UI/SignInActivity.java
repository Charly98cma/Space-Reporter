package com.dam.spacereporter.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;
import com.dam.spacereporter.Utils.DataValidator;
import com.dam.spacereporter.Utils.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.signin";
    private FirebaseAuth mAuth;
    private EditText login_et_email, login_et_password;
    private ProgressBar signup_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_sign_in);

        Log.d(TAG, "onCreate: Activity created");

        mAuth = FirebaseAuth.getInstance();

        /*
         * UI ELEMENTS
         */

        login_et_email = ((TextInputLayout) findViewById(R.id.signin_et_email)).getEditText();
        login_et_password = ((TextInputLayout) findViewById(R.id.signin_et_password)).getEditText();

        signup_progressBar = findViewById(R.id.signup_progbar);

        ((TextView) findViewById(R.id.signin_txt_poweredby)).setMovementMethod(LinkMovementMethod.getInstance());

        /*
         * LISTENERS
         */

        findViewById(R.id.login_btn_login).setOnClickListener(view -> {
            Log.d(TAG, "onCreate:login_btn_login:pressed");
            signInUser();
        });
        findViewById(R.id.login_btn_forgotpwd).setOnClickListener(view -> {
            Log.d(TAG, "onCreate:login_btn_forgotpwd:pressed");
            // Transition to FORGOTPWD window
            startActivity(new Intent(SignInActivity.this, ForgotPwdActivity.class));
        });
        findViewById(R.id.login_btn_signup).setOnClickListener(view -> {
            Log.d(TAG, "onCreate:login_btn_signup:pressed");
            // Transition to SIGNUP window
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
    }

    /*
     * AUXILIAR FUNCTIONS
     */

    private void signInUser() {

        // Check fields are not null or empty
        if (!isFormValid()) return;

        Log.d(TAG, "signInUser: Valid data");

        if (!Utils.isNetworkAvailable(SignInActivity.this)) {
            Log.w(TAG, "signInUser:isNetworkAvailable:false");
            Toast.makeText(this, R.string.global_noInternet, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "signInUser:isNetworkAvailable:true");

        signup_progressBar.setVisibility(View.VISIBLE);

        String email = login_et_email.getText().toString().trim();
        String password = login_et_password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            signup_progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Log.d(TAG, "signInUser:signInWithEmailAndPassword:success");
                Log.d(TAG, "signInUser: Transitioning to MAIN window");
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }else {
                Log.w(TAG, "signInUser:signInWithEmailAndPassword:failure", task.getException());
                Toast.makeText(SignInActivity.this, R.string.signin_toast_wrongcredentials, Toast.LENGTH_SHORT).show();
                login_et_password.setText("");
            }
        });
    }

    private boolean isFormValid() {

        Log.d(TAG, "isFormValid: Checking forms data...");

        if (login_et_email == null || login_et_password == null) {
            Log.e(TAG, "isFormValid: Email and/or Pwd EditText is null");
            Utils.toastException(SignInActivity.this);
            return false;
        }

        final String email = login_et_email.getText().toString().trim();
        final String password = login_et_password.getText().toString().trim();

        boolean valid = true;
        if (email.isEmpty()) {
            Log.w(TAG, "isFormValid: No email provided");
            login_et_email.setError(getResources().getString(R.string.signin_error_noEmail));
            valid = false;
        }
        if (!DataValidator.isValidEmail(email)) {
            Log.w(TAG, "isFormValid: Invalid email");
            login_et_email.setError(getResources().getString(R.string.signin_error_invalidEmail));
            valid = false;
        }
        if (password.isEmpty()) {
            Log.w(TAG, "isFormValid: Invalid password");
            login_et_password.setError(getResources().getString(R.string.signin_error_password));
            valid = false;
        }
        return valid;
    }
}