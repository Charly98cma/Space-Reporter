package com.dam.spacereporter.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.dam.spacereporter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.splash";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "onCreate: Activity created");
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Check if user has already sign in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // No user, go to StartActivity
            Log.d(TAG, "onStart: No user");
            Log.d(TAG, "onStart: Transitioning to START window");
            nextActivity(StartActivity.class);
        } else {
            // User present, check valid credentials
            currentUser.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // User credentials valid
                    Log.d(TAG, "onStart: User already sign in");
                    Log.d(TAG, "onStart: Transitioning to MAIN window");
                    nextActivity(MainActivity.class);
                } else {
                    Log.w(TAG, "onStart: User credentials no longer valid");
                    // User has been disabled, deleted or login credentials are no longer valid
                    Toast.makeText(SplashActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onStart: Transitioning to SIGNIN window");
                    nextActivity(SignInActivity.class);
                }
            });
        }
    }

    private void nextActivity(@SuppressWarnings("rawtypes") Class next) {
        startActivity(new Intent(SplashActivity.this, next));
        finish();
    }
}