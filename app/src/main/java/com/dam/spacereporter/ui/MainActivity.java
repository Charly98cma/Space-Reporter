package com.dam.spacereporter.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;

public class MainActivity extends AppCompatActivity {

    private static final String tag = "com.dam.spacereporter.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}