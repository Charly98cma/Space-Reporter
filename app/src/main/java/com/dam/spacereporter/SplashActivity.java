package  com.dam.spacereporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

public class SplashActivity extends AppCompatActivity {

    private String tag = "SPLASH_SCREEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageButton splash_btn_login = (ImageButton) findViewById(R.id.splash_btn_login);
        splash_btn_login.setOnClickListener(view -> goToLogin(SplashActivity.this));

        // Avoid SPLASH screen if already shown
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("splash_executed", true)) {
            goToLogin(this);
        }
    }

    private void goToLogin(Context oldContext) {
        startActivity(new Intent(oldContext, LoginActivity.class));
        finish();
    }
}