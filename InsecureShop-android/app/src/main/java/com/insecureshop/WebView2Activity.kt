package com.insecureshop

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_product_list.*


class WebView2Activity : AppCompatActivity() {

    companion object {
        private val ALLOWED_TARGETS = setOf(
            ComponentName("com.insecureshop", "com.insecureshop.ChooserActivity")
        )
    }

    val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Mobile Safari/537.36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setSupportActionBar(toolbar)
        title = getString(R.string.webview)

        val extraIntent = intent.getParcelableExtra<Intent>("extra_intent")
        if (extraIntent != null) {
            val resolved = extraIntent.resolveActivity(packageManager)
            if (resolved != null && ALLOWED_TARGETS.contains(ComponentName(resolved.packageName, resolved.className))) {
                startActivity(extraIntent)
                finish()
                return
            }
        }

        val webview = findViewById<WebView>(R.id.webview)

        webview.settings.javaScriptEnabled = true
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.settings.allowUniversalAccessFromFileURLs = false
        webview.settings.userAgentString = USER_AGENT
        if (!intent.dataString.isNullOrBlank()) {
            webview.loadUrl(intent.dataString)
        } else if (!intent.data?.getQueryParameter("url").isNullOrBlank()) {
            webview.loadUrl(intent.data?.getQueryParameter("url"))
        } else if(!intent.extras?.getString("url").isNullOrEmpty()){
            webview.loadUrl(intent.extras?.getString("url"))
        }
    }
}
