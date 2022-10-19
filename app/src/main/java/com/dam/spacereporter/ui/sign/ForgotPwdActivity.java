package com.dam.spacereporter.ui.sign;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;

// TODO Implement process
public class ForgotPwdActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.forgotpwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_pwd);


        Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT).show();
    }
}