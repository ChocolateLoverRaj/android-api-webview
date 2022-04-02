package com.chocolateloverraj.androidapiwebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class PageViewer : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_viewer)
        val webView = findViewById<WebView>(R.id.webView)
        val intent = intent
        val uri = intent.data!!
        println(uri)
        val webViewUrl = "https" + uri.toString().substring(uri.scheme!!.length)
        println(webViewUrl)
        webView.settings.javaScriptEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.blockNetworkLoads = false
        webView.webViewClient = WebViewClient()
        webView.loadUrl(webViewUrl)
    }
}