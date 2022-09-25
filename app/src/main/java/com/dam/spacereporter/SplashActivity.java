package  com.dam.spacereporter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String tag = "SPLASH_SCREEN";

    SharedPreferences sharedPreferences;

    private void goToLogin(Context context) {
        // Transition to Login window
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
        );

        // Go to LOGIN directly if SPLASH already seen
        if (sharedPreferences.getBoolean(getString(R.string.splash_executed), false)) {
            Log.i(tag, "Redirected to LOGIN");
            goToLogin(this);
        }

        // BUTTON to go to LOGIN screen
        findViewById(R.id.splash_btn_login).setOnClickListener(view -> {

            Log.i(tag, "Redirected to LOGIN");

            // Mark SPLASH screen as seen and go to LOGIN
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.splash_executed), true);
            editor.apply();

            goToLogin(SplashActivity.this);
        });
    }
}