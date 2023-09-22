package com.game.game

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var TAG = "MainActivity1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        LauncherGame.move(this)
//        GiftActivity.move(this)

        if (isInternetWorking()) {
            if (SharedPreferencesM.getIsGame(this) != null) {
                when {
                    SharedPreferencesM.getIsGame(this) == "true" -> {
                        LauncherGame.move(this)
                    }
                    else -> {
                        GiftActivity.move(this)
                    }
                }
            } else {
                GiftActivity.move(this)
                SharedPreferencesM.saveIsGame(this, false)
            }
        } else {
            LauncherGame.move(this)
            SharedPreferencesM.saveIsGame(this, true)
        }
    }

    fun isInternetWorking(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}