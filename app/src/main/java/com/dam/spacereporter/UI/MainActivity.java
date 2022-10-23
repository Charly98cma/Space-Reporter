package com.dam.spacereporter.UI;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.dam.spacereporter.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
    }
}