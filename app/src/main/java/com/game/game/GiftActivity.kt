package com.game.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class GiftActivity : AppCompatActivity() {
    private var TAG = "GiftActivity1"
    private lateinit var giftView: WebView
    private lateinit var myProgressBar: ProgressBar
    private var site = "https://splindlewort.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift)
        giftView = findViewById(R.id.giftView)
        myProgressBar = findViewById(R.id.myProgressBar)
        setSettings(giftView)
        giftView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush();
                if (giftView.progress == 100) {
                    myProgressBar.visibility = View.GONE
                }
            }
        }
        checkStatus(site)
        _is200.observe(this) {
            Log.d(TAG, it.toString())
            if (it) giftView.loadUrl(site)
//            else LauncherGame.move(this)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setSettings(webView: WebView) {
        webView.settings.also {
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
            it.useWideViewPort = true
            it.displayZoomControls = false
            it.builtInZoomControls = true
            it.loadsImagesAutomatically = true
            it.cacheMode = WebSettings.LOAD_NO_CACHE
            it.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            it.allowFileAccessFromFileURLs = true
        }
    }

    private fun goBack(webView: WebView) {
        if (webView.canGoBack()) webView.goBack()
        else finishAffinity()
    }

    override fun onBackPressed() {
        goBack(giftView)
    }

    companion object {
        fun move(context: Context) {
            context.startActivity(Intent(context, GiftActivity::class.java))
        }
    }

    private val _is200 = MutableLiveData<Boolean>()

    fun checkStatus(urlString: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                Log.d(TAG, "Response Code: $responseCode")
                runOnUiThread {
                    _is200.value = responseCode == HttpURLConnection.HTTP_OK
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

}