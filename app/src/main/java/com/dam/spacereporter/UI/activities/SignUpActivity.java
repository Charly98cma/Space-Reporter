package com.dam.spacereporter.UI.activities;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;
import com.dam.spacereporter.Tasks.LoginManager;
import com.dam.spacereporter.Utils.DBUsers;
import com.dam.spacereporter.Utils.DataValidator;
import com.dam.spacereporter.Utils.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.signup";

    private LoginManager login;
    private static DBUsers dbUser;

    private EditText signup_et_username, signup_et_fullName, signup_et_password, signup_et_repeatPassword, signup_et_email;
    private ProgressBar signup_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_sign_up);

        Log.d(TAG, "onCreate: Activity created");

        login = LoginManager.getInstance();
        dbUser = DBUsers.getInstance();

        /*
         * UI ELEMENTS
         */

        signup_et_username = ((TextInputLayout) findViewById(R.id.signup_et_username)).getEditText();
        signup_et_fullName = ((TextInputLayout) findViewById(R.id.signup_et_fullname)).getEditText();
        signup_et_password = ((TextInputLayout) findViewById(R.id.signup_et_password)).getEditText();
        signup_et_repeatPassword = ((TextInputLayout) findViewById(R.id.signup_et_repeatpassword)).getEditText();
        signup_et_email = ((TextInputLayout) findViewById(R.id.signup_et_email)).getEditText();

        ((TextView) findViewById(R.id.signup_txt_poweredby)).setMovementMethod(LinkMovementMethod.getInstance());

        signup_progressBar = findViewById(R.id.signup_progbar);

        /*
         * LISTENERS
         */

        findViewById(R.id.signup_btn_signup).setOnClickListener(view -> {
            Log.d(TAG, "onCreate: User pressed the 'Sign In' button");
            signUpUser();
        });
    }

    /*
     * AUXILIAR FUNCTIONS
     */
    private void signUpUser() {

        // Validate the fields of the form
        if (!isFormValid() | !isEmailValid() | !isPwdValid()) return;

        Log.d(TAG, "signUpUser: Valid data");

        // Check internet connectivity
        if (!Utils.isNetworkAvailable(SignUpActivity.this)) {
            Log.w(TAG, "signUpUser:isNetworkAvailable:false");
            Toast.makeText(this, R.string.global_noInternet, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "signUpUser:isNetworkAvailable:true");

        signup_progressBar.setVisibility(View.VISIBLE);

        String fullName = signup_et_fullName.getText().toString();
        String username = signup_et_username.getText().toString();
        String email = signup_et_email.getText().toString();
        String password = signup_et_password.getText().toString();

        // Check username
        dbUser.isUserInDB(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Username already in use
                    signup_progressBar.setVisibility(View.GONE);
                    signup_et_username.setError(getResources().getString(R.string.signup_db_usernameUsed));
                }else {
                    // FIREBASE AUTH: USER CREATION
                    login.createUser(email, password).addOnCompleteListener(fb_task -> {
                        if(fb_task.isSuccessful()) {
                            Log.d(TAG, "signUpUser:LoginManager:createUser:success");
                            login.signOut();
                            // Successful authentication leads to register user in Users DB
                            dbUser.storeUser(fullName, username, email).addOnCompleteListener(db_task -> {
                                signup_progressBar.setVisibility(View.GONE);
                                if (db_task.isSuccessful()) {
                                    // User registered on Firebase and DB
                                    Toast.makeText(SignUpActivity.this, R.string.signup_login_successful, Toast.LENGTH_SHORT).show();
                                    // Once fully registered, back to sign in page
                                    finish();
                                }else {
                                    // Unexpected error while adding user to DB
                                    Toast.makeText(SignUpActivity.this, Objects.requireNonNull(db_task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            // Inform user of error creating account
                            Log.w(TAG, "signInUser:LoginManager:createUser:failure", fb_task.getException());
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(fb_task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            signup_progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    /*
     * DATA VALIDATION
     */

    private boolean isFormValid() {

        Log.d(TAG, "isFormValid: Checking forms data...");

        // Check fields are not null
        if (signup_et_fullName == null || signup_et_username == null) {
            Log.e(TAG, "isFormValid: FullName and/or Username EditText is null");
            Utils.toastException(SignUpActivity.this);
            return false;
        }

        // Read fields content
        final String fullName = signup_et_fullName.getText().toString().trim();
        final String username = signup_et_username.getText().toString().trim();

        // Validate the fields content (valid name and username)
        boolean valid = true;
        if (fullName.isEmpty()) {
            Log.w(TAG, "isFormValid: Full name is empty");
            signup_et_fullName.setError(getResources().getString(R.string.signup_error_name));
            valid = false;
        }
        if (username.isEmpty()) {
            Log.w(TAG, "isFormValid: Username is empty");
            signup_et_username.setError(getResources().getString(R.string.signup_error_username));
            valid = false;
        }
        return valid;
    }

    private boolean isEmailValid() {

        if (signup_et_email == null) {
            Log.e(TAG, "isEmailValid: Email EditText is null");
            Utils.toastException(SignUpActivity.this);
            return false;
        }

        String email = signup_et_email.getText().toString().trim();

        // Check email format is valid
        if (!DataValidator.isValidEmail(email)) {
            Log.w(TAG, "isEmailValid: Invalid email");
            signup_et_email.setError(getResources().getString(R.string.signup_error_invalidemail));
            return false;
        }
        return true;
    }

    private boolean isPwdValid() {

        if (signup_et_repeatPassword == null || signup_et_password == null) {
            Log.e(TAG, "isPwdValid: Pwd EditText is null");
            Utils.toastException(SignUpActivity.this);
            return false;
        }

        final String pwd = signup_et_password.getText().toString().trim();
        final String pwdConfirmation = signup_et_repeatPassword.getText().toString().trim();

        if (!DataValidator.validatePassword(pwd, pwdConfirmation)) {
            Log.w(TAG, "isEmailValid: Invalid password");
            signup_et_password.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            signup_et_repeatPassword.setError(getResources().getString(R.string.signup_error_pwdrequirements));
            return false;
        }

        if (!pwd.equals(pwdConfirmation)) {
            Log.w(TAG, "isEmailValid: User passwords do not match");
            signup_et_repeatPassword.setError(getResources().getString(R.string.signup_error_nomatchpwd));
            return false;
        }
        return true;
    }
}