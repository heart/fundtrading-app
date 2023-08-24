package com.kkpfg.fundtrading.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.kkpfg.fundtrading.R

class WebViewActivity : AppCompatActivity() {

    companion object {
        const val URL_KEY = "url_key"

//        fun createIntent(context: Context,url: String): Intent{
//            val intent = Intent(context, WebViewActivity::class.java )
//            intent.putExtra(URL_KEY, url)
//            return intent
//        }

        fun createIntent(context: Context, url: String) = Intent(context, WebViewActivity::class.java).also {
            it.putExtra(URL_KEY, url)
        }


    }

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val url = intent.getStringExtra(URL_KEY) ?: ""
        if (url.isEmpty()) {
            // Handle error, invalid URL
            finish()
            return
        }

        webView = findViewById(R.id.webView)
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // Enable JavaScript for some websites

        // Load the URL in the WebView
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
