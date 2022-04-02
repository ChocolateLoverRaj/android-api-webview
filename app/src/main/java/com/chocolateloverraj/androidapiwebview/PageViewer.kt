package com.chocolateloverraj.androidapiwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class PageViewer extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_viewer);

        webView = findViewById(R.id.webView);

        final Intent intent = getIntent();
        final Uri uri = intent.getData();

        System.out.println(uri + ", " + uri.getScheme());
        final String webViewUrl = "https://" + uri.toString().substring(uri.getScheme().length() + 1);
        webView.loadUrl(webViewUrl);
    }
}