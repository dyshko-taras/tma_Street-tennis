package com.game.game

import android.content.Context
import android.content.Intent
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.game.game.lib.MainGame

class LauncherGame : AndroidApplication() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        var config = AndroidApplicationConfiguration()
        config.useAccelerometer = false;
        config.useCompass = false;
        initialize(MainGame(this), config)
    }

    //add intent
    companion object {
        fun move(context: Context) {
            context.startActivity(Intent(context, LauncherGame::class.java))
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

}