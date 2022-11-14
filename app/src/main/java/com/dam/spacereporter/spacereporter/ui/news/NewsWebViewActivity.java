package com.dam.spacereporter.spacereporter.ui.news;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.spacereporter.R;

public class NewsWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        /*---------- UI ELEMENTS ----------*/

        WebView newsWebView = findViewById(R.id.news_webView);
        // Enable JS for better experience with WebViews
        newsWebView.getSettings().setJavaScriptEnabled(true);
        newsWebView.loadUrl(getIntent().getStringExtra(getString(R.string.news_intent_urlKey)));
    }
}