package com.game.game

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesM {
    private const val PRE_IS_GAME = "MyPrefIsGame"
    private const val LAST_ACTIVITY_IS_GAME = "last_activity_is_game"

    fun saveIsGame(context: Context, isGame: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PRE_IS_GAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(LAST_ACTIVITY_IS_GAME, isGame.toString())
        editor.apply()
    }

    fun getIsGame(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PRE_IS_GAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LAST_ACTIVITY_IS_GAME, null)
    }
}