package com.chocolateloverraj.androidapiwebview

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PageViewer : AppCompatActivity() {
    companion object {
        const val customScheme = "android-api-webview"
        const val lastUrlId = "lastUrl"
        var androidPermissions: Map<String, String> = mapOf(
            Pair(PermissionRequest.RESOURCE_AUDIO_CAPTURE, Manifest.permission.RECORD_AUDIO),
            Pair(PermissionRequest.RESOURCE_VIDEO_CAPTURE, Manifest.permission.CAMERA)
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_viewer)
        val webView = findViewById<WebView>(R.id.webView)
        val intent = intent
        val uri = intent.data!!
        println(uri)
        val lastUrl = savedInstanceState?.getString(lastUrlId)
        val webViewUrl = lastUrl ?: "https" + uri.toString().substring(uri.scheme!!.length)
        println(webViewUrl)
        webView.settings.javaScriptEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.blockNetworkLoads = false
        webView.webViewClient = WebViewClient()

        var requestingPermissions = false
        val contract = ActivityResultContracts.RequestMultiplePermissions()
        var permissionsResultCallback: ActivityResultCallback<Map<String, Boolean>>? = null
        val permissionRequestLauncher = registerForActivityResult(contract) { permissions ->
            requestingPermissions = false
            permissionsResultCallback?.onActivityResult(permissions)
        }

        val context = this
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                if (!requestingPermissions) {
                    requestingPermissions = true
                    println(request.resources.joinToString(", "))

                    AlertDialog.Builder(context)
                        .setTitle(resources.getQuantityString(R.plurals.permission, request.resources.size))
                        .setMultiChoiceItems(
                            request.resources,
                            request.resources.map { false }.toBooleanArray()
                        ) { _, _, _ ->
                        }
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            // FIXME: Filter out unchecked resources
                            permissionRequestLauncher.launch(request.resources.map { webViewResource ->
                                androidPermissions[webViewResource]
                            }.toTypedArray())
                            permissionsResultCallback = ActivityResultCallback {
                                // FIXME: Don't grant permissions we don't have
                                request.grant(request.resources)
                            }
                        }
                        .setNegativeButton(R.string.deny_all_permissions) { _, _ ->
                            request.deny()
                        }
                        .show()
                }
            }
        }
        webView.loadUrl(webViewUrl)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val webView = findViewById<WebView>(R.id.webView)
        outState.putString(lastUrlId, webView.url)
    }
}