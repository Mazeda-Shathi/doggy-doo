package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class tryWebview extends AppCompatActivity {
   WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_webview);
        web=findViewById(R.id.webview);
        web.loadUrl("file:///android_asset/breedimg.html");
    }
}