package com.example.jumptopc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoActivity extends AppCompatActivity {

    WebView videoWebView;
    String url;
    SharedPreferences sharedPreferences;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        sharedPreferences=getSharedPreferences("pref",MODE_PRIVATE);
        url=sharedPreferences.getString("url","");
        videoWebView=(WebView)findViewById(R.id.videoWebView);
        videoWebView.getSettings().setJavaScriptEnabled(true);
        videoWebView.setWebViewClient(new WebViewClient());
        videoWebView.loadUrl(url);
    }
}